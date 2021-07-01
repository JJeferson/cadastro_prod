package com.cadastro_prod.Services;

import com.cadastro_prod.modelo.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class ProdutoService {

   // @Transactional
    public List<Produto> FornecedorPeloNome(String nomeForncedor){

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("produto");
        EntityManager em = factory.createEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery   = builder.createQuery(Produto.class);
        Root<Produto> produto = criteriaQuery.from(Produto.class);
        Predicate predicate = builder.like(builder.upper(produto.<String>get("Fornecedor")),"%"+nomeForncedor+"%".toUpperCase());

        criteriaQuery.select(produto);
        criteriaQuery.where(predicate);
        TypedQuery query = em.createQuery(criteriaQuery);
        List<Produto> results  = query.getResultList();

        em.close();
        factory.close();

        return results;
    }
}
