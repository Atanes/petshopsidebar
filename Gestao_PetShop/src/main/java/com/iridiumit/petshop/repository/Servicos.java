package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.petshop.model.Servico;

public interface Servicos extends JpaRepository<Servico, Long> {
	
	List<Servico> findAllByOrderByDescricao();

}
