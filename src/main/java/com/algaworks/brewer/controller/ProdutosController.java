package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/produto")
public class ProdutosController {
	

	@RequestMapping("/novo")
	public String produto() {
		return "cerveja/cadastro-produto";
		
	}
	

}
