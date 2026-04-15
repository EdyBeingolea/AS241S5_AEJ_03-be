package demo.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("foto")
public class Imagen {

    @Id
    @Column("id")
    private Integer id;
    @Column("id_usuario")
    private Integer idUsuario;
    @Column("name")
    private String nombreArchivo;
    @Column("vigente")
    private String vigente;
    @Column("activo")
    private String activo;
}
