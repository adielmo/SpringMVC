package com.algaworks.brewer.repository.helper.venda;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.groovy.control.io.StringReaderSource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.VendaFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class VendasImpl implements VendasQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
    @SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly= true)
	public Page<Venda> filtar(VendaFilter filtro, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filtro, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
    
    @Transactional(readOnly = true)
	@Override
	public Venda buscarComItens(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Venda) criteria.uniqueResult();
	}
	
	private Long total(VendaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(VendaFilter filtro, Criteria criteria) {
		  criteria.createAlias("cliente", "c");
		
		if (!StringUtils.isEmpty(filtro.getCodigo())) {
			criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
		}
		
		if (!StringUtils.isEmpty(filtro.getStatus())) {
			criteria.add(Restrictions.eq("status", filtro.getStatus()));
		}
		
		if (filtro.getDataDe() != null) {
			LocalDateTime desde = LocalDateTime.of(filtro.getDataDe(),  LocalTime.of(0, 0));
			criteria.add(Restrictions.ge("dataCriacao", desde));
			
		}
		
		if (filtro.getDataAte() != null) {
			LocalDateTime ate = LocalDateTime.of(filtro.getDataAte(), LocalTime.of(23, 59));
			criteria.add(Restrictions.le("dataCriacao", ate));
			
		}
		
		if (filtro.getValorDe() != null) {
			criteria.add(Restrictions.ge("valorTotal", filtro.getValorDe()));
				
			}
		
		if (filtro.getValorAte() != null) {
			criteria.add(Restrictions.le("valorTotal", filtro.getValorAte()));
			
		}
		
		if (!StringUtils.isEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
	if (!StringUtils.isEmpty(filtro.getCpfOuCnpj())) {
			criteria.add(Restrictions.eq("c.cpfOuCnpj", filtro.getCpfOuCnpjSemFormatacao()));
		}  
		
		}


		
	}


