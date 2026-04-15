package demo.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import demo.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Integer> {

}
