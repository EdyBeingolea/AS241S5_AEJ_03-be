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
@Table("usuarios")
public class Usuario extends ApiEntity {

    @Id
    @Column("id")
    private Integer id;
    @Column("nombre_usuario")
    private String username;
    @Column("password_hash")
    private String password;
    @Column("estado")
    private String estado;
}
