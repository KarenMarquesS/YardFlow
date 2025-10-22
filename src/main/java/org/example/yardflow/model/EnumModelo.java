package org.example.yardflow.model;

public enum EnumModelo {
    MOTTU_SPORT("Mottu Sport"), MOTTU_E("Mottu E"), MOTTU_POP("Mottu Pop");



    private final String descricao;

    private EnumModelo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

