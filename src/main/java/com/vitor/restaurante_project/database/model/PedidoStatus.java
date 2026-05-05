package com.vitor.restaurante_project.database.model;

public enum PedidoStatus {
    CRIADO(0),
    EM_PREPARO(1),
    PRONTO(2),
    FINALIZADO(3),
    CANCELADO(4);

    private final int ordem;

    PedidoStatus(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }
}