package com.api.bancaria.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.api.componentes.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Transacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtransacao")
	private Long idTransacao;
	
	@ManyToOne
	@JoinColumn(name = "idconta")
	private Conta idConta;
	
	private BigDecimal valor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datatransacao")
	@Generated(GenerationTime.INSERT)
	private Date dataTransacao;

	public Long getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(Long idTransacao) {
		this.idTransacao = idTransacao;
	}

	public Conta getIdConta() {
		return idConta;
	}

	public void setIdConta(Conta idConta) {
		this.idConta = idConta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	
	
}
