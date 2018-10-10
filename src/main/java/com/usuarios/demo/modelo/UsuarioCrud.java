package com.usuarios.demo.modelo;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioCrud extends CrudRepository<Usuario, Long > {
}
