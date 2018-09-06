package com.algaworks.brewer.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.validator.VendaValidator;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.security.UsuarioSistema;
import com.algaworks.brewer.service.CadastroVendaService;
import com.algaworks.brewer.session.TabelasItensSession;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private CadastroVendaService cadastroVendaService;
	
	@Autowired
	private TabelasItensSession tabelasItens;
	
	@Autowired
	private VendaValidator vendaValidator;
	
	@InitBinder
	public void inicializarValidator(WebDataBinder binder){
		binder.setValidator(vendaValidator);
	}
	
		
	@GetMapping("/novo")
	public ModelAndView novo(Venda venda){
		ModelAndView mv =  new ModelAndView("venda/CadastroVenda");
		
		if (StringUtils.isEmpty(venda.getUuid())) {
			venda.setUuid( UUID.randomUUID().toString());
		}
		
	   mv.addObject("itens",venda.getItens());
	   mv.addObject("valorFrete", venda.getValorFrete());
	   mv.addObject("valorDesconto", venda.getValorDesconto());
	   mv.addObject("valorTotalItens", tabelasItens.getValorTotal(venda.getUuid()));
	   
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		venda.adicionarItens(tabelasItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();
		
		vendaValidator.validate(venda, result);
		if (result.hasErrors()) {
			return novo(venda);
		}
		venda.setUsuario(usuarioSistema.getUsuario());
		
		
		cadastroVendaService.salvar(venda);
	 attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
		return new ModelAndView("redirect:/vendas/novo");
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
	
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelasItens.adicionarItem(uuid, cerveja, 1);
		return mvTabelaItensVenda(uuid);
	}
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidade(@PathVariable("codigoCerveja") Cerveja cerveja,
			Integer quantidade, String uuid) {
		
		tabelasItens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		return mvTabelaItensVenda(uuid);
		
	}
	
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja,
			@PathVariable String uuid) {		
		tabelasItens.excluirItem(uuid, cerveja);
		
		return mvTabelaItensVenda(uuid);
		
	}

	private ModelAndView mvTabelaItensVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVendas");
		mv.addObject("itens", tabelasItens.getItens(uuid));
		mv.addObject("valorTotal", tabelasItens.getValorTotal(uuid));
		return mv;
	}

}
