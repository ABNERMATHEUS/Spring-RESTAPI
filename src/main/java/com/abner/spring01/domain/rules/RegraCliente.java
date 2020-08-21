package com.abner.spring01.domain.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abner.spring01.domain.model.Cliente;
import com.abner.spring01.domain.repository.ClienteRepository;


@Service
public class RegraCliente {
	
	@Autowired
	public ClienteRepository clienteRepository;
	
	public Cliente Salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public void Excluir (Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}

}
