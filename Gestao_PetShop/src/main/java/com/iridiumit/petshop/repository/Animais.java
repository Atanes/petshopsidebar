package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.petshop.model.Animal;
import com.iridiumit.petshop.model.Cliente;
import com.iridiumit.petshop.model.Raca;

public interface Animais extends JpaRepository<Animal, Long>{
	
List<Animal> findByNome(String nome);
	
	List<Animal> findByCliente (Cliente c);

	List<Animal> findByNomeContainingIgnoreCaseOrderByNome(String nome);
	
	List<Animal> findByRaca(Raca raca);

}
