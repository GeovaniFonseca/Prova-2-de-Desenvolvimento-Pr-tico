package org.senai.ecommerce.entity.produto;

import lombok.Data;

@Data
public class ProdutoDTO {
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
}
