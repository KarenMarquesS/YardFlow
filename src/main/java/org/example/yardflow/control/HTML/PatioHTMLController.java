package org.example.yardflow.control.HTML;



import org.example.yardflow.dto.PatioDTO;

import org.example.yardflow.model.Patio;
import org.example.yardflow.service.PatioCachingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller("/patio")
public class PatioHTMLController {

    @Autowired
    private PatioCachingService ptS;




    @GetMapping("/{idpatio}")
    public ModelAndView buscarPorId(@PathVariable long idpatio) {
        ModelAndView mv = new ModelAndView("patio_detalhe");
        Patio patio = ptS.buscarPatioPorId(idpatio)
                .orElseThrow(() -> new IllegalArgumentException("Pátio não encontrado"));
        mv.addObject("patio", patio);
        return mv;
    }


    @GetMapping("/qtd/{qtd_vagas}")
    public ModelAndView buscarPorQtdVagas(@PathVariable long qtd_vagas) {
        List<Patio> patios = ptS.buscarQtdVagas(qtd_vagas);
        ModelAndView mv = new ModelAndView("patio_lista");
        mv.addObject("patios", patios);
        return mv;
    }


    @GetMapping("/nome/{name}")
    public ModelAndView buscarPorNome(@PathVariable String name) {
        List<Patio> patios = ptS.buscarPatioPorNome(name);
        ModelAndView mv = new ModelAndView("patio_lista");
        mv.addObject("patios", patios);
        return mv;
    }


    @GetMapping("/novo")
    public ModelAndView novoPatio() {
        ModelAndView mv = new ModelAndView("patio_form");
        mv.addObject("patioDTO", new PatioDTO());
        return mv;
    }


    @PostMapping("/inserir")
    public ModelAndView inserir(@ModelAttribute PatioDTO dto) {
        ptS.inserirPatio(dto);
        return new ModelAndView("redirect:/patio/lista");
    }


    @PostMapping("/atualizar/{idpatio}")
    public ModelAndView atualizar(@PathVariable long idpatio, @ModelAttribute PatioDTO dto) {
        ptS.atualizarPatio(idpatio, dto);
        return new ModelAndView("redirect:/patio/lista");
    }

    @GetMapping("/patio-view/inserirNovo")
    public ModelAndView viewPatioDados() {
        ModelAndView mv = new ModelAndView("cadastros/patio");
        // Busca o primeiro pátio existente ou cria um novo
        List<Patio> patios = ptS.buscarTodosPatios();
        if (!patios.isEmpty()) {
            mv.addObject("patioDTO", new PatioDTO(patios.get(0)));
        } else {
            mv.addObject("patioDTO", new PatioDTO());
        }
        return mv;
    }

    @PostMapping("/patio-view/inserirNovo")
    public ModelAndView inserirNovoPatio(@ModelAttribute PatioDTO dto) {
        try {
            // Se já existe um pátio, atualiza; senão, cria novo
            List<Patio> patios = ptS.buscarTodosPatios();
            if (!patios.isEmpty()) {
                ptS.atualizarPatio(patios.get(0).getIdpatio(), dto);
            } else {
                ptS.inserirPatio(dto);
            }
            return new ModelAndView("redirect:/cadastros/patio?sucesso=salvo");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("cadastros/patio");
            mv.addObject("patioDTO", dto);
            mv.addObject("erro", "Erro ao salvar dados do pátio: " + e.getMessage());
            return mv;
        }
    }


    @PostMapping("/deletar/{idpatio}")
    public ModelAndView deletar(@PathVariable long idpatio) {
        ptS.deletarPatio(idpatio);
        return new ModelAndView("redirect:/patio/lista");
    }

}
