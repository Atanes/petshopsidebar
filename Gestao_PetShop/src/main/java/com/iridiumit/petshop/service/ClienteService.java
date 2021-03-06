package com.iridiumit.petshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iridiumit.petshop.model.Cliente;
import com.iridiumit.petshop.repository.Clientes;

@Service
public class ClienteService{
	
	@Autowired
	private Clientes clientes;
	
	public void excluir(Long codigo) {
		clientes.delete(codigo);
	}
	
	public Cliente localizar(Long id){
		return clientes.getOne(id);
	}
	
	public Cliente localizarLogin(String cpf){
		return clientes.findByCpf(cpf);
	}
	
	public void incluir(Cliente cliente){
               
        clientes.save(cliente);
    }
	
	public void salvar(Cliente cliente) {
		
		clientes.save(cliente);
		
	}
	
}
