package org.senai.ecommerce.service;

import jakarta.transaction.Transactional;
import org.senai.ecommerce.entity.produto.Produto;
import org.senai.ecommerce.entity.produto.ProdutoDTO;
import org.senai.ecommerce.repositororio.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoService{

    private final ProdutoRepositorio produtoRepositorio;

    @Autowired
    public ProdutoService(ProdutoRepositorio produtoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
    }

    public List<Produto> getTodosProdutos() {
        return produtoRepositorio.findAll();
    }

    public Produto getProdutoPorCodigo(Long codigo) {
        Optional<Produto> optionalTask = produtoRepositorio.findById(codigo);
        return optionalTask.orElse(null);
    }

    public void criarProduto(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());

        produtoRepositorio.save(produto);
    }

    public void atualizarProduto(Long codigo, ProdutoDTO dto) {
        Optional<Produto> opcionalProduto = produtoRepositorio.findById(codigo);
        if (opcionalProduto.isPresent()) {
            Produto produto = opcionalProduto.get();
            produto.setNome(dto.getNome());
            produto.setDescricao(dto.getDescricao());
            produto.setPreco(dto.getPreco());
            produto.setEstoque(dto.getEstoque());

            produtoRepositorio.save(produto);
        }
    }

    public void removerProduto(Long codigo) {
        produtoRepositorio.deleteById(codigo);
    }
}
