package demo.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import demo.demo.dto.request.GenerarImagenRequest;
import demo.demo.dto.response.ApiResponse;
import demo.demo.dto.response.GenerarImagenResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ImagenService {

    private final WebClient webClient;

    @Value("${rapidapi.image-generator.base-url}")
    private String imageGeneratorBaseUrl;

    @Value("${rapidapi.image-generator.quick-path}")
    private String imageGeneratorQuickPath;

    @Value("${rapidapi.image-generator.host}")
    private String rapidApiHost;

    @Value("${rapidapi.image-generator.api-key}")
    private String rapidApiKey;

    public Mono<ApiResponse<GenerarImagenResponse>> generarImagen(GenerarImagenRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("prompt", request.getPrompt());
        body.put("style_id", request.getStyleId());
        body.put("size", request.getSize());

        return webClient.post()
                .uri(imageGeneratorBaseUrl + imageGeneratorQuickPath)
                .header("x-rapidapi-host", rapidApiHost)
                .header("x-rapidapi-key", rapidApiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(GenerarImagenResponse.class)
                .map(response -> ApiResponse.succes(
                        "Imagen generada correctamente",
                        response))
                .onErrorResume(error -> Mono.just(
                        ApiResponse.error(
                                "Error al generar imagen: " + error.getMessage(),
                                null)));

    }

}
