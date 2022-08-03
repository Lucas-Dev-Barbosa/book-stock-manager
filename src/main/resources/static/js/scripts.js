/**
 * Scripts executados pela aplicação
 */

const regex = /^[a-z0-9.]+@[a-z0-9]+\.[a-z]+(\.([a-z]+))?$/;

var url = '';

$('button[id*="btn_"]').click(function() {
	url = "/" + $(this).attr('id').split("_")[1];
});

$('#ok_confirm').click(function() {
	document.location.href = url;
});

$(function() {
	$('[data-toggle="popover"]').popover();
});

$(document).ready(function() {
	$(".navbar-toggle").click(function() {
		$(".sidebar").toggleClass("sidebar-open");
	})
});

//Para controlar os ícones dos menus do sidebar
//$(".nav-link").click(function() {
//	var seletor = 'i[class*="oi-caret-"]';
//
//	$(this).find(seletor).toggleClass("oi-caret-right").toggleClass("oi-caret-bottom");
//});


/*
	Validação básica cliente-side do formulário de usuario, 
	tanto para login quanto para cadastro de usuario.
*/
function controlaClasseValidacao(element, isValid) {
	if (isValid) {
		$(element).removeClass("is-invalid");
		$(element).addClass("is-valid");
	} else {
		$(element).removeClass("is-valid");
		$(element).addClass("is-invalid");
	}
	
	return isValid;
}

//EMAIL
function validaEmail() {
	var email = $("#emailLogin").val();
	if (!email || !regex.exec(email)) {
		return false;
	}
	return true;
}

$("#emailLogin").keyup(function() {
	if(controlaClasseValidacao(this, validaEmail())) {
		$("#emailError").attr('hidden', 'true');
	}
});

//SENHA
function validaSenha() {
	var senha = $("#passwordLogin").val();
	var idUsuario = $("#id-usuario");

	if (!senha || senha.length < 5 || senha.length > 8) {
		if(idUsuario.val()) {
			return true;
		}
		return false;
	}

	return true;
}

$("#passwordLogin").keyup(function() {
	if(controlaClasseValidacao(this, validaSenha())) {
		$("#passWordError").attr('hidden', 'true')
	}
});

//NOME
function validaNome() {
	var nome = $("#nome").val();
	if (!nome) {
		return false;
	}
	return true;
}

$("#nome").keyup(function() {
	if(controlaClasseValidacao(this, validaNome())) {
		$("#nomeError").attr('hidden', 'true');
	}
});

//SOBRENOME
function validaSobreNome() {
	var sobrenome = $("#sobrenome").val();
	if (!sobrenome) {
		return false;
	}
	return true;
}

$("#sobrenome").keyup(function() {
	if(controlaClasseValidacao(this, validaSobreNome())) {
		$("#sobrenomeError").attr('hidden', 'true');
	}
});

//PERFIL
function validaPerfil() {
	var perfil = $("#perfil").val();
	if (!perfil || perfil == 0) {
		return false;
	}
	return true;
}

$("#perfil").change(function() {
	if(validaPerfil()) {
		$("#perfilError").attr('hidden', 'true');
	}
});


/*
	Função generica para chamar PreventDefault e mostrar mensagem de erro 
	caso o campo não esteja valido
*/
function validaPreSubmit(validaCampoFunction, elementErrorId, event){
	if(!validaCampoFunction()) {
		event.preventDefault();
		
		$(elementErrorId).removeAttr('hidden');
	}
}

//Submit formulario login
$("#login").submit(function(event) {
	validaPreSubmit(validaEmail, "#emailError", event);
	validaPreSubmit(validaSenha, "#passWordError", event);
});

//Submit formulario de cadastro de usuario
$("#cadastroUsuario").submit(function(event) {
	validaPreSubmit(validaNome, "#nomeError", event);
	validaPreSubmit(validaSobreNome, "#sobrenomeError", event);
	validaPreSubmit(validaEmail, "#emailError", event);
	validaPreSubmit(validaSenha, "#passWordError", event);
	validaPreSubmit(validaPerfil, "#perfilError", event);
});


//Controle dos modais da aplicacao
function controleModal(){
	var modal = document.querySelector('div[id*=modal-js]');
	
	if(modal){
		const myModal = new bootstrap.Modal(modal);
		myModal.show();	
	}
}
controleModal();

//Validacao de disponibilidade de estoque no formulario de cadastro de livro
$("#emEstoque").change(function() {
	var quantidade = $(this).val();
	alteraStatusDisponibilidade(quantidade);
});

$("#emEstoque").ready(function() {
	var quantidade = $("#emEstoque").val(); //Foi necessario obter o elemento novamente pois seu valor estava ficando vazio
	alteraStatusDisponibilidade(quantidade);
});

function alteraStatusDisponibilidade(quantidade){
	var status = $("#statusDisponibilidade");
	
	if(quantidade > 0){
		status.text("Produto Disponível");
	} else if(quantidade == 0) {
		console.log('Quantidade ' + quantidade);
		status.text("Produto Indisponível");
	} else {
		status.text("");
	}
}