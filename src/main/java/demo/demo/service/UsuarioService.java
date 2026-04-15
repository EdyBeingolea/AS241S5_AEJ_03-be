package demo.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import demo.demo.dto.request.UsuarioRequest;
import demo.demo.dto.response.ApiResponse;
import demo.demo.dto.response.UsuarioResponse;
import demo.demo.mapper.MapperDatos;
import demo.demo.model.Usuario;
import demo.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MapperDatos mapperDatos;

    public Mono<ApiResponse<List<UsuarioResponse>>> listaUsuario() {
        return usuarioRepository.findAll()
                .map(user -> mapperDatos.toResponse(user))
                .collectList()
                .map(data -> ApiResponse.succes("Lista de Usuario", data));
    }

    public Mono<ApiResponse<String>> agregarUsuario(UsuarioRequest request) {
        Usuario usuario = mapperDatos.crearUsuario(request);
        return usuarioRepository.save(usuario)
                .map(saved -> ApiResponse.succes("Se creo el usuario", "OK"));
    }

}
