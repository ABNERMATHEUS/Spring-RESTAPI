package com.abner.spring01.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abner.spring01.domain.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
