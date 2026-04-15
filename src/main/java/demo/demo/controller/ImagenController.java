package demo.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import demo.demo.dto.request.GenerarImagenRequest;
import demo.demo.dto.response.ApiResponse;
import demo.demo.service.ImagenService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/imagen")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenService imagenService;

    @PostMapping("/subir-archivo")
    public Mono<ApiResponse<Object>> uploadFile(
            @RequestPart("file") FilePart file,
            @RequestParam("idUsuario") Integer idUsuario) {

        if (file == null) {
            return Mono.just(ApiResponse.error("Archivo no enviado", null));
        }

        return imagenService.uploadFile(file, idUsuario);
    }

    @PostMapping("/generar-imagen")
    public Mono<ApiResponse<JsonNode>> generarImagen(@RequestBody GenerarImagenRequest request) {
        return imagenService.generarImagen(request);
    }

    @PostMapping("/{idUsuario}")
    public Mono<ResponseEntity<Resource>> verImagen(@PathVariable Integer idUsuario) {
        return imagenService.getFile(idUsuario)
                .map(apiResponse -> {
                    Resource resource = apiResponse.data();
                    String contentType = "image/jpeg";
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION,
                                    "inline; filename=\"" + resource.getFilename() + "\"")
                            .contentType(MediaType.parseMediaType(contentType))
                            .body(resource);
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
