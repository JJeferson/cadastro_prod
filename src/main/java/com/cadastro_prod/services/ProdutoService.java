package com.cadastro_prod.services;

import com.cadastro_prod.modelo.Produto;
import com.cadastro_prod.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public  class ProdutoService implements ProdutoRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ProdutoRepository produtoRepository;



    public List<Produto> FornecedorPeloNome2(String nomeForncedor)  {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Produto> queryProduto = cb.createQuery(Produto.class);
        Root<Produto> c = queryProduto.from(Produto.class);
        queryProduto.multiselect(c.get("ProdutoId")).where(cb.like(cb.lower(c.get("Fornecedor")), "%" +nomeForncedor.toLowerCase()+"%"));
        TypedQuery query = em.createQuery(queryProduto.select(c));
        List<Produto> result = query.getResultList();

        return result;
    }


    @Transactional
    public List<Tuple> FornecedorPeloNome(String nomeForncedor) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<Produto> root = query.from(Produto.class);

        query.multiselect(root.get("ProdutoId"),root.get("Nome")).
        where(builder.and(builder.like(builder.lower(root.get("Fornecedor")), "%" +nomeForncedor.toString().toLowerCase()+"%")));

        List<Tuple> results= em.createQuery(query).getResultList();

        return results;
    }

    @Override
    public Produto findById(long id) {
       return produtoRepository.findById(id);
    }

    @Override
    public List<Produto> findByNome(String Nome) {
        return null;
    }

    @Override
    public List<Produto> findAll() {
        return null;
    }

    @Override
    public List<Produto> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Produto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Produto> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Produto produto) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends Produto> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Produto> S save(S s) {
        return null;
    }

    @Override
    public <S extends Produto> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Produto> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Produto> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends Produto> List<S> saveAllAndFlush(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Produto> iterable) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Produto getOne(Long aLong) {
        return null;
    }

    @Override
    public Produto getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Produto> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Produto> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Produto> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Produto> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Produto> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Produto> boolean exists(Example<S> example) {
        return false;
    }
}
