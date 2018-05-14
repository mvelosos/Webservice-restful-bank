package com.api.bancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bancaria.model.Conta;

/**
 * Essa interface é um Repository de Conta, feita para efetuar as operações com a entidade no banco de dados.
 * 
 * @author mateusveloso
 *
 */
public interface ContaRepo extends JpaRepository<Conta, Long> {

}
