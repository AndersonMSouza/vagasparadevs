package com.andersonmendes.vagadevs.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CandidatoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CandidatoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CandidatoNaoEncontradoException(Long candidatoId) {
		this(String.format("Não existe um cadastro de candidato com código %d", candidatoId));
	}
	
}