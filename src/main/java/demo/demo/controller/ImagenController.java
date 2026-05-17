package demo.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.dto.request.GenerarImagenRequest;
import demo.demo.dto.response.ApiResponse;
import demo.demo.dto.response.GenerarImagenResponse;
import demo.demo.service.ImagenService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/imagen")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenService imagenService;

    @PostMapping("/generar-imagen")
    public Mono<ApiResponse<GenerarImagenResponse>> generarImagen(@RequestBody GenerarImagenRequest request) {
        return imagenService.generarImagen(request);
    }

}
