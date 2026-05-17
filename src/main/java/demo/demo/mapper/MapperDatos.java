package demo.demo.mapper;

import org.springframework.stereotype.Component;

import demo.demo.dto.ConstanApi;
import demo.demo.dto.request.UsuarioRequest;
import demo.demo.dto.response.UsuarioResponse;
import demo.demo.model.Usuario;

@Component
public class MapperDatos {

    public UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .estado(usuario.getEstado())
                .usuario(usuario.getUsuarioCreacion())
                .build();
    }

    public Usuario crearUsuario(UsuarioRequest request) {
        return Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .estado(ConstanApi.ESTADO_ACTIVO)
                .usuarioCreacion(request.getUsuario())
                .build();
    }

}
