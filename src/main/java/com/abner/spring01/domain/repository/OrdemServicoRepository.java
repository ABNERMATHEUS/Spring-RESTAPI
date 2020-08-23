package com.abner.spring01.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abner.spring01.domain.model.Cliente;
import com.abner.spring01.domain.model.OrdemServico;


@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>{
	
	List<OrdemServico> findByCliente (Cliente cliente);

}
