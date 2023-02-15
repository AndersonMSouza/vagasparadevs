package com.andersonmendes.vagadevs.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andersonmendes.vagadevs.domain.model.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long>{

	List<Vaga> findByNomeContaining(String nome);
	
}
