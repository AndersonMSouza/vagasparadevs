package com.andersonmendes.vagadevs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andersonmendes.vagadevs.domain.model.Candidato;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long>{

	//List<Candidato> findByNomeContaining(String nomeCandidato);
	
}
