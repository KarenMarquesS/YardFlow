package org.example.yardflow.dto;

import org.example.yardflow.model.EnumSetor;
import org.example.yardflow.model.Registro_check_in_out;

import java.time.LocalDate;



public class Registro_check_in_outDTO {

    private long idregistro;
    private LocalDate entradapatio;
    private LocalDate saidapatio;
    private long periodo;
    private EnumSetor setor;
    private MotoDTO motoDTO;

    public Registro_check_in_outDTO(Registro_check_in_out registro) {
        this.idregistro = registro.getIdregistro();
        this.entradapatio = registro.getEntradapatio();
        this.saidapatio = registro.getSaidapatio();
        this.periodo = registro.getPeriodo();
        this.motoDTO = new MotoDTO();

        // sobre a entidade moto
        var m = registro.getMoto();
        this.motoDTO = new MotoDTO(
                registro.getMoto().getIdmoto(),
                registro.getMoto().getModelo(),
                registro.getMoto().getChassi(),
                registro.getMoto().getPlaca(),
                registro.getMoto().getHistorico(),
                registro.getMoto().getYardflow()
        );
    }

    public Registro_check_in_outDTO() {
    }

    public Registro_check_in_outDTO(long idregistro, LocalDate entradapatio, LocalDate saidapatio, long periodo,
                                    EnumSetor setor, MotoDTO motoDTO) {
        this.idregistro = idregistro;
        this.entradapatio = entradapatio;
        this.saidapatio = saidapatio;
        this.periodo = periodo;
        this.setor = setor;
        this.motoDTO = motoDTO;
    }

    public long getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(long idregistro) {
        this.idregistro = idregistro;
    }

    public LocalDate getEntradapatio() {
        return entradapatio;
    }

    public void setEntradapatio(LocalDate entradapatio) {
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

    public MotoDTO getMotoDTO() {
        return motoDTO;
    }

    public void setMotoDTO(MotoDTO motoDTO) {
        this.motoDTO = motoDTO;
    }
}
