Brewer = Brewer || {};

Brewer.DialogoExcluir = (function(){
	
	function DialogoExcluir(){
		this.exclusaoBtn = $('.js-exclusao-btn');
		
	}
	
	DialogoExcluir.prototype.iniciar = function(){
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
		   
		if (window.location.search.indexOf('excluido') > -1) {
			swal('Pronto!', 'Excluido com sucesso', 'success');
		}
	}
	
	function onExcluirClicado(evento){
		event.preventDefault();
		
		var boataoClicado = $(evento.currentTarget);
		var url = boataoClicado.data('url');
		var objeto = boataoClicado.data('objeto');
		
		swal({
			title:"Tem certeza?",
			text:'Excluir "' + objeto + ' "? Você não pode recuperar depois',
			showCancelButton: true,
			confimButtonColor:"#DD6B55",			
			confinButtonText:"Sim, exclua agora!",
			closeOnConfirm: false,
						
		}, onExcluirConfirmado.bind(this, url));
		
	}
	
		function onExcluirConfirmado(url){
			
			$.ajax({
				url: url,
				method:'DELETE',
				success: onExcluidoSucesso.bind(this),
				error: onErrorExcluir.bind(this)
			});
			
		}		
				
	
	
	function onExcluidoSucesso(){
		
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&': '?';
		var novoUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novoUrl;
	}
	
	function onErrorExcluir(e){
		swal('Oops!', e.responseText, 'error');
	}
	
	return DialogoExcluir;
	
	
}());
	
$(function(){
	var dialogo  = new Brewer.DialogoExcluir();
	dialogo.iniciar();
		
	});
	
	
