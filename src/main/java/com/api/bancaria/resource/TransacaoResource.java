package com.api.bancaria.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.bancaria.model.Conta;
import com.api.bancaria.model.Transacao;
import com.api.bancaria.repository.ContaRepo;
import com.api.bancaria.repository.TransacaoRepo;
import com.api.bancaria.responses.Response;

/**
 * Classe controller de Transacao, contendo o path "/transacao" das requisições e os métodos para as operações.
 * 
 * @author mateusveloso
 *
 */
@RestController
@RequestMapping("/transacao")
public class TransacaoResource {
	
	@Autowired
	private TransacaoRepo transacaoRepo;
	
	@Autowired
	private ContaRepo contaRepo;
	
	/**
	 * Método GET com o path "/buscarTransacoes" para buscar todas as transacoes no banco de dados.
	 * 
	 * @return Retorna uma resposta contendo uma lista de todas as transacoes.
	 */
	@GetMapping("/buscarTransacoes")
	public ResponseEntity<Response<List<Transacao>>> buscarTransacoes(){
		
		return ResponseEntity.ok(new Response<List<Transacao>>(transacaoRepo.findAll()));
	}
	
	/**
	 * Método POST com path "/depositar" para fazer um depósito em uma conta
	 * O depósito só será realizado se a conta não esitver bloqueada e o valor do depósito for maior que 0.
	 * 
	 * @param transacao - Recebe uma Transacao como parâmetro, a transação contém informações para que a operação seja realizada.
	 * @return Retorna uma resposta dizendo se a operação foi realizada.
	 */
	@PostMapping("/depositar")
	public ResponseEntity<Transacao> depositar(@Valid @RequestBody Transacao transacao){
		
		BigDecimal min = new BigDecimal(0);
		Conta conta = contaRepo.findOne(transacao.getIdConta().getIdConta());
		
		if(conta.getFlagAtivo() == true && (transacao.getValor().compareTo(min) > 0)) {
			BigDecimal novoSaldo = conta.getSaldo().add(transacao.getValor());
			conta.setSaldo(novoSaldo);
			contaRepo.save(conta);
			
			return new ResponseEntity<Transacao>(transacaoRepo.save(transacao), HttpStatus.CREATED);
		}
		return ResponseEntity.badRequest().build();
	}
	
	/**
	 * Método POST com path "/sacar" para fazer um saque em uma conta em uma conta.
	 * O saque só será realizado se a conta não estiver bloqueada, se o valor a ser sacado for menor ou igual ao limite
	 * disponivel do dia e se o valor a ser sacado for menor ou igual ao saldo disponível na conta.
	 * 
	 * @param transacao - Recebe uma Transacao como parâmetro, a transação contém informações para que a operação seja realizada.
	 * @return Retorna uma resposta dizendo se a operação foi realizada.
	 */
	@PostMapping("/sacar")
	public ResponseEntity<Transacao> sacar(@Valid @RequestBody Transacao transacao){
		System.out.println(transacao.getIdConta().getIdConta());
		Conta conta = contaRepo.findOne(transacao.getIdConta().getIdConta());
		if((conta.getFlagAtivo() == true) && (transacao.getValor().compareTo(conta.getLimiteSaqueDiario()) <= 0)
				&& (transacao.getValor().compareTo(conta.getSaldo()) <= 0)) {
			BigDecimal novoLimite = conta.getLimiteSaqueDiario().subtract(transacao.getValor());
			conta.setLimiteSaqueDiario(novoLimite);
			BigDecimal novoSaldo = conta.getSaldo().subtract(transacao.getValor());
			conta.setSaldo(novoSaldo);
			contaRepo.save(conta);
			
			return new ResponseEntity<Transacao>(transacaoRepo.save(transacao), HttpStatus.CREATED);
		}
		return ResponseEntity.badRequest().build();
	}
	
	/**
	 * Método GET com path "/extrato/{idConta}" que mostra o extrato de transacoes de uma determinada conta
	 * ao ser passado um idConta no path.
	 * 
	 * @param idConta - Recebe um idConta para que seja listada todas as transações de uma conta.
	 * @return Retorna uma resposta contendo uma lista de transações.
	 */
	@GetMapping("/extrato/{idConta}")
	public ResponseEntity<Response<List<Transacao>>> extrato(@PathVariable Conta idConta){
	
		return ResponseEntity.ok(new Response<List<Transacao>>(transacaoRepo.findByIdConta(idConta)));
	}
}