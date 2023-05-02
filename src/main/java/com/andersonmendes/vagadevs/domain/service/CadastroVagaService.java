package com.andersonmendes.vagadevs.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.andersonmendes.vagadevs.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.vagadevs.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.vagadevs.domain.model.Vaga;
import com.andersonmendes.vagadevs.domain.repository.VagaRepository;

@Service
public class CadastroVagaService {
	
	@Autowired
	private VagaRepository vagaRepository;

	public Vaga salvar(Vaga vaga) {
		return vagaRepository.save(vaga);
	}
	
	public void excluir(Long vagaId) {
		try {
			vagaRepository.deleteById(vagaId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe loja cadastrada com o código %d", vagaId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Loja de código %d não pode ser removida, pois está em uso!", vagaId));
		}
	}
	
	public Vaga buscarOuFalhar(@PathVariable Long vagaId) {
		return vagaRepository.findById(vagaId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe loja cadastrada com o código %d", vagaId)));
	}
	
}
