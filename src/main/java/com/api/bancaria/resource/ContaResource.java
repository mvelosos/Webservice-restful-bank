package com.api.bancaria.resource;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.bancaria.model.Conta;
import com.api.bancaria.repository.ContaRepo;
import com.api.bancaria.responses.Response;


@RestController
@RequestMapping("/conta")
public class ContaResource {
	
	@Autowired
	private ContaRepo contaRepo;
	
	
	@GetMapping("/buscarContas")
	public ResponseEntity<Response<List<Conta>>> buscarContas(){

		return ResponseEntity.ok(new Response<List<Conta>>(contaRepo.findAll())); 
	}
	
	@PostMapping("/novaConta")
	public ResponseEntity<Response<Conta>> novaConta(@Valid @RequestBody Conta conta, BindingResult result) {
		if(result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Conta>(erros));
		}
		contaRepo.save(conta);
		return ResponseEntity.ok(new Response<Conta>(conta));
	}
	
	@GetMapping("/buscarContas/{idConta}")
	public ResponseEntity<Response<?>> buscarPorId(@PathVariable Long idConta){
		Conta conta = contaRepo.findOne(idConta);
		if(conta == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new Response<Conta>(contaRepo.findOne(idConta))); 
	}
	
	@PutMapping("/bloquearConta/{idConta}")
	public ResponseEntity<?> bloquearConta(@PathVariable Long idConta){
		Conta conta = contaRepo.findOne(idConta);
		if(conta == null) {
			return ResponseEntity.notFound().build();
		}
		if(conta.getFlagAtivo() == true) {
			conta.setFlagAtivo(false);
		}else {
			conta.setFlagAtivo(true);
		}
		contaRepo.save(conta);
		
		return ResponseEntity.status(HttpStatus.OK).body(conta);
	}
	
	@GetMapping("/saldo/{idConta}")
	public ResponseEntity<?> saldoConta(@PathVariable Long idConta){
		Conta conta = contaRepo.findOne(idConta);
		if(conta != null) {
			BigDecimal saldo = conta.getSaldo();
			return ResponseEntity.status(HttpStatus.OK).body("idConta: "+conta.getIdConta()
					+"\nNome: "+conta.getIdPessoa().getNome()+"\nSaldo: "+saldo);
		}
		return ResponseEntity.notFound().build();
	}

}
