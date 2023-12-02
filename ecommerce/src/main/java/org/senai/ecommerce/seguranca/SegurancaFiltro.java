package org.senai.ecommerce.seguranca;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senai.ecommerce.repositororio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SegurancaFiltro extends OncePerRequestFilter {
    @Autowired
    TokenServico tokenServico;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recuperarToken(request);
        if(token != null){
            var login = tokenServico.validarToken(token);
            UserDetails usuario = usuarioRepositorio.findByLogin(login);

            var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request){
        var autenticacaoHeader = request.getHeader("Authorization");
        if(autenticacaoHeader == null) return null;
        return autenticacaoHeader.replace("Bearer ", "");
    }
}
