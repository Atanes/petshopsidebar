package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.petshop.model.Animal;
import com.iridiumit.petshop.model.Cliente;

public interface Animais extends JpaRepository<Animal, Long>{
	
	List<Animal> findByNome(String nome);
	
	List<Animal> findByCliente (Cliente c);

	List<Animal> findByNomeContainingIgnoreCase(String nome);
	
	List<Animal> findByRaca(String raca);

}
