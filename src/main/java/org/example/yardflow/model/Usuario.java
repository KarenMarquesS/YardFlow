package org.example.yardflow.model;


import java.util.Set;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="tb_yf_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;

    @Pattern(regexp = "^(.+)@(.+)$", message = "Digite um e-mail válido")
    private String email;

    private String senha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idfuncao")
    private Funcao funcao;



    public Usuario() {
    }

    public Usuario(long id, String nome, String email, String senha, Funcao funcao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
