package com.andersonmendes.vagadevs.domain.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RelacaoCandidatoVaga {

	
	private Candidato candidato;
	
	private Vaga vaga;
}
