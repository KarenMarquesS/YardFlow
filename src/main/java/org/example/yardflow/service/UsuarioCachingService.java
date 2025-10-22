package org.example.yardflow.service;


import org.example.yardflow.model.Funcao;
import org.example.yardflow.model.Usuario;
import org.example.yardflow.repository.FuncaoRepositorio;
import org.example.yardflow.repository.UsuarioRepositorio;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioCachingService {

    @Autowired
    private UsuarioRepositorio uR;

    @Autowired
    private FuncaoRepositorio fR;

    //inserir usuário
    @CacheEvict(value = "usuarioCache", allEntries = true)
    public void inserirUsuario(Usuario usuario, Long idFuncao) {
        // a função deve ser informada na criação do usuário
        if (idFuncao == null) {
            throw new IllegalArgumentException("Função é obrigatória ao criar usuário");
        }

        // buscar a função que será atribuída ao novo usuário
        Funcao funcao = fR.findById(idFuncao)
                .orElseThrow(() -> new RuntimeException("Função com ID " + idFuncao + " não encontrada"));

        usuario.setFuncao(funcao);
        uR.save(usuario);
    }

    @CachePut(value = "usuarioCache", key = "#email")
    public void editarUsuario(Usuario usuario, Long idFuncao) {

        Usuario user = uR.findById(usuario.getId()).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));

        // atualiza o nome e email
        user.setNome(usuario.getNome());
        user.setEmail(usuario.getEmail());

        // mantem a senha anterior caso o campo esteja vazio
        if(usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            user.setSenha(usuario.getSenha());
        }

        // atualiza a função apenas se for alterada
        if(idFuncao!= null) {
            Funcao funcao = fR.findById(idFuncao).orElseThrow(()-> new RuntimeException("Função não encontrada"));
        }

        uR.save(user);
    }

    // buscao usuário por email
    @Cacheable(value = "usuarioCache", key = "'email' + #email")
    public Optional<Usuario> buscarPorEmail(String email) {
        return uR.findByEmail(email);
    }

    @CacheEvict(value = "usuariocache", allEntries = true)
    public void deletarUsuario(Long id) {
        if (uR.existsById(id)) {
            uR.deleteById(id);
        }
    }

}
