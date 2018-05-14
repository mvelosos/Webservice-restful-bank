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

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
	
	@Autowired
	private PessoaRepo pessoaRepo; 
	
	@PostMapping("/novaPessoa")
	public ResponseEntity<Response<Pessoa>> novaPessoa(@Valid @RequestBody Pessoa pessoa, BindingResult result){
		if(result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Pessoa>(erros));
		}
		return ResponseEntity.ok(new Response<Pessoa>(pessoaRepo.save(pessoa)));
	}
	
	@GetMapping("/buscarPessoas")
	public ResponseEntity<Response<List<Pessoa>>> buscarPessoa(){
		
		return ResponseEntity.ok(new Response<List<Pessoa>>(pessoaRepo.findAll())); 
	}
	
	@GetMapping("/buscarPessoas/{idPessoa}")
	public ResponseEntity<Response<?>> buscarPorId(@PathVariable Long idPessoa){
		Pessoa pessoa = pessoaRepo.findOne(idPessoa);
		if(pessoa == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new Response<Pessoa>(pessoa));
	}

}
