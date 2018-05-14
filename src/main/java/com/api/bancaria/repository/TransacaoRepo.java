package com.api.bancaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bancaria.model.Conta;
import com.api.bancaria.model.Transacao;

/**
 * Essa interface é um Repository de Transacao, feita para efetuar as operações com a entidade no banco de dados.
 * 
 * @author mateusveloso
 *
 */
public interface TransacaoRepo extends JpaRepository<Transacao, Long> {

	List<Transacao> findByIdConta(Conta idConta);
	
}
