package com.cadastro_prod.modelo;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "grupo_produto")
public class GrupoProduto {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long GrupoProdutoId;
    private String nome;
    @OneToMany(mappedBy = "grupoProduto",cascade = CascadeType.REMOVE )
    private List<Produto> ListaProdutos;
}
