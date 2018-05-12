package com.api.bancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bancaria.model.Pessoa;

public interface PessoaRepo extends JpaRepository<Pessoa, Long> {

}
