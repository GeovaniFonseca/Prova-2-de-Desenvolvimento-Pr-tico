package org.senai.ecommerce.entity.produto;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "produto")
@Entity(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "codigo")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;

}
