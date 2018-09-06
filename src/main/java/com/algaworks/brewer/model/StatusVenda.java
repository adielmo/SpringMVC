package com.algaworks.brewer.model;

public enum StatusVenda {
	
	ORCAMENTO("Orcamento"),
	EMITIDA("Emitida"),
	CANCELADA("Cancelada");
	
	private String descricao;
	
	private StatusVenda(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	

}
