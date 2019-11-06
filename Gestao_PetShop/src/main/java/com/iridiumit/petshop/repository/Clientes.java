package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iridiumit.petshop.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long>, PagingAndSortingRepository<Cliente, Long>{

	List<Cliente> findByNomeContainingIgnoreCaseAndAtivo(String nome, boolean ativo);
	
	Page<Cliente> findByNomeContainingIgnoreCaseAndAtivo(String nome, boolean ativo, Pageable pageable);
	
	Cliente findByCpf(String cpf);
	
	List<Cliente> findByAtivo(boolean ativo);
	
	Page<Cliente> findByAtivo(boolean ativo, Pageable pageable);
	
	Page<Cliente> findAll(Pageable pageable);

}
