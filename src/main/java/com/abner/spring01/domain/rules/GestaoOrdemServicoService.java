package com.abner.spring01.domain.rules;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abner.spring01.domain.exception.RegraException;
import com.abner.spring01.domain.model.Cliente;
import com.abner.spring01.domain.model.OrdemServico;
import com.abner.spring01.domain.model.StatusOrdemServico;
import com.abner.spring01.domain.repository.ClienteRepository;
import com.abner.spring01.domain.repository.OrdemServicoRepository;
@Service 
public class GestaoOrdemServicoService {
	
	@Autowired
	OrdemServicoRepository ordemServicoRepository;
	@Autowired
	ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(()-> new RegraException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(LocalDateTime.now());
		
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	

}
