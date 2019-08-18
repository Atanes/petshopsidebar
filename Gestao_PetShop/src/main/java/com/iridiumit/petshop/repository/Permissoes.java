package com.iridiumit.petshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.petshop.model.Permissao;

public interface Permissoes extends JpaRepository<Permissao, Long>{
	
	Permissao findByNome(String nome);

}
