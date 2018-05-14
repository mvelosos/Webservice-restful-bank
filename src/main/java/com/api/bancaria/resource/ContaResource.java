package com.api.bancaria.resource;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.bancaria.model.Conta;
import com.api.bancaria.repository.ContaRepo;


@RestController
@RequestMapping("/conta")
public class ContaResource {
	
	@Autowired
	private ContaRepo contaRepo;
	
	
	@GetMapping("/buscarContas")
	public ResponseEntity<List<Conta>> buscarContas(){
		return new ResponseEntity<List<Conta>>(contaRepo.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/novaConta")
	public ResponseEntity<Conta> novaConta(@RequestBody Conta conta) {
		contaRepo.save(conta);
		
		return new ResponseEntity<Conta>(conta, HttpStatus.CREATED);
	}
	
	@GetMapping("/buscarContas/{idConta}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long idConta){
		Conta conta = contaRepo.findOne(idConta);
		if(conta == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(conta);
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
