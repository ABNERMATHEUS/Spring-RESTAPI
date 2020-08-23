package com.abner.spring01.domain.rules;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abner.spring01.domain.exception.EntidadeNaoEncontradaException;
import com.abner.spring01.domain.exception.RegraException;
import com.abner.spring01.domain.model.Cliente;
import com.abner.spring01.domain.model.Comentario;
import com.abner.spring01.domain.model.OrdemServico;
import com.abner.spring01.domain.model.StatusOrdemServico;
import com.abner.spring01.domain.repository.ClienteRepository;
import com.abner.spring01.domain.repository.ComentarioRepository;
import com.abner.spring01.domain.repository.OrdemServicoRepository;
@Service 
public class GestaoOrdemServicoService {
	
	@Autowired
	OrdemServicoRepository ordemServicoRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(()-> new RegraException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId).orElseThrow(()-> new EntidadeNaoEncontradaException("Ordem de servico não encontrada"));
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
		
	}
	
	
	public void Finalizar(Long ordemServicoId) {
		OrdemServico ordemServico =  ordemServicoRepository.findById(ordemServicoId).orElseThrow(()-> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
		
				
	}
	
	

}
