package com.cadastro_prod.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ProdutoId;
    @NotNull(message="Nome precisa ser ppreenchido")
    private String Nome;
    private String Fornecedor;
    private BigDecimal Quantidade;
    private BigDecimal Valor;
    @ManyToOne
    private GrupoProduto grupoProduto;

    public Produto(){

    }
    public Produto(String nome,String fornecedor, BigDecimal quantidade, BigDecimal valor, GrupoProduto grupo_Produto){
        this.Nome=nome;
        this.Fornecedor=fornecedor;
        this.Quantidade = quantidade;
        this.Valor = valor;
        this.grupoProduto=grupo_Produto;
    }
}
