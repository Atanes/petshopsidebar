package com.iridiumit.petshop.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
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
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank (message = "{descricaoproduto.not.blank}")
	private String descricao;
	
	@NotBlank (message = "{tipoproduto.not.blank}")
	private String tipo;
	
	@NotBlank (message = "{unidademedida.not.blank}")
	private String unidadeMedida;
	
	@NotNull (message = "{qtdproduto.not.null}")
	private Float qtd;
	
	@NotNull (message = "{valorcompra.not.null}")
	private Double valorCompra;
	
	@NotNull (message = "{valorvenda.not.null}")
	private Double valorVenda;
	
	@Column(name="data_validade")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future (message = "{data_validade.mustbe.future}")
	private Date data_validade;
	
	private String lote;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

	public Produto(){
		
	}
	
	public Produto(Long id, String descricao, String tipo, String unidadeMedida, Float qtd, Double valorCompra,
			Double valorVenda, Date data_validade, String lote, Fornecedor fornecedor) {
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.unidadeMedida = unidadeMedida;
		this.qtd = qtd;
		this.valorCompra = valorCompra;
		this.valorVenda = valorVenda;
		this.data_validade = data_validade;
		this.lote = lote;
		this.fornecedor = fornecedor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Float getQtd() {
		return qtd;
	}

	public void setQtd(Float qtd) {
		this.qtd = qtd;
	}

	public Double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(String valorCompra) {
		this.valorCompra = Double.valueOf(valorCompra.replace(",", "."));
	}

	public Double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(String valorVenda) {
		this.valorVenda = Double.valueOf(valorVenda.replace(",", "."));;
	}

	public String convertValorToMoney(Double valor){
		DecimalFormat df = new DecimalFormat("###,###.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		return "R$ " + df.format(valor);
	}
	
	public Date getData_validade() {
		return data_validade;
	}

	public void setData_validade(Date data_validade) {
		this.data_validade = data_validade;
	}
	
	public String getDataFormatada(Date data) {
		Date d = data; 
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		return formato.format(d);
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
	
	
}
