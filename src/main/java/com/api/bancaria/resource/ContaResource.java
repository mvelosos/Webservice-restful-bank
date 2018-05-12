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
	
//		private List<Conta> listaContas;
//		
//		public ContaResource(){
//			listaContas = new ArrayList<>();
//			
//			Conta c1 = new Conta();
//			c1.setIdConta(new Long(10));
//			c1.setIdPessoa(new Pessoa());
//			c1.setSaldo(new BigDecimal("999999"));
//			c1.setLimiteSaqueDiario(new BigDecimal("1000"));
//			c1.setFlagAtivo(true);
//			c1.setTipoConta(1);
//			c1.setDataCriacao(new Date("1998/03/07"));
//			
//			Conta c2 = new Conta();
//			c2.setIdConta(new Long(20));
//			c2.setIdPessoa(new Pessoa());
//			c2.setSaldo(new BigDecimal("888888"));
//			c2.setLimiteSaqueDiario(new BigDecimal("1000"));
//			c2.setFlagAtivo(true);
//			c2.setTipoConta(1);
//			c2.setDataCriacao(new Date("1998/10/07"));
//			
//			listaContas.add(c1);
//			listaContas.add(c2);
//		}
	
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
