package org.example.yardflow.repository;

import org.example.yardflow.model.Yardflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface YardflowRepositorio extends JpaRepository<Yardflow, Long> {

    Optional<Yardflow> findBySerial(@Param("serial")String serial);

    List<Yardflow> findByDtUltimoAcionamento(LocalDateTime dtUltimoAcionamento);

    List<Yardflow> findByDtUltimoAcionamentoBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT yf FROM Yardflow yf LEFT JOIN FETCH yf.moto ORDER BY yf.idyf")
    List<Yardflow> findAllWithMoto();

}

