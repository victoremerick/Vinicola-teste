package br.victoremerick.gx2teste.model.dto;

import lombok.Data;

@Data
public class Produto {
    private int codigo;
    private String tipo_vinho;
    private double preco;
    private String safra;
    private int ano_compra;
}
