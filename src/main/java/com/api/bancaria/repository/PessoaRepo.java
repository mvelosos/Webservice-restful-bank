package com.api.bancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bancaria.model.Pessoa;

/**
 * Essa interface é um Repository de Pessoa, feita para efetuar as operações com a entidade no banco de dados.
 * 
 * @author mateusveloso
 *
 */
public interface PessoaRepo extends JpaRepository<Pessoa, Long> {

}
