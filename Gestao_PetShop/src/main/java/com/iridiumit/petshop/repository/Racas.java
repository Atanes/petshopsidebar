package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iridiumit.petshop.model.Raca;

public interface Racas extends JpaRepository<Raca, Long>, PagingAndSortingRepository<Raca, Long> {
	
	List<Raca> findAllByOrderByNome();
	
	List<Raca> findByEspecieIgnoreCaseOrderByNome(String especie);
	
	Raca findByNomeIgnoreCase(String nome);

	List<Raca> findByNomeContainingIgnoreCaseOrderByNome(String nome);
	
	Page<Raca> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
	Page<Raca> findAll(Pageable pageable);

}
