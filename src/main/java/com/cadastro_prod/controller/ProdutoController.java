package com.cadastro_prod.controller;
import com.cadastro_prod.modelo.GrupoProduto;
import com.cadastro_prod.modelo.Produto;
import com.cadastro_prod.repository.ProdutoRepository;
import com.cadastro_prod.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.List;
import java.util.spi.ToolProvider;

@RestController
@RequestMapping(value="/api")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoService produtoService;


    @Transactional
    @CacheEvict(value = "/produto", allEntries = true)
    @PostMapping("/produto")
    public ResponseEntity<Produto> salvaProduto(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    @GetMapping("/produtos")
    public ResponseEntity<Page<Produto>> listaProdutos(@RequestParam Integer qtde, @RequestParam Integer pagina){
        if(qtde == null || pagina==null){
            return new ResponseEntity(new Error("Parametros qtde e pagina precisam ser informados"), HttpStatus.BAD_REQUEST);
        }else {
            Pageable paginacao = PageRequest.of(pagina, qtde);
            Page<Produto> listaProdutos = produtoRepository.findAll(paginacao);
            if(listaProdutos.getContent().size()>0){
               for (Produto ProdutoAtual :  listaProdutos.getContent()){
               ProdutoAtual.getGrupoProduto().setListaProdutos(null);
               }
            }
            return ResponseEntity.ok(listaProdutos);
        }
     }

    @Transactional
    @CacheEvict(value = "/produto", allEntries = true)
    @PutMapping("/produto")
    public ResponseEntity<Produto> alteraProduto(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    @GetMapping("/produto/{nome}")
    public ResponseEntity<List<Produto>> produtoPorNome(@PathVariable(value="nome") String nome){
        List<Produto> ProdutoPorNome = produtoRepository.findByNome(nome);
        return ResponseEntity.ok(ProdutoPorNome);
    }


    @GetMapping("/produto_por_fornecedor/{nome}")
    public ResponseEntity<List<Produto>> produtoPorFornecedor(@PathVariable(value="nome") String nome){

        List<Produto> ProdutoPorFornecedor = produtoService.FornecedorPeloNome2(nome);
        return ResponseEntity.ok(ProdutoPorFornecedor);
    }

}
