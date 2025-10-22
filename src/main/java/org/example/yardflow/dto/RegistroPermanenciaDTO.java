package org.example.yardflow.dto;

import org.example.yardflow.model.EnumModelo;

import java.time.LocalDate;

public class RegistroPermanenciaDTO {

    private long idpermanencia;
    private long idmoto;
    private EnumModelo modelo;
    private LocalDate entradapatio;
    private LocalDate saidapatio;
    private long periodo;

    public RegistroPermanenciaDTO() {
    }

    public RegistroPermanenciaDTO(long idpermanencia, long idmoto, EnumModelo modelo, LocalDate entradapatio, LocalDate saidapatio, long periodo) {
        this.idpermanencia = idpermanencia;
        this.idmoto = idmoto;
        this.modelo = modelo;
        this.entradapatio = entradapatio;
        this.saidapatio = saidapatio;
        this.periodo = periodo;
    }

    public long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(long periodo) {
        this.periodo = periodo;
    }

    public LocalDate getSaidapatio() {
        return saidapatio;
    }

    public void setSaidapatio(LocalDate saidapatio) {
        this.saidapatio = saidapatio;
    }

    public LocalDate getEntradapatio() {
        return entradapatio;
    }

    public void setEntradapatio(LocalDate entradapatio) {
        this.entradapatio = entradapatio;
    }

    public EnumModelo getModelo() {
        return modelo;
    }

    public void setModelo(EnumModelo modelo) {
        this.modelo = modelo;
    }

    public long getIdmoto() {
        return idmoto;
    }

    public void setIdmoto(long idmoto) {
        this.idmoto = idmoto;
    }

    public long getIdpermanencia() {
        return idpermanencia;
    }

    public void setIdpermanencia(long idpermanencia) {
        this.idpermanencia = idpermanencia;
    }
}
