package com.iridiumit.petshop.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank (message = "{nome.not.blank}")
	private String nome;
	
	@NotBlank(message = "{CPF.not.blank}")
	private String cpf;
	
	private String rg;
	
	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.not.valid}")
	private String email;
	
	@NotBlank(message = "{telefone1.not.blank}")
	private String telefone1;
	
	private String telefone2;
	
	@NotBlank (message = "{sexo.not.blank}")
	private String sexo;
	
	@Column(name="data_nasc")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull (message = "{data_nasc.notnull}")
	@Past (message = "{data_nasc.mustbe.past}")
	private Date data_nasc;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "cliente")
    private Set<Animal> animal = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;
	
	private boolean ativo;
	
	public Cliente (){
		
	}

	public Cliente(Long id, String nome, String cpf, String email, String telefone1, String telefone2, String sexo,
			String rg, Date data_nasc, Set<Animal> animal, Endereco endereco, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
		this.sexo = sexo;
		this.rg = rg;
		this.data_nasc = data_nasc;
		this.animal = animal;
		this.endereco = endereco;
		this.ativo = true;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone) {
		this.telefone1 = telefone;
	}
	
	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String celular) {
		this.telefone2 = celular;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
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

	public Set<Animal> getAnimal() {
		return animal;
	}

	public void setAnimal(Set<Animal> animal) {
		this.animal = animal;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	
}