package org.example.yardflow.model;

import jakarta.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "tb_yf_IoT")
public class Yardflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idyf;

    @Column(nullable = false, unique = true)
    private String serial;

    @Column(name = "dtultimoacionamento")
    private LocalDateTime dtUltimoAcionamento;

    @OneToOne(mappedBy = "yardflow")
    private Moto moto;


    public Yardflow() {
    }

    public Yardflow(long idyf) {
        this.idyf = idyf;
    }

    public Yardflow(long idyf, String serial, LocalDateTime dtultimoacionamento, Moto moto) {
        this.idyf = idyf;
        this.serial = serial;
        this.dtUltimoAcionamento = dtultimoacionamento;
        this.moto = moto;
    }

    public long getIdyf() {
        return idyf;
    }

    public void setIdyf(long idyf) {
        this.idyf = idyf;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public LocalDateTime getDtUltimoAcionamento() {
        return dtUltimoAcionamento;
    }

    public void setDtUltimoAcionamento(LocalDateTime dtUltimoAcionamento) {
        this.dtUltimoAcionamento = dtUltimoAcionamento;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }
}

