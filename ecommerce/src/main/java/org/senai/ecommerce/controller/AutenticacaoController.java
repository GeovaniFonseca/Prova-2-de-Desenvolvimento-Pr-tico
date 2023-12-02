package org.senai.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.senai.ecommerce.entity.usuario.AutenticacaoDTO;
import org.senai.ecommerce.entity.usuario.LoginRespostaDTO;
import org.senai.ecommerce.entity.usuario.RegistroDTO;
import org.senai.ecommerce.entity.usuario.Usuario;
import org.senai.ecommerce.repositororio.UsuarioRepositorio;
import org.senai.ecommerce.seguranca.TokenServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autenticacao")
@Tag(name = "Autenticação", description = "Contem as operações para adicionar novos usuarios e login(Bearer JWT)")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private TokenServico tokenServico;

    @PostMapping("/login")
    @Operation(summary = "Login",
    description = "Ao fazer o login é gerado um token para reazlizar a autenticação")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var autenticacao = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenServico.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new LoginRespostaDTO(token));
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registra um novo usuario.",
    description = "Os usuarios possuem dois tipos de cargos (USER e ADMIN) com diferença de permissões." +
            " Usuarios com o cargo USER tem permissão apenas para usar o endpoint GET /produtos, para retornar os produtos já adicionados." +
            " Usuarios com o cargo ADMIN tem permissão para usar os seguintes endpoints POST /produtos, PUT /produtos e DELETE /produtos.")
    public ResponseEntity registrar(@RequestBody @Valid RegistroDTO data){
        if(this.usuarioRepositorio.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        String senhaEncriptada = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.login(), senhaEncriptada, data.cargo());

        this.usuarioRepositorio.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
