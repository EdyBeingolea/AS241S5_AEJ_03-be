package demo.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerarImagenRequest {

    @NotBlank(message = "El prompt es obligatorio")
    private String prompt;
    @NotNull(message = "El estilo no puede ser nulo")
    private Integer styleId;
    @NotBlank(message = "El tamaño no puede ser nulo")
    private String size;
}