package demo.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table("images_publicas")
public class ImagenPublica extends ApiEntity {

    @Id
    @Column("id")
    private Integer id;
    @Column("imagen_publica")
    private String publica;
    @Column("id_imagen")
    private Integer idImagen;
    @Column("id_usuario")
    private Integer idUsuario;

}
