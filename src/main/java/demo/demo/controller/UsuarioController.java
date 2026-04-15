package demo.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.dto.request.UsuarioRequest;
import demo.demo.dto.response.ApiResponse;
import demo.demo.dto.response.UsuarioResponse;
import demo.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/listar-usuario")
    public Mono<ApiResponse<List<UsuarioResponse>>> listaUsuario() {
        return usuarioService.listaUsuario();
    }

    @PostMapping("/agregar-usuario")
    public Mono<ApiResponse<String>> agregarUsuario(@RequestBody UsuarioRequest request) {
        return usuarioService.agregarUsuario(request);
    }
}
