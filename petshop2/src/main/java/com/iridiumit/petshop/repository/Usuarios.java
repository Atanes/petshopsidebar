package com.iridiumit.petshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.petshop.model.Permissao;
import com.iridiumit.petshop.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);
	
	Usuario findByCpf(String cpf);
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	List<Usuario> findByPermissoes(Permissao p);
}
