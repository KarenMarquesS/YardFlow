package org.example.yardflow.control.HTML;


import org.example.yardflow.dto.MotoDTO;
import org.example.yardflow.model.Moto;
import org.example.yardflow.service.MotoCachingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/moto")
public class MotoHTMLController {

    @Autowired
    private MotoCachingService mtS;

    @Autowired
    private ModelMapper mm;


    @GetMapping("/id/{idmoto}")
    public ModelAndView buscarPorId(@PathVariable long idmoto) {
        ModelAndView mv = new ModelAndView("moto");
        Moto moto = mtS.findById(idmoto).orElseThrow(() -> new IllegalArgumentException("Moto não localizada"));
        mv.addObject("moto", moto);
        return mv;
    }

    @GetMapping("/chassi/{chassi}")
    public ModelAndView buscarPorChassi(@PathVariable String chassi) {
        ModelAndView mv = new ModelAndView("moto_chassi");
        MotoDTO motoDTO = mtS.findByChassi(chassi);
        mv.addObject("motoDTO", motoDTO);
        return mv;
    }

    @GetMapping("/placa/{placa}")
    public ModelAndView buscarPorPlaca(@PathVariable String placa) {
        ModelAndView mv = new ModelAndView("moto_placa");
        MotoDTO motoDTO = mtS.findByPlaca(placa);
        mv.addObject("motoDTO", motoDTO);
        return mv;
    }

    @GetMapping("/historico/{idmoto}")
    public ModelAndView buscarHistorico(@PathVariable long idmoto) {
        ModelAndView mv = new ModelAndView("moto_historico");
        MotoDTO motoDTO = mtS.buscarHistorico(idmoto);
        mv.addObject("motoDTO", motoDTO);
        return mv;
    }

    @GetMapping("/inserir/moto")
    public ModelAndView NovaMoto() {
        ModelAndView mv = new ModelAndView("cadastros/moto");
        mv.addObject("motoDTO", new MotoDTO());
        mv.addObject("modelos", org.example.yardflow.model.EnumModelo.values());
        return mv;
    }

    @PostMapping("/inserirMoto")
    public ModelAndView inserir(@ModelAttribute MotoDTO dto) {
        try {
            mtS.criarNovaMoto(dto);
            return new ModelAndView("redirect:/home");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("cadastros/moto");
            mv.addObject("motoDTO", dto);
            mv.addObject("modelos", org.example.yardflow.model.EnumModelo.values());
            mv.addObject("erro", "Erro ao inserir moto: " + e.getMessage());
            return mv;
        }
    }

    @GetMapping("/lista")
    public ModelAndView listarMotos(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(required = false) String sucesso,
                                    @RequestParam(required = false) String erro) {
        ModelAndView mv = new ModelAndView("consultas/listaMoto");

        try {
            Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
            Page<MotoDTO> motos = mtS.findAllPaginado(pageable);
            System.out.println("Total de motos encontradas: " + motos.getTotalElements());
            System.out.println("Motos na página atual: " + motos.getContent().size());

            mv.addObject("motos", motos.getContent());
            mv.addObject("currentPage", motos.getNumber());
            mv.addObject("totalPages", motos.getTotalPages());


            if ("excluido".equals(sucesso)) {
                mv.addObject("sucesso", "Moto excluída com sucesso!");
            } else if ("atualizado".equals(sucesso)) {
                mv.addObject("sucesso", "Moto atualizada com sucesso!");
            }
            if ("exclusao".equals(erro)) {
                mv.addObject("erro", "Erro ao excluir moto. Verifique se a moto não possui registros associados.");
            } else if ("moto_nao_encontrada".equals(erro)) {
                mv.addObject("erro", "Moto não encontrada. Ela pode ter sido excluída por outro usuário.");
            }

        } catch (Exception e) {
            mv.addObject("erro", "Erro ao carregar lista de motos: " + e.getMessage());
            mv.addObject("motos", java.util.Collections.emptyList());
            mv.addObject("currentPage", 0);
            mv.addObject("totalPages", 0);
        }
        return mv;
    }

    @GetMapping("/editar/{idmoto}")
    public ModelAndView editar(@PathVariable long idmoto) {
        ModelAndView mv = new ModelAndView("cadastros/editarMoto");
        Moto moto = mtS.findById(idmoto)
                .orElseThrow(() -> new IllegalArgumentException("Moto não localizada"));
        MotoDTO motoDTO = mm.map(moto, MotoDTO.class);
        mv.addObject("motoDTO", motoDTO);
        mv.addObject("modelos", org.example.yardflow.model.EnumModelo.values());
        return mv;
    }

    @PostMapping("/atualizar/{idmoto}")
    public ModelAndView atualizar(@PathVariable long idmoto, @ModelAttribute MotoDTO dto) {
        try {
            mtS.atualizarRegistroMoto(idmoto, dto);
            return new ModelAndView("redirect:/moto/lista?sucesso=atualizado");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("cadastros/editarMoto");
            mv.addObject("motoDTO", dto);
            mv.addObject("modelos", org.example.yardflow.model.EnumModelo.values());
            mv.addObject("erro", "Erro ao atualizar moto: " + e.getMessage());
            return mv;
        }
    }

    @PostMapping("/deletar/{idmoto}")
    public ModelAndView deletar(@PathVariable long idmoto) {
        try {
            mtS.deletarRegistroMoto(idmoto);
            return new ModelAndView("redirect:/moto/lista?sucesso=excluido");
        } catch (Exception e) {
            return new ModelAndView("redirect:/moto/lista?erro=exclusao");
        }
    }


}