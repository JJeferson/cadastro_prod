package com.cadastro_prod.repository;

import com.cadastro_prod.modelo.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto findById(long id);

    @Query("select c from Produto c where c.Nome like %:Nome%")
    public List<Produto> findByNome(String Nome);

}
