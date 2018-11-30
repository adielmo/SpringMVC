package com.algaworks.brewer.repository.helper.venda;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.VendaFilter;

public interface VendasQueries {
	
	public Page<Venda> filtar(VendaFilter vendaFilter, Pageable pageable);
	
	public Venda buscarComItens(Long codigo);
	
	public BigDecimal valorTotalAno();
	public BigDecimal valorTotalMes();
	public BigDecimal valorTicktMedioAno();

}
