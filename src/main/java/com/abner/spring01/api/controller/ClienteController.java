package com.abner.spring01.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abner.spring01.domain.exception.RegraException;
import com.abner.spring01.domain.model.Cliente;
import com.abner.spring01.domain.repository.ClienteRepository;
import com.abner.spring01.domain.rules.RegraCliente;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	
	
	
	@Autowired
	private ClienteRepository clienterepository;
	
	@Autowired
	private RegraCliente regraCliente;
	
	
	
	@GetMapping
	public List<Cliente>listando(){
	    return clienterepository.findAll();
		//return clienterepository.findByNome("Abner");
		//return clienterepository.findByNomeContaining("Eli");
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		
		Optional<Cliente> cliente = clienterepository.findById(clienteId);
		
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}else {
			return ResponseEntity.notFound().build();
		}
		
		
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente registrar(@Valid @RequestBody Cliente cliente){
		
		Cliente existCliente = clienterepository.findByEmail(cliente.getEmail());
		
		if(existCliente != null && !existCliente.equals(cliente)) {
			throw new RegraException("E-mail já cadastrado");
		}
		
		Cliente clienteReturn = regraCliente.Salvar(cliente);
		
		return clienteReturn;
		
		//return clienterepository.save(cliente);
	}
	
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId
										   , @RequestBody Cliente cliente){
		
		
		if(!clienterepository.existsById(clienteId)) {
			
			return ResponseEntity.notFound().build();
		}else {
			cliente.setId(clienteId);
			cliente = regraCliente.Salvar(cliente);
			//return ResponseEntity.ok().build();
			return ResponseEntity.ok(cliente);
			
		}
			
		
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(!clienterepository.existsById(clienteId)) {
			return 	ResponseEntity.notFound().build();
		}else {
			regraCliente.Excluir(clienteId);
			return ResponseEntity.noContent().build();
		}
	}
		
}
