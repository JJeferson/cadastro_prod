package com.cadastro_prod.repository;

import com.cadastro_prod.modelo.GrupoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoProdutoRepository extends JpaRepository<GrupoProduto, Long> {
    GrupoProduto findById(long id);
}
