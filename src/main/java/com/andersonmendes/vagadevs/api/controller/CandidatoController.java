package com.andersonmendes.vagadevs.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	@ResponseStatus(HttpStatus.CREATED)
	public Candidato adicionar(@RequestBody Candidato candidato) {
		return cadastroCandidatoService.salvar(candidato);
	}
	
	@PutMapping("/{candidatoId}")
	public Candidato atualizar(@PathVariable Long candidatoId, @RequestBody Candidato candidato) {
		
		Candidato candidatoAtual = candidatoRepository.findById(candidatoId).orElse(null);	
			
		BeanUtils.copyProperties(candidato, candidatoAtual, "id");
		
		return cadastroCandidatoService.salvar(candidatoAtual);
	}
	
	@DeleteMapping("/{candidatoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long candidatoId) {		
		cadastroCandidatoService.excluir(candidatoId);				
	}
	
}
