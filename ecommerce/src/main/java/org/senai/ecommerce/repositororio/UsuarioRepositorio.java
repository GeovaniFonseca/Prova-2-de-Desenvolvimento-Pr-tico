package org.senai.ecommerce.repositororio;

import org.senai.ecommerce.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);
}
