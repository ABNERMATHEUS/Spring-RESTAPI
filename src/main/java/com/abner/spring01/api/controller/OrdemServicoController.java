package com.abner.spring01.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abner.spring01.api.model.OrdemServicoInput;
import com.abner.spring01.api.model.OrdemServicoRepresetationModel;
import com.abner.spring01.domain.model.OrdemServico;
import com.abner.spring01.domain.repository.ClienteRepository;
import com.abner.spring01.domain.repository.OrdemServicoRepository;
import com.abner.spring01.domain.rules.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService ;
	
	@Autowired
	private OrdemServicoRepository ordemServicorepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresetationModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoinput ) {
		
		OrdemServico ordemServico = toEntity(ordemServicoinput); ///Convertendo de representation para ordem servico
		
		return toMoldel(gestaoOrdemServicoService.criar(ordemServico));
		
	}
	
	
	@GetMapping
	public List<OrdemServicoRepresetationModel> listar (){
			return toCollectionModel(ordemServicorepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoRepresetationModel> buscar(@PathVariable Long ordemServicoId){
		
		Optional<OrdemServico> ordem = ordemServicorepository.findById(ordemServicoId);
		
		if(ordem.isPresent()) {
			//OrdemServicoRepresetationModel model = new OrdemServicoRepresetationModel();
			//model.setId(ordem.get().getId());
			//model.setStatus(ordem.get().getStatus());
			//...... continuaria
			
			OrdemServicoRepresetationModel model = toMoldel(ordem.get());
			
			
			return ResponseEntity.ok(model);
		}
		
		return 	ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void FinalizarOrdem(@PathVariable Long ordemServicoId) {
		gestaoOrdemServicoService.Finalizar(ordemServicoId);
	}
	
	
	
	private OrdemServicoRepresetationModel toMoldel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoRepresetationModel.class);
	}
	
	private List<OrdemServicoRepresetationModel> toCollectionModel(List<OrdemServico> ordensServico){
		return ordensServico.stream()
			   .map(ordemServico -> toMoldel(ordemServico))
			   .collect(Collectors.toList());
	}
	
	public OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
	
	

}
