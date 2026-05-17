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
@Table("imagenes_favoritas")
public class ImagenFavorita extends ApiEntity {

    @Id
    @Column("id")
    private Integer id;
    @Column("imagen_favorito")
    private String favorita;
    @Column("id_imagen")
    private Integer idImagen;
    @Column("id_usuario")
    private Integer idUsuario;
}
