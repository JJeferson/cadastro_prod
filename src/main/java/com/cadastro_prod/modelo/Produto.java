package com.cadastro_prod.modelo;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ProdutoId;
    private String Nome;
    private String Fornecedor;
    private BigDecimal Quantidade;
    private BigDecimal Valor;
    @ManyToOne
    private GrupoProduto grupoProduto;
}
