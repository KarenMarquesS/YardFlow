package org.example.yardflow.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Entity
@Table(name = "tb_yf_registro_check_in_out")
public class Registro_check_in_out {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idregistro;

    @Past(message = "Data de Entrada inválida!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate entradapatio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate saidapatio;

    @Column(nullable = false)
    private long periodo;

    @Enumerated(EnumType.STRING)
    private EnumSetor setor;

    @Valid
    @ManyToOne
    @JoinColumn(name="idmoto")
    private Moto moto;



    // irá retornar Zero se a entrada e saida for no mesmo dia
    public void calcularPeriodoPermanencia() {
        if (entradapatio == null) {
            throw new IllegalStateException("A data de entrada deve ser informada.");
        }

        LocalDate dataSaida = (saidapatio != null) ? saidapatio : LocalDate.now();

        if (dataSaida.isBefore(entradapatio)) {
            throw new IllegalArgumentException("A data de saída não pode ser anterior à data de entrada.");
        }

        long dias = ChronoUnit.DAYS.between(entradapatio, dataSaida);
        this.periodo = (long) Math.max(dias, 1 ); // mínimo 1 dia
    }

    public Registro_check_in_out() {
    }

    public Registro_check_in_out(long idregistro, LocalDate entradapatio, LocalDate saidapatio, long periodo, EnumSetor setor, Moto moto) {
        this.idregistro = idregistro;
        this.entradapatio = entradapatio;
        this.saidapatio = saidapatio;
        this.periodo = periodo;
        this.setor = setor;
        this.moto = moto;
    }

    public long getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(long idregistro) {
        this.idregistro = idregistro;
    }

    public @Past(message = "Data de Entrada inválida!") LocalDate getEntradapatio() {
        return entradapatio;
    }

    public void setEntradapatio(@Past(message = "Data de Entrada inválida!") LocalDate entradapatio) {
        this.entradapatio = entradapatio;
    }

    public LocalDate getSaidapatio() {
        return saidapatio;
    }

    public void setSaidapatio(LocalDate saidapatio) {
        this.saidapatio = saidapatio;
    }

    public long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(long periodo) {
        this.periodo = periodo;
    }

    public EnumSetor getSetor() {
        return setor;
    }

    public void setSetor(EnumSetor setor) {
        this.setor = setor;
    }

    public @Valid Moto getMoto() {
        return moto;
    }

    public void setMoto(@Valid Moto moto) {
        this.moto = moto;
    }
}
