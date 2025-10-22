package org.example.yardflow.repository;

import org.example.yardflow.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoRepositorio extends JpaRepository<Moto, Long> {

    Moto findByPlaca(String placa);

    Moto findByChassi(String chassi);

    @Query("select mT.historico from Moto mT where mT.idmoto = :idmoto")
    String historicoMoto(@Param("idmoto") long idmoto);

    Optional<Moto> findById(Long idmoto);
}
