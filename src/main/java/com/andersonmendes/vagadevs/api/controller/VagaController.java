package com.andersonmendes.vagadevs.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmendes.vagadevs.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.vagadevs.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.vagadevs.domain.model.Vaga;
import com.andersonmendes.vagadevs.domain.repository.VagaRepository;
import com.andersonmendes.vagadevs.domain.service.CadastroVagaService;

@RestController
@RequestMapping("/vagas")
public class VagaController {

	@Autowired
	private VagaRepository vagaRepository;
	
	@Autowired
	private CadastroVagaService cadastroVagaService;
	
	@GetMapping
	public List<Vaga> listar() {
		return vagaRepository.findAll();
	}
	
	@GetMapping("/vaga-por-nome")
	public List<Vaga> porNome(String nome) {
		return vagaRepository.findByNomeContaining(nome);
	}	
	
	@GetMapping("/{vagaId}")
	public ResponseEntity<Vaga> buscar(@PathVariable Long vagaId) {
		Optional<Vaga> vaga = vagaRepository.findById(vagaId);
	
		if (vaga.isPresent()) {
			return ResponseEntity.ok(vaga.get()); 
		}
		
		return ResponseEntity.notFound().build();	
	}	
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Vaga vaga) {
		try {
			vaga = cadastroVagaService.salvar(vaga);
			return ResponseEntity.status(HttpStatus.CREATED).body(vaga);
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{vagaId}")
	public ResponseEntity<?> atualizar(@PathVariable Long vagaId, @RequestBody Vaga vaga) {
		try {
			Optional<Vaga> vagaAtual = vagaRepository.findById(vagaId);
			
			if (vagaAtual.isPresent()) {
				BeanUtils.copyProperties(vaga, vagaAtual.get(), "id");
				Vaga vagaSalva = cadastroVagaService.salvar(vagaAtual.get());
				return ResponseEntity.ok(vagaSalva);
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{vagaId}")
	public ResponseEntity<Vaga> remover(@PathVariable Long vagaId) {
		try {
			cadastroVagaService.excluir(vagaId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}		
	}
	
	
}
