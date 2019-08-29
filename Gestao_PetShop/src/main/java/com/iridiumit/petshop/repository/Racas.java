package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.petshop.model.Raca;

public interface Racas extends JpaRepository<Raca, Long> {
	
	List<Raca> findAllByOrderByNome();
	
	List<Raca> findByEspecieIgnoreCaseOrderByNome(String especie);
	
	Raca findByNomeIgnoreCase(String nome);

	List<Raca> findByNomeContainingIgnoreCaseOrderByNome(String nome);

}
