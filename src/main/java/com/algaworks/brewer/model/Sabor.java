package com.algaworks.brewer.model;

public enum Sabor {
	
	ADOCICADA("Adocicada"),
	AMARGA("Amarga"),
	FORTE("Forte"),
	SUAVE("Suave");
	
	
	private String descricao;
	
	Sabor(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		
		return this.descricao;
	}

}
