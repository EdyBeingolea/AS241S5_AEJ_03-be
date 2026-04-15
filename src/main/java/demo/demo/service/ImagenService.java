package demo.demo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import demo.demo.dto.ConstanApi;
import demo.demo.dto.request.GenerarImagenRequest;
import demo.demo.dto.response.ApiResponse;
import demo.demo.mapper.MapperDatos;
import demo.demo.model.Imagen;
import demo.demo.repository.ImagenRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class ImagenService {

    private final String UPLOAD_DIR = "uploads/";
    private final ImagenRepository imagenRepository;
    private final MapperDatos mapperDatos;
    private final ObjectMapper objectMapper;

    @Value("${rapidapi.image-generator.base-url}")
    private String imageGeneratorBaseUrl;

    @Value("${rapidapi.image-generator.quick-path}")
    private String imageGeneratorQuickPath;

    @Value("${rapidapi.image-generator.host}")
    private String rapidApiHost;

    @Value("${rapidapi.image-generator.api-key}")
    private String rapidApiKey;

    public Mono<ApiResponse<Object>> uploadFile(FilePart file, Integer idUsuario) {

        if (file.filename() == null || file.filename().isEmpty()) {
            return Mono.just(ApiResponse.error("El archivo está vacío", null));
        }

        String fileName = file.filename();
        Path path = Paths.get(UPLOAD_DIR).resolve(fileName);

        return file.transferTo(path)
                .then(Mono.defer(() -> {
                    return imagenRepository.findByIdUsuarioAndVigenteAndActivo(
                            idUsuario, ConstanApi.ESTADO_VIGENTE, ConstanApi.ESTADO_ACTIVO)
                            .flatMap(fotoAnterior -> {
                                // Marcar foto anterior como histórica
                                fotoAnterior.setVigente(ConstanApi.ESTADO_HISTORICO);
                                return imagenRepository.save(fotoAnterior);
                            })
                            .then(Mono.defer(() -> {
                                // Guardar nueva foto
                                Imagen imagenNueva = mapperDatos.creatImagen(idUsuario, fileName);
                                return imagenRepository.save(imagenNueva)
                                        .map(img -> ApiResponse.succes(
                                                "Archivo subido exitosamente",
                                                null));
                            }))
                            .switchIfEmpty(Mono.defer(() -> {
                                // Si no hay foto anterior, solo guardar la nueva
                                Imagen imagenNueva = mapperDatos.creatImagen(idUsuario, fileName);
                                return imagenRepository.save(imagenNueva)
                                        .map(img -> ApiResponse.succes(
                                                "Archivo subido exitosamente",
                                                null));
                            }));
                }))
                .onErrorResume(e -> Mono.just(ApiResponse.error("Error al subir archivo: " + e.getMessage(), null)));
    }

    public Mono<ApiResponse<Resource>> getFile(Integer idUsuario) {
        return imagenRepository.findByIdUsuarioAndVigenteAndActivo(
                idUsuario, ConstanApi.ESTADO_VIGENTE, ConstanApi.ESTADO_ACTIVO)
                .flatMap(imagen -> {
                    Path path = Paths.get(UPLOAD_DIR).resolve(imagen.getNombreArchivo());
                    Resource resource = new FileSystemResource(path);

                    if (!resource.exists() || !resource.isReadable()) {
                        return Mono.error(new RuntimeException("Archivo no encontrado o no legible"));
                    }

                    return Mono.just(ApiResponse.succes("imagen encontrada", resource));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró imagen para el usuario")));
    }

    public Mono<ApiResponse<JsonNode>> generarImagen(GenerarImagenRequest request) {
        if (request == null || request.getPrompt() == null || request.getPrompt().trim().isEmpty()) {
            return Mono.just(ApiResponse.error("El prompt es obligatorio", null));
        }

        if (rapidApiKey == null || rapidApiKey.isBlank()) {
            return Mono.just(ApiResponse.error("Falta configurar la variable RAPIDAPI_KEY", null));
        }

        return Mono.fromCallable(() -> {
            String prompt = request.getPrompt().replace("\"", "\\\"");
            Integer styleId = request.getStyleId() == null ? 4 : request.getStyleId();
            String size = request.getSize() == null || request.getSize().isBlank() ? "1-1" : request.getSize();

            String body = String.format(
                    "{\"prompt\":\"%s\",\"style_id\":%d,\"size\":\"%s\"}",
                    prompt,
                    styleId,
                    size);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(imageGeneratorBaseUrl + imageGeneratorQuickPath))
                    .header("x-rapidapi-key", rapidApiKey)
                    .header("x-rapidapi-host", rapidApiHost)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonNode responseObject = objectMapper.readTree(response.body());
                return ApiResponse.succes("Imagen generada correctamente", responseObject);
            }

            return ApiResponse.error(
                    "Error al generar la imagen. Codigo HTTP: " + response.statusCode(),
                    objectMapper.readTree(response.body()));
        }).subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(e -> Mono.just(ApiResponse.error("Error al generar imagen: " + e.getMessage(), null)));
    }

}
