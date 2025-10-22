package org.example.yardflow.control.HTML;

import org.example.yardflow.dto.MotoDTO;
import org.example.yardflow.dto.PatioDTO;
import org.example.yardflow.model.EnumModelo;
import org.example.yardflow.model.Moto;
import org.example.yardflow.model.Patio;
import org.example.yardflow.model.Usuario;
import org.example.yardflow.repository.PatioRepositorio;
import org.example.yardflow.repository.Registro_check_in_outRepositorio;
import org.example.yardflow.repository.FuncaoRepositorio;
import org.example.yardflow.repository.UsuarioRepositorio;
import org.example.yardflow.service.MotoCachingService;
import org.example.yardflow.service.PatioCachingService;
import org.example.yardflow.service.YardflowCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeHTMLController {

    @Autowired
    private PatioRepositorio ptR;

    @Autowired(required = false)
    private Registro_check_in_outRepositorio rgR;

    @Autowired
    private FuncaoRepositorio fuR;

    @Autowired
    private MotoCachingService mtS;

    @Autowired
    private YardflowCachingService yfS;

    @Autowired
    private UsuarioRepositorio usR;
    
    @Autowired
    private PatioCachingService ptS;


    @GetMapping({"/","/index"})
    public String mostrarIndex() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView mostrarHome() {
        ModelAndView mv = new ModelAndView("home");

        long vagasTotal = ptR.findAll().stream().mapToLong(p -> p.getQtdvagas()).sum();

//        ptR parece ser um repositório (PátioRepository).
//        findAll() retorna todas as instâncias de Pátio cadastradas no banco.
//        .stream() transforma a lista em fluxo de dados.
//        .mapToLong(p -> p.getQtdvagas()) pega apenas o número de vagas de cada pátio.
//        .sum() soma tudo, gerando o total de vagas do sistema.

        long vagasOcupadas = 0;
        long motosMais5Dias = 0;
        if (rgR != null) {
            //      Verifica se o repositório rgR (provavelmente RegistroRepository) não é nulo antes de usá-lo.
            //       Isso evita NullPointerException caso o repositório não tenha sido injetado corretamente.

            var todos = rgR.findByEntradapatioIsNotNull();
            //Busca todos os registros que possuem data de entrada registrada, ou seja, motos que já entraram no pátio.



            vagasOcupadas = (long) todos.stream()
                    .filter(r -> r.getSaidapatio() == null)
                    .count();

//            Conta quantas motos ainda não saíram do pátio.
//            .filter(r -> r.getSaidapatio() == null) mantém apenas os registros sem data de saída, ou seja, ainda estacionados.
//            .count() retorna a quantidade desses registros. O resultado é convertido para long e armazenado em vagasOcupadas.


            motosMais5Dias = (int) todos.stream()
                    .filter(r -> r.getSaidapatio() == null)
                    .filter(r -> r.getEntradapatio() != null)
                    .filter(r -> java.time.temporal.ChronoUnit.DAYS
                            .between(r.getEntradapatio(), LocalDate.now()) > 5)
                    .count();

//            Conta quantas motos estão há mais de 5 dias no pátio.
//                    Explicando por partes:
//
//            .filter(r -> r.getSaidapatio() == null) → ainda não saíram.
//            .filter(r -> r.getEntradapatio() != null) → têm uma data de entrada válida.
//            .filter(r -> ChronoUnit.DAYS.between(r.getEntradapatio(), LocalDate.now()) > 5) → calcula a diferença em dias entre a data de entrada e hoje, e mantém apenas as que ultrapassaram 5 dias.
//            .count() → retorna o número total de casos assim.
//            🧠 Resultado: número de motos estacionadas por mais de 5 dias.
            
        }
        long vagasLivres = Math.max(vagasTotal - vagasOcupadas, 0);

//        Calcula o número de vagas livres disponíveis.
//        vagasTotal - vagasOcupadas → quantidade de vagas restantes.
//        Math.max(..., 0) → garante que o número nunca seja negativo, mesmo se houver inconsistências (ex: mais motos que vagas registradas).

        mv.addObject("vagasTotal", vagasTotal);
        mv.addObject("vagasOcupadas", vagasOcupadas);
        mv.addObject("vagasLivres", vagasLivres);
        mv.addObject("motosMais5Dias", motosMais5Dias);
        mv.addObject("nome", "Pátio Principal");

//        Adiciona todos os valores ao modelo do ModelAndView, para que o template consiga exibi-los na página.
//        Essas chaves serão acessadas no HTML via ${vagasTotal}, ${vagasLivres}, etc.

        return mv;
//        Retorna o objeto ModelAndView finalizado, que contém:
//        o nome da view ("home");
//        e todos os dados (vagas, motos, nome).
//        O Spring MVC renderiza a página home.html (ou .jsp) com as informações incluídas.
    }



    // Rotas de visualização para navegar a partir da Home
    @GetMapping("/cadastros/moto")
    public ModelAndView viewCadastroMoto() {
        ModelAndView mv = new ModelAndView("cadastros/moto");
        mv.addObject("motoDTO", new MotoDTO());
        mv.addObject("modelos", EnumModelo.values());
        return mv;
    }

    @GetMapping("/cadastros/patio")
    public ModelAndView viewCadastroPatio() {
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

    @GetMapping("/cadastros/usuario")
    public ModelAndView viewCadastroUsuario() {
        ModelAndView mv = new ModelAndView("cadastros/usuario");
        mv.addObject("usuario", new Usuario());
        mv.addObject("lista_funcoes", fuR.findAll());
        return mv;
    }

    @GetMapping("/cadastros/yardflow")
    public ModelAndView viewCadastroYardflow() {
        ModelAndView mv = new ModelAndView("cadastros/yardflow");
        mv.addObject("yardflows", java.util.Collections.emptyList());
        return mv;
    }

    @GetMapping("/consultas/listaMoto")
    public ModelAndView viewListaMoto(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        // Redireciona para o endpoint correto que usa MotoDTO
        return new ModelAndView("redirect:/moto/lista?page=" + page + "&size=" + size);
    }

    @GetMapping("/consultas/listaUsuario")
    public ModelAndView viewListaUsuario() {
        ModelAndView mv = new ModelAndView("consultas/listaUsuarios");
        mv.addObject("usuarios", usR.findAll());
        return mv;
    }

    @GetMapping("/consultas/localizarMoto")
    public ModelAndView viewLocalizarMoto() {
        ModelAndView mv = new ModelAndView("consultas/localizarMoto");
        return mv;
    }

    @PostMapping("/consultas/buscarMoto")
    public ModelAndView buscarMoto(@RequestParam(value = "idyf", required = false) Integer idyf,
                                   @RequestParam(value = "placa", required = false) String placa,
                                   @RequestParam(value = "chassi", required = false) String chassi) {
        ModelAndView mv = new ModelAndView("consultas/resultadoBuscaMoto");

        try {
            Moto moto = null;

            // Busca por ID YardFlow
            if (idyf != null && idyf > 0) {
                try {
                    moto = yfS.localizarMotoPorYardFlow(idyf);
                } catch (Exception e) {
                    // YardFlow não encontrado ou sem moto associada
                }
            }

            // Busca por placa
            if (moto == null && placa != null && !placa.trim().isEmpty()) {
                try {
                    MotoDTO motoDTO = mtS.findByPlaca(placa.trim());
                    if (motoDTO != null) {
                        // Converter DTO para entidade Moto
                        moto = mtS.findById(motoDTO.getIdmoto()).orElse(null);
                    }
                } catch (Exception e) {
                    // Placa não encontrada
                }
            }

            // Busca por chassi
            if (moto == null && chassi != null && !chassi.trim().isEmpty()) {
                try {
                    MotoDTO motoDTO = mtS.findByChassi(chassi.trim());
                    if (motoDTO != null) {
                        // Converter DTO para entidade Moto
                        moto = mtS.findById(motoDTO.getIdmoto()).orElse(null);
                    }
                } catch (Exception e) {
                    // Chassi não encontrado
                }
            }

            mv.addObject("moto", moto);

        } catch (Exception e) {
            mv.addObject("moto", null);
        }

        return mv;
    }
}


