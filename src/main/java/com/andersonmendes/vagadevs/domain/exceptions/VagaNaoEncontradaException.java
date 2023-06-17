package com.andersonmendes.vagadevs.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VagaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public VagaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public VagaNaoEncontradaException(Long vagaId) {
		this(String.format("Não existe um cadastro de vaga com código %d", vagaId));
	}
	
}