package demo.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenGeneradaItemResponse {

    private Integer index;
    private Boolean nsfw;
    private String origin;
    private String thumb;
}
