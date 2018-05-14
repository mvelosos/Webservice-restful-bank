package com.api.bancaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.bancaria.model.Pessoa;
import com.api.bancaria.repository.PessoaRepo;
import com.api.bancaria.responses.Response;

/**
 * Classe controller de Pessoa, contendo o path "/pessoa" das requisições e os métodos para as operações.
 * 
 * @author mateusveloso
 *
 */
@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
	
	@Autowired
	private PessoaRepo pessoaRepo; 
	
	/**
	 * Método POST com o path "/novaPessoa" para a criação de uma nova Pessoa no banco de dados.
	 * 
	 * @param pessoa - Recebe um objeto Pessoa para que seja salvo no banco de dados.
	 * @param result - Recebe um BindingResult para tratar as validações e listar os erros, caso ocorra.
	 * @return Response<Pessoa> - Retorna uma resposta da inserção da Pessoa.
	 */
	@PostMapping("/novaPessoa")
	public ResponseEntity<Response<Pessoa>> novaPessoa(@Valid @RequestBody Pessoa pessoa, BindingResult result){
		if(result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Pessoa>(erros));
		}
		return ResponseEntity.ok(new Response<Pessoa>(pessoaRepo.save(pessoa)));
	}
	
	/**
	 * Método GET com o path "/buscarPessoas" para buscar todas as pessoas no banco de dados.
	 * 
	 * @return Response<List<Pessoa>>> - Retorna uma resposta contendo uma lista de Pessoa.
	 */
	@GetMapping("/buscarPessoas")
	public ResponseEntity<Response<List<Pessoa>>> buscarPessoa(){
		
		return ResponseEntity.ok(new Response<List<Pessoa>>(pessoaRepo.findAll())); 
	}
	
	/**
	 * Método GET com path "/buscarPessoas/{idPessoa}" para a busca de uma determinada Pessoa passando o idPessoa no path.
	 * 
	 * @param idPessoa - Recebe um idPessoa para que seja feita a busca da pessoa pelo idPessoa.
	 * @return Response<Pessoa> - Retorna uma resposta contendo uma pessoa, ou retorna "notFound" caso a pessoa não exista no banco de dados.
	 */
	@GetMapping("/buscarPessoas/{idPessoa}")
	public ResponseEntity<Response<?>> buscarPorId(@PathVariable Long idPessoa){
		Pessoa pessoa = pessoaRepo.findOne(idPessoa);
		if(pessoa == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new Response<Pessoa>(pessoa));
	}

}
