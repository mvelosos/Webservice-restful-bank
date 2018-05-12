package com.api.bancaria.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.bancaria.model.Pessoa;
import com.api.bancaria.repository.PessoaRepo;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
	
	@Autowired
	private PessoaRepo pessoaRepo; 
	
	@PostMapping("/novaPessoa")
	public ResponseEntity<Pessoa> novaPessoa(@RequestBody Pessoa pessoa){
		pessoaRepo.save(pessoa);
		
		return new ResponseEntity<Pessoa>(pessoa, HttpStatus.CREATED);
	}
	
	@GetMapping("/buscarPessoas")
	public ResponseEntity<List<Pessoa>> buscarPessoa(){
		
		return new ResponseEntity<List<Pessoa>>(pessoaRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/buscarPessoas/{idPessoa}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long idPessoa){
		Pessoa pessoa = pessoaRepo.findOne(idPessoa);
		if(pessoa == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(pessoa);
	}

}
