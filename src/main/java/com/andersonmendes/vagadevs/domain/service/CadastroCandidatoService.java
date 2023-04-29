package com.andersonmendes.vagadevs.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.andersonmendes.vagadevs.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.vagadevs.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.vagadevs.domain.model.Candidato;
import com.andersonmendes.vagadevs.domain.repository.CandidatoRepository;

@Service
public class CadastroCandidatoService {
	
	private static final String MSG_CANDIDATO_EM_USO 
	= "Candidato(a) de código %d não pode ser removido(a), pois está em uso!";

	private static final String MSG_CANDIDATO_NAO_ENCONTRADO 
	= "Não existe candidato(a) cadastrado(a) com o código %d";

	@Autowired
	private CandidatoRepository candidatoRepository;
	
	public Candidato salvar(Candidato candidato) {
		return candidatoRepository.save(candidato);
	}
	
	public void excluir(Long candidatoId) {
		try {
			candidatoRepository.deleteById(candidatoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format(MSG_CANDIDATO_NAO_ENCONTRADO, candidatoId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_CANDIDATO_EM_USO, candidatoId));
		}
	}
	
	public Candidato buscarOuFalhar(@PathVariable Long candidatoId) {
		return candidatoRepository.findById(candidatoId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_CANDIDATO_NAO_ENCONTRADO,candidatoId)));
	}
	
}
