package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.petshop.model.Fornecedor;
import com.iridiumit.petshop.model.Produto;

public interface Produtos extends JpaRepository<Produto, Long>{

	List<Produto> findByFornecedor(Fornecedor f);
	
	List<Produto> findByFornecedorAndDescricaoContainingIgnoreCase(Fornecedor f, String descricao);
	
	List<Produto> findByDescricao(String descricao);
	
	List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
}
