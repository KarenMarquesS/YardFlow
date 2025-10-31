package org.example.yardflow.control.HTML;

import org.example.yardflow.model.Funcao;
import org.example.yardflow.model.Usuario;
import org.example.yardflow.repository.FuncaoRepositorio;
import org.example.yardflow.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/usuario")
public class UsuarioHTMLController {


    @Autowired
    private UsuarioRepositorio uR;

    @Autowired
    private FuncaoRepositorio fR;

    @Autowired
    private PasswordEncoder encoder;



    @GetMapping("/novo")
    public ModelAndView retornarCadUsuario() {
        ModelAndView mv = new ModelAndView("cadastros/usuario");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Usuario> op = uR.findBynome(auth.getName());

        op.ifPresent(usuario -> mv.addObject("usuario_logado", usuario));

        mv.addObject("usuario", new Usuario());
        mv.addObject("lista_funcoes", fR.findAll());

        return mv;
    }

    @PostMapping("/inserir")
    public ModelAndView inserirUsuario(Usuario usuario,
                                       @RequestParam(name = "idfuncao", required = false) Long idfuncao) {
        try {
            usuario.setSenha(encoder.encode(usuario.getSenha()));

            if (idfuncao == null) {
                throw new IllegalArgumentException("Função é obrigatória ao cadastrar usuário");
            }

            // valida email único antes de persistir
            uR.findByEmail(usuario.getEmail()).ifPresent(u -> {
                throw new IllegalArgumentException("Email já cadastrado");
            });

            Funcao funcao = fR.findById(idfuncao).orElseThrow(() -> new IllegalArgumentException("Função não encontrada"));

            usuario.setFuncao(funcao);
            uR.save(usuario);

            return new ModelAndView("redirect:/home");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("cadastros/usuario");
            
            mv.addObject("usuario", new Usuario());
            mv.addObject("lista_funcoes", fR.findAll());
            mv.addObject("erro", "Erro ao inserir usuário: " + e.getMessage());
            return mv;
        }
    }

    @GetMapping("/lista")
    public ModelAndView listarUsuarios() {
        ModelAndView mv = new ModelAndView("/usuario/lista");
        mv.addObject("usuarios", uR.findAll());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarUsuario(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("/usuario/editar");
        Usuario usuario = uR.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        mv.addObject("usuario", usuario);
        mv.addObject("lista_funcoes", fR.findAll());
        return mv;
    }

    @PostMapping("/atualizar")
    public ModelAndView atualizarUsuario(Usuario usuario, @RequestParam(name = "idfuncao", required = false) Long idfuncao) {
        Usuario usuarioExistente = uR.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        //  Atualizar os dados
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());

        // Só atualizar senha se foi informada (não vazia)
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuarioExistente.setSenha(encoder.encode(usuario.getSenha()));
        }

        // Atualizar função apenas se foi informada
        // Se idfuncao for null, mantém a função atual
        if (idfuncao != null) {
            Funcao funcao = fR.findById(idfuncao)
                    .orElseThrow(() -> new IllegalArgumentException("Função não encontrada"));
            usuarioExistente.setFuncao(funcao);
        }

        uR.save(usuarioExistente);

        return new ModelAndView("redirect:/consultas/listaUsuario");
    }

    @PostMapping("/deletar/{id}")
    public ModelAndView deletarUsuario(@PathVariable Long id) {
        if (uR.existsById(id)) {
            uR.deleteById(id);
        }
        return new ModelAndView("redirect:/consultas/listaUsuario");
    }

}
