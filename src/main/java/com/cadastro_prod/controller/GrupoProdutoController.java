package com.cadastro_prod.controller;
import com.cadastro_prod.modelo.GrupoProduto;
import com.cadastro_prod.repository.GrupoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api")
public class GrupoProdutoController {


    @Autowired
    GrupoProdutoRepository grupoProdutoRepository;


    @Transactional
    @CacheEvict(value = "/grupo_produto", allEntries = true)
    @PostMapping("/grupo_produto")
    public ResponseEntity<GrupoProduto> salvaGrupoProduto(@RequestBody GrupoProduto grupoproduto) {
        return ResponseEntity.ok(grupoProdutoRepository.save(grupoproduto));
    }

    @GetMapping("/grupos_de_produtos")
    public ResponseEntity<Page<GrupoProduto>> listaGrupoProdutos(@RequestParam Integer qtde, @RequestParam Integer pagina){
        if(qtde == null || pagina==null){
            return new ResponseEntity(new Error("Parametros qtde e pagina precisam ser informados"), HttpStatus.BAD_REQUEST);
        }else {
            Pageable paginacao = PageRequest.of(pagina, qtde);
            Page<GrupoProduto> listaGrupoProdutos = grupoProdutoRepository.findAll(paginacao);
            return ResponseEntity.ok(listaGrupoProdutos);
        }
    }

    @Transactional
    @CacheEvict(value = "/grupo_produto", allEntries = true)
    @PutMapping("/grupo_produto")
    public ResponseEntity<GrupoProduto> alteraGrupoProduto(@RequestBody GrupoProduto grupoproduto) {
        return ResponseEntity.ok(grupoProdutoRepository.save(grupoproduto));
    }
}
