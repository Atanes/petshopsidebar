package com.iridiumit.petshop.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Animal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank (message = "{nome.not.blank}")
	private String nome;
	
	@NotBlank (message = "{sexo.not.blank}")
	private String sexo;
	
	@NotBlank (message = "{raca.not.blank}")
	private String raca;
	
	@NotBlank (message = "{especie.not.blank}")
	private String especie;
	
	private boolean castrado;
	
	private boolean pedigree;
	
	@Column(name="data_nasc")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past (message = "{data_nasc.mustbe.past}")
	@NotNull (message = "{data_nasc.notnull}")
	private Date data_nasc;
	
	private String microship;
	
	private String observacoes;
	
	@Column(name="data_cadastro")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data_cadastro;
	
	private String resp_cadastro;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

	public Animal() {

	}

	public Animal(Long id, String nome, String sexo, String raca, String especie, boolean castrado, boolean pedigree,
			Date data_nasc, String microship, String observacoes, Date data_cadastro, String resp_cadastro,
			Cliente cliente) {
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.raca = raca;
		this.especie = especie;
		this.castrado = castrado;
		this.pedigree = pedigree;
		this.data_nasc = data_nasc;
		this.microship = microship;
		this.observacoes = observacoes;
		this.data_cadastro = data_cadastro;
		this.resp_cadastro = resp_cadastro;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public boolean isCastrado() {
		return castrado;
	}

	public void setCastrado(boolean castrado) {
		this.castrado = castrado;
	}

	public boolean isPedigree() {
		return pedigree;
	}

	public void setPedigree(boolean pedigree) {
		this.pedigree = pedigree;
	}
	
	public String getPedigree(){
		if(this.pedigree){
			return "Sim";
		}
		return "Não";
	}

	public Date getData_nasc() {
		return data_nasc;
	}
	
	public String getDataFormatada(Date data) {
		Date d = data; 
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		return formato.format(d);
	}

	public void setData_nasc(Date data_nasc) {
		this.data_nasc = data_nasc;
	}
	
	public String getIdade() {
		
		final LocalDate dataNasc = ((java.sql.Date) this.data_nasc).toLocalDate();
		
	    final LocalDate dataAtual = LocalDate.now();
	    final Period periodo = Period.between(dataNasc, dataAtual);
	    String idade = periodo.getYears() + " ano(s), " + periodo.getMonths() + " mese(s) e " + periodo.getDays() + " dia(s)";
	    System.out.println(idade);
	    return idade;
	}
	
	public String getMicroship() {
		return microship;
	}

	public void setMicroship(String microship) {
		this.microship = microship;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public String getResp_cadastro() {
		return resp_cadastro;
	}

	public void setResp_cadastro(String resp_cadastro) {
		this.resp_cadastro = resp_cadastro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
