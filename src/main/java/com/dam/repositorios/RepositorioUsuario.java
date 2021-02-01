package com.dam.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dam.modelos.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {

}
