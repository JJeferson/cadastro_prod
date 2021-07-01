package com.cadastro_prod.services;

import com.cadastro_prod.modelo.Produto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProdutoService {

    private EntityManager em;

    public List<Tuple> FornecedorPeloNome(String nomeForncedor) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<Produto> root = query.from(Produto.class);

        query.multiselect(root.get("ProdutoId"),root.get("Nome")).
        where(builder.and(builder.like(builder.lower(root.get("Fornecedor")), "%" +nomeForncedor.toString().toLowerCase()+"%")));

        List<Tuple> results= em.createQuery(query).getResultList();



        return results;
    }

}
