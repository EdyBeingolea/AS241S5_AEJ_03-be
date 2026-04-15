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
@Table("usuario")
public class Usuario {

    @Id
    @Column("id")
    private Integer id;
    @Column("name")
    private String nombre;
    @Column("paternal_surname")
    private String apellidoPaterno;
    @Column("maternal_surname")
    private String apellidoMaterno;
    @Column("status")
    private String estado;
}
