package demo.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import demo.demo.model.Imagen;

@Repository
public interface ImagenRepository extends ReactiveCrudRepository<Imagen, Integer> {

}
