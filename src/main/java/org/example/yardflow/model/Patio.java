package org.example.yardflow.model;

import jakarta.persistence.*;


@Entity
@Table(name = "tb_yf_patio")
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idpatio;

    private String name;

    @Column(nullable = false)
    private long qtdvagas;


    public Patio() {
    }

    public Patio(long idpatio, String name, long qtdvagas) {
        this.idpatio = idpatio;
        this.name = name;
        this.qtdvagas = qtdvagas;
    }

    public long getIdpatio() {
        return idpatio;
    }

    public void setIdpatio(long idpatio) {
        this.idpatio = idpatio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQtdvagas() {
        return qtdvagas;
    }

    public void setQtdvagas(long qtdvagas) {
        this.qtdvagas = qtdvagas;
    }
}

