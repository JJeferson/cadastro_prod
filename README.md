# cadastro_prod
 
Projeto criado para testar e implementar do zero Criteria junto com JPA.</br>
Aproveitei para testar relacionamento de tabelas e paginação em endpoints GET.</br>
</br></br>
Como Implementar:</br>
-Criar banco mysql de nome: cadastro_prod</br>
Deve ter user root e sem senha.</br>
</br></br>
A api irá rodar na porta 8000.</br>
Pode conferir os endpoints corretamente nas classes de Controller.</br>
</br></br>
-[Get]Endpoint do criteria:</br>
http://localhost:8000/api/produto_por_fornecedor/nome de fornecedor</br>
-[Post]novo produto:  http://localhost:8000/api/produto</br>
Json de Exemplo:</br>
 {</br>
   "nome":"teste novo registro",</br>
   "fornecedor":"teste"</br>
 }</br>
 -[Post]-Novo Grupo de Produtos:</br>
 http://localhost:8000/api/grupoproduto</br>
 Json de Exemplo:</br>
 {</br>
   "nome":"teste grupo com itens na lista1",</br>
   "listaProdutos":[</br>
       {"produtoId":37},</br>
       {"produtoId":38},</br>
       {"produtoId":39}</br>
   ]</br>
}</br>
</br></br>
-[Get] - Paginado.</br>
Exemplo de GET: http://localhost:8000/api/produtos?qtde=20&pagina=0</br></br>
