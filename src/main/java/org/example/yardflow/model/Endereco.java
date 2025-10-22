package org.example.yardflow.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="tb_yf_endereco")
public class Endereco {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido, use o formato 00000-000 ou 00000000")
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;


    public Endereco() {
    }

    public Endereco(long id, String cep, String logradouro, String numero, String bairro, String localidade, String uf) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido, use o formato 00000-000 ou 00000000") String getCep() {
        return cep;
    }

    public void setCep(@Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido, use o formato 00000-000 ou 00000000") String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
