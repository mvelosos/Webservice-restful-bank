package com.api.bancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bancaria.model.Conta;

public interface ContaRepo extends JpaRepository<Conta, Long> {

}
