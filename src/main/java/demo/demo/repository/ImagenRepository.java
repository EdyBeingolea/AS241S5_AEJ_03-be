package demo.demo.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import demo.demo.model.Imagen;
import reactor.core.publisher.Mono;

@Repository
public interface ImagenRepository extends ReactiveCrudRepository<Imagen, Integer> {

    Mono<Imagen> findByIdUsuarioAndVigenteAndActivo(Integer idUsuario, String vigente, String activo);

}
