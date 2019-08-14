package com.iridiumit.petshop.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Fornecedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank (message = "{nomeFornecedor.not.blank}")
	private String nome;
	
	@NotBlank (message = "{cnpjFornecedor.not.blank}")
	private String cnpj;
	
	private String ie;
	
	@NotBlank(message = "{telefoneFornecedor.not.blank}")
	private String telefone;
	
	@NotBlank(message = "{celularFornecedor.not.blank}")
	private String celular;
	
	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.not.valid}")
	private String email;
	
	private String site;
	
	@NotBlank(message = "{contatoFornecedor.not.blank}")
	private String contato;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "fornecedor")
    private Set<Produto> produto = new HashSet<>();
	
	public Fornecedor(){
		
	}

	public Fornecedor(Long id, String nome, String cnpj, String ie, String telefone, String celular, String email,
			String site, String contato, Set<Produto> produto) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.ie = ie;
		this.telefone = telefone;
		this.celular = celular;
		this.email = email;
		this.site = site;
		this.contato = contato;
		this.produto = produto;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Set<Produto> getProduto() {
		return produto;
	}

	public void setProduto(Set<Produto> produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}