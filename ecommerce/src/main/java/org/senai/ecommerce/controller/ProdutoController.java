package org.senai.ecommerce.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.senai.ecommerce.entity.produto.Produto;
import org.senai.ecommerce.entity.produto.ProdutoDTO;
import org.senai.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@SecurityRequirement(name = "Bearer Authentication")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista com todos os produtos adicionados")
    public List<Produto> getTodosProdutos() {
        return produtoService.getTodosProdutos();
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Retorna um produto com base em seu codigo")
    public ResponseEntity<?> getProdutoPorCodigo(@PathVariable Long codigo) {
        return new ResponseEntity<>(produtoService.getProdutoPorCodigo(codigo), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Insere um produto novo")
    public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO dto) {
        produtoService.criarProduto(dto);
        return new ResponseEntity<>("Produto criado", HttpStatus.CREATED);

    }

    @PutMapping("/{codigo}")
    @Operation(summary = "Altera um produto(já adiocionado) por seu codigo")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long codigo, @RequestBody ProdutoDTO dto) {
        produtoService.atualizarProduto(codigo, dto);
        return new ResponseEntity<>("Produto atualizado", HttpStatus.OK);
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Remove um produto com base no seu codigo")
    public ResponseEntity<?> removerProduto(@PathVariable Long codigo) {
        produtoService.removerProduto(codigo);
        return new ResponseEntity<>("Produto removido", HttpStatus.OK);
    }
}
