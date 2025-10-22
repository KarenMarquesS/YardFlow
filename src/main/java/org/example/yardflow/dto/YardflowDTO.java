package org.example.yardflow.dto;


import org.example.yardflow.model.Moto;


import java.time.LocalDateTime;
import java.util.UUID;


public class YardflowDTO {

    private long idyf;
    private String serial;
    private LocalDateTime dtultimoacionamento;
    private Moto moto;


    public YardflowDTO() {
    }

    public YardflowDTO(long idyf, String serial) {
        this.idyf = idyf;
        this.serial = serial;
    }


    public YardflowDTO(long idyf, String serial, LocalDateTime dtultimoacionamento, Moto moto) {
        this.idyf = idyf;
        this.serial = serial;
        this.dtultimoacionamento = dtultimoacionamento;
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
        return dtultimoacionamento;
    }

    public void setDtUltimoAcionamento(LocalDateTime dtultimoacionamento) {
        this.dtultimoacionamento = dtultimoacionamento;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }
}

