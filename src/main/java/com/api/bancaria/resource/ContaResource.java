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

/**
 * Classe controller de Conta, contendo o path "/conta" das requisições e os métodos para as operações.
 * 
 * @author mateusveloso
 *
 */
@RestController
@RequestMapping("/conta")
public class ContaResource {
	
	@Autowired
	private ContaRepo contaRepo;
	
	/**
	 * Método GET com o path "/buscarContas" para buscar todas as contas no banco de dados.
	 * 
	 * @return Retorna uma resposta contendo uma lista de Conta
	 */
	@GetMapping("/buscarContas")
	public ResponseEntity<Response<List<Conta>>> buscarContas(){

		return ResponseEntity.ok(new Response<List<Conta>>(contaRepo.findAll())); 
	}
	
	/**
	 * Método POST com o path "/novaConta" para a criação de uma nova conta no banco de dados.
	 * 
	 * @param conta - Recebe um objeto Conta para que seja salvo no banco de dados.
	 * @param result - Recebe um BindingResult para tratar as validações e listar os erros, caso ocorra.
	 * @return Retorna uma resposta da inserção da Conta.
	 */
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
	
	/**
	 * Método GET com path "/buscarContas/{idConta}" para a busca de uma determinada Conta passando o idConta no path.
	 * 
	 * @param idConta - Recebe um idConta para que seja feita a busca da conta pelo idConta.
	 * @return Retorna uma resposta contendo uma conta, ou retorna "notFound" caso a conta não exista no banco de dados.
	 */
	@GetMapping("/buscarContas/{idConta}")
	public ResponseEntity<Response<Conta>> buscarPorId(@PathVariable Long idConta){
		Conta conta = contaRepo.findOne(idConta);
		if(conta == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new Response<Conta>(contaRepo.findOne(idConta))); 
	}
	
	/**
	 * Método PUT com path "/bloquearConta/{idConta}" para que uma conta seja bloqueada passando o idConta no path.
	 * OBS: Uma conta bloqueada pode ser desbloqueada recebendo o mesmo path de bloqueio.
	 * 
	 * @param idConta - Recebe um idConta para que a conta seja buscada e bloqueada.
	 * @return Retorna a resposta da requisição.
	 */
	@PutMapping("/bloquearConta/{idConta}")
	public ResponseEntity<Conta> bloquearConta(@PathVariable Long idConta){
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
	
	/**
	 * Método GET com path "/saldo/{idConta}" para que seja consultado o saldo de uma conta passando o idConta.
	 * 
	 * @param idConta - Recebe um idConta para que a busca da conta seja feita pelo idConta.
	 * @return Retorna uma resposta da requisição contendo uma String com o saldo e informações da Conta.
	 */
	@GetMapping("/saldo/{idConta}")
	public ResponseEntity<String> saldoConta(@PathVariable Long idConta){
		Conta conta = contaRepo.findOne(idConta);
		if(conta != null) {
			BigDecimal saldo = conta.getSaldo();
			return ResponseEntity.status(HttpStatus.OK).body("idConta: "+conta.getIdConta()
					+"\nNome: "+conta.getIdPessoa().getNome()+"\nSaldo: "+saldo+"\nConta ativa: "+conta.getFlagAtivo());
		}
		return ResponseEntity.notFound().build();
	}

}
