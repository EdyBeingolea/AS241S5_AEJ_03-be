package demo.demo.model;

import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ApiEntity {
    @Column("usuario_creacion")
    private String usuarioCreacion;
    @Column("fecha_creacion")
    private String fechaCreacion;
}
