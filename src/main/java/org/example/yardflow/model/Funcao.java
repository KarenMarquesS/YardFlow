package org.example.yardflow.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="tb_yf_funcao")
public class Funcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idfuncao;

    @Enumerated(EnumType.STRING)
    @Column(name = "funcao", nullable = false, length = 50)
    private EnumFuncao funcao;

    @OneToMany(mappedBy = "funcao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();

    public Funcao() {
    }

    public Funcao(long idfuncao, EnumFuncao enumFuncao, List<Usuario> usuarios) {
        this.idfuncao = idfuncao;
        this.funcao = enumFuncao;
        this.usuarios = usuarios;
    }

    public long getIdfuncao() {
        return idfuncao;
    }

    public void setIdfuncao(long idfuncao) {
        this.idfuncao = idfuncao;
    }

    public EnumFuncao getNome() {
        return funcao;
    }

    public void setNome(EnumFuncao enumFuncao) {
        this.funcao = enumFuncao;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
