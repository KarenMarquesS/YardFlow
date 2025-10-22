package org.example.yardflow.repository;


import org.example.yardflow.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository <Usuario, Long>{

    Optional<Usuario> findBynome(String nome);
    Optional<Usuario> findByEmail(String email);


}
