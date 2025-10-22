package org.example.yardflow.control.HTML;

import org.example.yardflow.model.Moto;
import org.example.yardflow.model.Yardflow;
import org.example.yardflow.repository.MotoRepositorio;
import org.example.yardflow.repository.YardflowRepositorio;
import org.example.yardflow.service.YardflowCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;



@Controller
@RequestMapping("/yardflow")
public class YardflowHTMLController {

    @Autowired
    private YardflowCachingService yfS;

    @Autowired
    private YardflowRepositorio yfR;

    @Autowired
    private MotoRepositorio motoRepo;


    @GetMapping("/lista")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("consultas/listaYardflow");
        try {
            // Usar o metodo que carrega as relações com moto
            List<Yardflow> yardflows = yfR.findAllWithMoto();
            
            // Se não há dados, informar que os dados do V3__insert devem ser carregados
            if (yardflows.isEmpty()) {
                mv.addObject("info", "Nenhum YardFlow encontrado. Certifique-se de que os dados do arquivo V3__insert_tabela.sql foram carregados no banco de dados.");
            }
            
            mv.addObject("yardflows", yardflows);
            mv.addObject("totalYardflows", yardflows.size());
        } catch (Exception e) {
            mv.addObject("yardflows", java.util.Collections.emptyList());
            mv.addObject("erro", "Erro ao carregar lista de YardFlows: " + e.getMessage());
        }
        return mv;
    }

    @GetMapping("/consultas/localizarMoto")
    public ModelAndView exibirFormularioLocalizacao() {
        ModelAndView mv = new ModelAndView("consultas/localizarMoto");
        mv.addObject("yardflow", new Yardflow()); // cria um objeto vazio
        return mv;
    }

    @GetMapping("/{idyf}/moto")
    public ModelAndView localizarMoto(@PathVariable long idyf) {
        try {
            Moto moto = yfS.localizarMotoPorYardFlow(idyf);
            ModelAndView mv = new ModelAndView("consultas/moto_detalhe");
            mv.addObject("moto", moto);
            return mv;
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("consultas/localizarMoto");
            mv.addObject("erro", "Erro ao localizar moto: " + e.getMessage());
            return mv;
        }
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("cadastros/novoYardflow");
        mv.addObject("yardflow", new Yardflow());
        mv.addObject("motos", motoRepo.findAll());
        return mv;
    }

    @PostMapping("/inserir")
    public ModelAndView inserir(@ModelAttribute Yardflow yardflow, 
                                @RequestParam(value = "idmoto", required = false) Long idmoto) {
        try {
            // Limpar a moto do objeto yardflow para evitar problemas de estado
            yardflow.setMoto(null);
            
            if (idmoto != null && idmoto != 0) {
                Moto moto = motoRepo.findById(idmoto)
                        .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada"));
                yardflow.setMoto(moto);
            }
            
            yfS.criarNovoYardFlow(yardflow);
            return new ModelAndView("redirect:/yardflow/lista");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("cadastros/novoYardflow");
            mv.addObject("yardflow", yardflow);
            mv.addObject("motos", motoRepo.findAll());
            mv.addObject("erro", "Erro ao inserir YardFlow: " + e.getMessage());
            return mv;
        }
    }

    @GetMapping("/editar/{idyf}")
    public ModelAndView editar(@PathVariable long idyf) {
        ModelAndView mv = new ModelAndView("cadastros/editarYardflow");
        Yardflow yardflow = yfR.findById(idyf)
                .orElseThrow(() -> new IllegalArgumentException("YardFlow não encontrado"));
        mv.addObject("yardflow", yardflow);
        mv.addObject("motos", motoRepo.findAll());
        return mv;
    }

    @PostMapping("/atualizar/{idyf}")
    public ModelAndView atualizar(@PathVariable long idyf, @ModelAttribute Yardflow yardflow,
                                  @RequestParam(value = "idmoto", required = false) Long idmoto) {
        Yardflow yardflowExistente = yfR.findById(idyf)
                .orElseThrow(() -> new IllegalArgumentException("YardFlow não encontrado"));

        yardflowExistente.setSerial(yardflow.getSerial());
        yardflowExistente.setDtUltimoAcionamento(yardflow.getDtUltimoAcionamento());

        if (idmoto != null && idmoto != 0) {
            Moto moto = motoRepo.findById(Long.valueOf(idmoto))
                    .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada"));
            yardflowExistente.setMoto(moto);
        } else {
            yardflowExistente.setMoto(null);
        }

        yfR.save(yardflowExistente);
        return new ModelAndView("redirect:/yardflow/lista");
    }

    @GetMapping("/deletar/{idyf}")
    public ModelAndView deletar(@PathVariable long idyf) {
        yfS.removerYardFlow(idyf);
        return new ModelAndView("redirect:/yardflow/lista");
    }


    @PostMapping("/{idyf}/ativar/{idmoto}")
    public ModelAndView ativar(@PathVariable long idyf, @PathVariable long idmoto) {
        Yardflow ativo = yfS.ativarYardFlow(idyf, idmoto);
        ModelAndView mv = new ModelAndView("yardflow_detalhe");
        mv.addObject("yardflow", ativo);
        return mv;
    }


    @PostMapping("/{idyf}/desativar")
    public ModelAndView desativar(@PathVariable long idyf) {
        Yardflow desativado = yfS.desativarYardFlow(idyf);
        ModelAndView mv = new ModelAndView("yardflow_detalhe");
        mv.addObject("yardflow", desativado);
        return mv;
    }


//    @GetMapping("/{idyf}/moto")
//    public ModelAndView localizarMoto(@PathVariable long idyf) {
//        Moto moto = yfS.localizarMotoPorYardFlow(idyf);
//        ModelAndView mv = new ModelAndView("moto_detalhe");
//        mv.addObject("moto", moto);
//        return mv;
//    }


    @PostMapping("/{idyf}/remover")
    public ModelAndView remover(@PathVariable long idyf) {
        yfS.removerYardFlow(idyf);
        return new ModelAndView("redirect:/yardflow/lista");
    }

}
