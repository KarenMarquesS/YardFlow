package org.example.yardflow.service;


import org.example.yardflow.model.Funcao;
import org.example.yardflow.model.Usuario;
import org.example.yardflow.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;


@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio uR;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = uR.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("E-mail não encontrado"));

        return new User(usuario.getEmail(), usuario.getSenha(), Collections.singleton(new SimpleGrantedAuthority(
                usuario.getFuncao().getNome().toString()))

        );
    }
}
