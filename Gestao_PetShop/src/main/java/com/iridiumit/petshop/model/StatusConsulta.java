package com.iridiumit.petshop.model;

public enum StatusConsulta {
	
	AGENDADA("Agendada"),
	AGUARDANDO("Aguardando"),
	ATENDIMENTO("Em atendimento"), 
	CANCELADA("Cancelada"),
	CONFIRMADA("Confirmada"),
	FALTOU("Faltou"),
	FINALIZADA("Finalizada");
	
	private String descricao;
	
	StatusConsulta(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
