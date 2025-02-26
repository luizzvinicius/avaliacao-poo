package avaliacao.luiz.domain.entities;

import java.time.LocalDateTime;

public class Compra {
    private int id;
    private int idCliente;
    private int idPagamento;
    private LocalDateTime dataCompra;

    public Compra(int idCliente) {
        this.idCliente = idCliente;
        this.dataCompra = LocalDateTime.now();
    }

    public Compra(int id, int idCliente, int idPagamento, LocalDateTime dataCompra) {
        this.id = id;
        this.idCliente = idCliente;
        this.idPagamento = idPagamento;
        this.dataCompra = dataCompra;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }
}