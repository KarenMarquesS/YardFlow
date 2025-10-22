package org.example.yardflow.repository;

import org.example.yardflow.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatioRepositorio extends JpaRepository<Patio, Long> {

    Patio findByName(String name);

    List<Patio> findByQtdvagas(long qtdvagas);

}
