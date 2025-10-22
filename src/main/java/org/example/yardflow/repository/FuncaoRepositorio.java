package org.example.yardflow.repository;

import org.example.yardflow.model.Funcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncaoRepositorio  extends JpaRepository<Funcao, Long> {
    Optional<Funcao> findById(long idfuncao);
}
