package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iridiumit.petshop.model.Animal;
import com.iridiumit.petshop.model.Cliente;
import com.iridiumit.petshop.model.Raca;

public interface Animais extends JpaRepository<Animal, Long>, PagingAndSortingRepository<Animal, Long>{
	
	List<Animal> findByNome(String nome);
	
	List<Animal> findByCliente (Cliente c);

	Page<Animal> findByNomeContainingIgnoreCaseOrderByNome(String nome, Pageable pageable);
	
	Page<Animal> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
	List<Animal> findByRaca(Raca raca);
	
	Page<Animal> findAll(Pageable pageable);

}
