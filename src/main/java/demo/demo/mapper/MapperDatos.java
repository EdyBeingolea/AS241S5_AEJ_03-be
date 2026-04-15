package demo.demo.mapper;

import org.springframework.stereotype.Component;

import demo.demo.dto.ConstanApi;
import demo.demo.dto.request.UsuarioRequest;
import demo.demo.dto.response.UsuarioResponse;
import demo.demo.model.Imagen;
import demo.demo.model.Usuario;

@Component
public class MapperDatos {

    public UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .estado(usuario.getEstado())
                .build();
    }

    public Usuario crearUsuario(UsuarioRequest request) {
        return Usuario.builder()
                .nombre(request.getNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .estado(ConstanApi.ESTADO_ACTIVO)
                .build();
    }

    public Imagen creatImagen(Integer usuario, String filname) {
        return Imagen.builder()
                .nombreArchivo(filname)
                .idUsuario(usuario)
                .activo(ConstanApi.ESTADO_ACTIVO)
                .vigente(ConstanApi.ESTADO_VIGENTE)
                .build();
    }
}
