package com.andersonmendes.vagadevs.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.andersonmendes.vagadevs.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.vagadevs.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.vagadevs.domain.model.Candidato;
import com.andersonmendes.vagadevs.domain.repository.CandidatoRepository;

@Service
public class CadastroCandidatoService {

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
				String.format("Não existe candidato(a) cadastrado(a) com o código %d", candidatoId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Candidato(a) de código %d não pode ser removido(a), pois está em uso!", candidatoId));
		}
	}
	
}
