package com.api.bancaria.responses;

import java.util.List;

public class Response<T> {
	
	private T dados;
	private List<String> erros;
	
	public Response(T dados) {
		this.dados = dados;
	}
	
	public Response(List<String> erros) {
		this.erros = erros;
	}

	public T getDados() {
		return dados;
	}

	public void setDados(T dados) {
		this.dados = dados;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
	

}
