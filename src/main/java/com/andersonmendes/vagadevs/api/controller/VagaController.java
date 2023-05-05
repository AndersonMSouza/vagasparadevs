package com.andersonmendes.vagadevs.api.controller;

import java.util.List;

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
	public Vaga buscar(@PathVariable Long vagaId) {
		return cadastroVagaService.buscarOuFalhar(vagaId);
	}	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Vaga adicionar(@RequestBody Vaga vaga) {
		return cadastroVagaService.salvar(vaga);
	}
	
	@PutMapping("/{vagaId}")
	public Vaga atualizar(@PathVariable Long vagaId, @RequestBody Vaga vaga) {
		Vaga vagaAtual = cadastroVagaService.buscarOuFalhar(vagaId);
		
		BeanUtils.copyProperties(vaga, vagaAtual, "id");
		
		return cadastroVagaService.salvar(vagaAtual);
				
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
