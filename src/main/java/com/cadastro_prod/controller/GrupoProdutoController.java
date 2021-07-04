package com.cadastro_prod.controller;
import com.cadastro_prod.modelo.GrupoProduto;
import com.cadastro_prod.modelo.Produto;
import com.cadastro_prod.repository.GrupoProdutoRepository;
import com.cadastro_prod.repository.ProdutoRepository;
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
    @Autowired
    ProdutoRepository produtoRepository;


    @Transactional
    @CacheEvict(value = "/grupoproduto", allEntries = true)
    @PostMapping("/grupoproduto")
    public ResponseEntity<GrupoProduto> salvaGrupoProduto(@RequestBody GrupoProduto grupoproduto) {
        grupoProdutoRepository.save(grupoproduto);
        if(grupoproduto.getListaProdutos().size()>0) {
            for (Produto prodAtual : grupoproduto.getListaProdutos()) {
                Produto objProd = produtoRepository.findById(prodAtual.getProdutoId());
                GrupoProduto grupoProdutoSaveProd = grupoproduto;
                grupoProdutoSaveProd.setListaProdutos(null);
                objProd.setGrupoProduto(grupoProdutoSaveProd);
                produtoRepository.save(objProd);
            }
        }
        return ResponseEntity.ok(grupoproduto);

    }

    @GetMapping("/gruposdeprodutos")
    public ResponseEntity<Page<GrupoProduto>> listaGrupoProdutos(@RequestParam Integer qtde, @RequestParam Integer pagina){
        if(qtde == null || pagina==null){
            return new ResponseEntity(new Error("Parametros qtde e pagina precisam ser informados"), HttpStatus.BAD_REQUEST);
        }else {
            Pageable paginacao = PageRequest.of(pagina, qtde);
            Page<GrupoProduto> listaGrupoProdutos = grupoProdutoRepository.findAll(paginacao);
            for (GrupoProduto grupoProdutoAtual :  listaGrupoProdutos.getContent())
            if(grupoProdutoAtual.getListaProdutos().size()>0) {
                    if(grupoProdutoAtual.getListaProdutos().size()>0) {
                        for (Produto ProdutoAtual : grupoProdutoAtual.getListaProdutos()) {
                            ProdutoAtual.setGrupoProduto(null);
                        }
                }
            }
            return ResponseEntity.ok(listaGrupoProdutos);
        }
    }

    @Transactional
    @CacheEvict(value = "/grupoproduto", allEntries = true)
    @PutMapping("/grupoproduto")
    public ResponseEntity<GrupoProduto> alteraGrupoProduto(@RequestBody GrupoProduto grupoproduto) {
        grupoProdutoRepository.save(grupoproduto);
        if(grupoproduto.getListaProdutos().size()>0) {
            for (Produto prodAtual : grupoproduto.getListaProdutos()) {
                Produto objProd = produtoRepository.findById(prodAtual.getProdutoId());
                GrupoProduto grupoProdutoSaveProd = grupoproduto;
                grupoProdutoSaveProd.setListaProdutos(null);
                objProd.setGrupoProduto(grupoProdutoSaveProd);
                produtoRepository.save(objProd);
            }
        }
        return ResponseEntity.ok(grupoproduto);
    }
}
