package org.example.yardflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import org.example.yardflow.model.EnumModelo;
import org.example.yardflow.model.Yardflow;

import java.time.LocalDateTime;


public class MotoDTO {

    private long idmoto;

    private EnumModelo modelo;

    @Size(min = 17, max = 17, message = "Chassi deve ter 17 caracteres")
    private String chassi;

    @Size(min = 6, max = 7, message = "Placa deverá ter 7 caracteres")
    @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$", message = "Placa inválida. Use o formato ABC1D23.")
    private String placa;

    @NotBlank(message = "Registre o motivo da moto entrar no pátio")
    @Size(max = 500, message = "Histórico deve ter no máximo 500 caracteres")
    private String historico;

    private Yardflow yardflow;

    // Campos usados na página de cadastro (view model)
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private Integer yFlowIoT;
    private Boolean ativo;


    public MotoDTO() {
    }

    public MotoDTO(long idmoto, EnumModelo modelo, String chassi, String placa, String historico, Yardflow yardflow) {
        this.idmoto = idmoto;
        this.modelo = modelo;
        this.chassi = chassi;
        this.placa = placa;
        this.historico = historico;
        this.yardflow = yardflow;
    }

    public long getIdmoto() {
        return idmoto;
    }

    public void setIdmoto(long idmoto) {
        this.idmoto = idmoto;
    }

    public EnumModelo getModelo() {
        return modelo;
    }

    public void setModelo(EnumModelo modelo) {
        this.modelo = modelo;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Yardflow getYardflow() {
        return yardflow;
    }

    public void setYardflow(Yardflow yardflow) {
        this.yardflow = yardflow;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public Integer getyFlowIoT() {
        return yFlowIoT;
    }

    public void setyFlowIoT(Integer yFlowIoT) {
        this.yFlowIoT = yFlowIoT;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}