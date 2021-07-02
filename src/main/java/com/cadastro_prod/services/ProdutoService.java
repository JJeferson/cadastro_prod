package com.cadastro_prod.services;

import com.cadastro_prod.modelo.Produto;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class ProdutoService {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("produto");
    private EntityManager em = FACTORY.createEntityManager();

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
