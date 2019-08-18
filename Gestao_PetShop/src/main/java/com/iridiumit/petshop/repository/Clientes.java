package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.petshop.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long>{

	List<Cliente> findByNomeContainingIgnoreCaseAndAtivo(String nome, boolean ativo);
	
	Cliente findByCpf(String cpf);
	
	List<Cliente> findByAtivo(boolean ativo);

}
