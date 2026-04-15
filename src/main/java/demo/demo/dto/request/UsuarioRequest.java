package demo.demo.dto.request;

import lombok.*;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequest {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
}
