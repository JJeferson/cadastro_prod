package com.cadastro_prod.controller;
import com.cadastro_prod.DTO.ErroDTO;
import com.cadastro_prod.modelo.GrupoProduto;
import com.cadastro_prod.modelo.Produto;
import com.cadastro_prod.repository.ProdutoRepository;
import com.cadastro_prod.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.spi.ToolProvider;

@RestController
@RequestMapping(value="/api")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoService produtoService;

    /**
     * Usando isso daqui para fazer testes com JPA e Criteria Query
     * Também tratamento de excessões e erros, além de paginação.
     *
     */

    @Transactional
    @CacheEvict(value = "/produto", allEntries = true)
    @PostMapping("/produto")
    public ResponseEntity<Produto> salvaProduto(@RequestBody Produto produto) {
        //recuperando erros do hibernate e exibindo na saida caso erro.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator ();
        Set<ConstraintViolation<Produto>> constraintViolations = validator.validate(produto);
        if(constraintViolations.size()>0){
            for (ConstraintViolation error: constraintViolations) {
                String msgError = error.getMessage();
                return new ResponseEntity(new Error(msgError), HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    @GetMapping("/produtos")
    public ResponseEntity<Page<Produto>> listaProdutos(@RequestParam(value = "qtde", required=false) Integer qtde, @RequestParam(value = "pagina", required=false) Integer pagina){

            if(pagina==null||qtde==null){
                Error Msg_Erro = new Error("Valores de quantidade e numero da pagina precisam ser informados nos parametros Exemplo: /api/produtos?qtde=20&pagina=0 ");
                return new ResponseEntity(Msg_Erro, HttpStatus.BAD_REQUEST);
            }
            Pageable paginacao = PageRequest.of(pagina, qtde);
            Page<Produto> listaProdutos = produtoRepository.findAll(paginacao);
            
            if(listaProdutos.getContent().size()>0){
                for (int i = 0; i < listaProdutos.getContent().size(); i++){
                    if(listaProdutos.getContent().get(i).getGrupoProduto() != null){
                 //   listaProdutos.getContent().get(i).getGrupoProduto().setListaProdutos(null);
                    }

                }
                return ResponseEntity.ok(listaProdutos);
            }else{
                Error Msg_Erro = new Error("Nenhum registro encontrado. ");
                return new ResponseEntity(Msg_Erro, HttpStatus.NOT_FOUND);
            }


     }

    @Transactional
    @CacheEvict(value = "/produto", allEntries = true)
    @PutMapping("/produto")
    public ResponseEntity<Produto> alteraProduto(@RequestBody Produto produto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator ();
        Set<ConstraintViolation<Produto>> constraintViolations = validator.validate(produto);
        if(constraintViolations.size()>0){
            for (ConstraintViolation error: constraintViolations) {
                String msgError = error.getMessage();
                return new ResponseEntity(new Error(msgError), HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(produtoRepository.save(produto));
    }



    @GetMapping("/produto/{nome}")
    public ResponseEntity<List<Produto>> produtoPorNome(@PathVariable(value="nome") String nome){
        List<Produto> ProdutoPorNome = produtoRepository.findByNome(nome);
        if(ProdutoPorNome.size()<0){
            Error Msg_Erro = new Error("Nenhum registro encontrado. ");
            return new ResponseEntity(Msg_Erro, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ProdutoPorNome);
    }
/*
    @GetMapping("/produto_por_fornecedor")
    public ResponseEntity<String> produtoPorFornecedorTrataErro(){
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("É Preciso informar um parametro de pesquisa após o endpoint");
   }
*/

    @GetMapping("/produto_por_fornecedor")
    public ResponseEntity<ErroDTO> produtoPorFornecedorTrataErro(){
        //Tratamento de excessões
        ErroDTO erroDTO = new ErroDTO();
        erroDTO.setErro("É preciso informar alguma coisa após o endpoint como parametro de pesquisa");
        erroDTO.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(erroDTO);

    }
    @GetMapping("/produto_por_fornecedor/{nome}")
    public ResponseEntity<Page<Produto>> produtoPorFornecedor(@PathVariable(value="nome") String nome,
                                                              @RequestParam(value = "qtde", required=false) Integer qtde,
                                                              @RequestParam(value = "pagina", required=false) Integer pagina){

        if(pagina==null||qtde==null){
            Error Msg_Erro = new Error("Valores de quantidade e numero da pagina precisam ser informados nos parametros Exemplo: /api/produtos?qtde=20&pagina=0 ");
            return new ResponseEntity(Msg_Erro, HttpStatus.BAD_REQUEST);
        }

        //Implementação de Lista com Paginação
        //Implementação de query com Criteria Query
        List<Produto> ProdutoPorFornecedor = produtoService.FornecedorPeloNome(nome);
        Pageable paginacao = PageRequest.of(pagina, qtde);
        Page<Produto> page = new PageImpl<>(ProdutoPorFornecedor,paginacao,ProdutoPorFornecedor.size());
        if(page.getContent().size()>0) {
            return ResponseEntity.ok(page);
        }else{
            Error Msg_Erro = new Error("Não encontrado.");
            return new ResponseEntity(Msg_Erro,HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/produto_por_fornecedor_jpa/{nome}")
    public ResponseEntity<Page<Produto>> produtoPorFornecedorJPA(@PathVariable(value="nome") String nome,
                                                                 @RequestParam(value = "qtde", required=false) Integer qtde,
                                                                 @RequestParam(value = "pagina", required=false) Integer pagina){
          //query com jpa teste

        if(pagina==null||qtde==null){
            Error Msg_Erro = new Error("Valores de quantidade e numero da pagina precisam ser informados nos parametros Exemplo: /api/produtos?qtde=20&pagina=0 ");
            return new ResponseEntity(Msg_Erro, HttpStatus.BAD_REQUEST);
        }

          Pageable paginacao = PageRequest.of(pagina, qtde);
          Page<Produto> listaProdutos = produtoRepository.findByFornecedor(nome,paginacao);
          return ResponseEntity.ok(listaProdutos);

        /*
          Unable to locate Attribute  with the the given name [fornecedor] on this ManagedType [com.cadastro_prod.modelo.Produto];
          Não sei porque ta dando isso ainda, tá tudo certo na model.
         */

    }

}
