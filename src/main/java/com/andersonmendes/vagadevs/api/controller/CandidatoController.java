package com.andersonmendes.vagadevs.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmendes.vagadevs.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.vagadevs.domain.model.Candidato;
import com.andersonmendes.vagadevs.domain.repository.CandidatoRepository;
import com.andersonmendes.vagadevs.domain.service.CadastroCandidatoService;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

	@Autowired
	private CandidatoRepository candidatoRepository;
	
	@Autowired
	private CadastroCandidatoService cadastroCandidatoService;
	
	@GetMapping
	public List<Candidato> listar() {
		return candidatoRepository.findAll();
	}
		
	@GetMapping("/{candidatoId}")
	public Candidato buscar(@PathVariable Long candidatoId) {
		return cadastroCandidatoService.buscarOuFalhar(candidatoId);
	}	
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Candidato candidato) {
		try {
			candidato = cadastroCandidatoService.salvar(candidato);
			return ResponseEntity.status(HttpStatus.CREATED).body(candidato);
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{candidatoId}")
	public ResponseEntity<?> atualizar(@PathVariable Long candidatoId, @RequestBody Candidato candidato) {
		try {
			Optional<Candidato> candidatoAtual = candidatoRepository.findById(candidatoId);
			
			if (candidatoAtual.isPresent()) {
				BeanUtils.copyProperties(candidato, candidatoAtual.get(), "id");
				Candidato candidatoSalvo = cadastroCandidatoService.salvar(candidatoAtual.get());
				return ResponseEntity.ok(candidatoSalvo);
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{candidatoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long candidatoId) {		
		cadastroCandidatoService.excluir(candidatoId);				
	}
	
}
