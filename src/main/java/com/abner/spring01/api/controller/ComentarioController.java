package com.abner.spring01.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abner.spring01.api.model.ComentarioInput;
import com.abner.spring01.api.model.ComentarioRepresentationModel;
import com.abner.spring01.domain.exception.EntidadeNaoEncontradaException;
import com.abner.spring01.domain.model.Comentario;
import com.abner.spring01.domain.model.OrdemServico;
import com.abner.spring01.domain.repository.OrdemServicoRepository;
import com.abner.spring01.domain.rules.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioRepresentationModel adicionarComentario (@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioInput comentarioInput) {
		
		Comentario comentario = gestaoOrdemServicoService.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
		
		return toModel(comentario);
		
	}
	
	@GetMapping
	public List<ComentarioRepresentationModel> listar( @PathVariable Long ordemServicoId){
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId).orElseThrow(()-> new EntidadeNaoEncontradaException("Ordem serviço não encontrada"));
		return toCollectionModel(ordemServico.getComentarios());
	}
	
	
	
	public ComentarioRepresentationModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioRepresentationModel.class);
	}
	
	public List<ComentarioRepresentationModel> toCollectionModel(List<Comentario> comentarios) {
		return comentarios.stream().map(comentario -> toModel(comentario)).collect(Collectors.toList());
	}
	
	
	

}
