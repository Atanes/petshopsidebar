package com.iridiumit.petshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iridiumit.petshop.model.Fornecedor;
import com.iridiumit.petshop.repository.Fornecedores;
import com.iridiumit.petshop.repository.filtros.FornecedorFiltro;

@Service
public class FornecedorService {
	
	@Autowired
	private Fornecedores fornecedores;
		
	public List<Fornecedor> filtrar(FornecedorFiltro filtro) {
		
		if(filtro.getNome() == null) {
			return fornecedores.findAll();
		}else {
			return fornecedores.findByNomeContainingIgnoreCase(filtro.getNome());
		}
	}
	
	public void excluir(Long codigo) {
		fornecedores.delete(codigo);
	}
	
	public Fornecedor localizar(long id){
		return fornecedores.getOne(id);
	}
	
	public Fornecedor localizarCnpj(String cnpj){
		return fornecedores.findByCnpj(cnpj);
	}
	
	public Fornecedor localizarContato(String contato){
		return fornecedores.findByContato(contato);
	}
	
	public void salvar(Fornecedor fornecedor) {
		
		fornecedores.save(fornecedor);
		
	}

}
