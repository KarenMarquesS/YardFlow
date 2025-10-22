package org.example.yardflow.repository;

import org.example.yardflow.model.Registro_check_in_out;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface Registro_check_in_outRepositorio extends JpaRepository<Registro_check_in_out, Long> {


    List<Registro_check_in_out> findAllByEntradapatio(LocalDate entradapatio);


    List<Registro_check_in_out> findAllBySaidapatio(LocalDate saidapatio);

    // Encontra o registro em aberto (entrada sem saída) mais recente da moto
    Optional<Registro_check_in_out> findTopByMoto_IdmotoAndSaidapatioIsNullOrderByEntradapatioDesc(long idmoto);

    // Encontra o último registro (fechado ou não) pela moto
    Optional<Registro_check_in_out> findTopByMoto_IdmotoOrderByEntradapatioDesc(long idmoto);

    // Lista todos os registros de uma moto
    List<Registro_check_in_out> findByMoto_Idmoto(long idmoto);

    // Lista todos com entrada (útil para ordenar por permanência em memória)
    List<Registro_check_in_out> findByEntradapatioIsNotNull();

}
