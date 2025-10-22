package org.example.yardflow.control.api;


import jakarta.validation.Valid;
import org.example.yardflow.dto.RegistroPermanenciaDTO;
import org.example.yardflow.dto.Registro_check_in_outDTO;
import org.example.yardflow.model.Registro_check_in_out;
import org.example.yardflow.service.Registro_check_in_outCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/apiregistrocheckinout")
public class Registro_check_in_outController {

    @Autowired
    private Registro_check_in_outCachingService rgS;


    @PostMapping("/entrada/{idmoto}")
    public ResponseEntity<Registro_check_in_outDTO> registrarEntrada(@PathVariable("idmoto") long idmoto,
                                                                     @Valid @RequestBody Registro_check_in_outDTO dto) {
        Registro_check_in_out reg = rgS.inserirDataEntrada(idmoto, dto.getEntradapatio());
        Registro_check_in_outDTO resposta = new Registro_check_in_outDTO(reg.getIdregistro(), reg.getEntradapatio(),
                reg.getSaidapatio(), reg.getPeriodo(), reg.getSetor(), null);
        return ResponseEntity.ok(resposta);
    }


    @PostMapping("/saida/{idmoto}")
    public ResponseEntity<Registro_check_in_outDTO> registrarSaida(@PathVariable("idmoto") long idmoto,
                                                                @Valid @RequestBody Registro_check_in_outDTO dto) {
        Registro_check_in_out reg = rgS.inserirDataSaida(idmoto, dto.getSaidapatio());
        Registro_check_in_outDTO resposta = new Registro_check_in_outDTO(reg.getIdregistro(), reg.getEntradapatio(),
                reg.getSaidapatio(), reg.getPeriodo(), reg.getSetor(), null);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/entrada")
    public ResponseEntity<List<Registro_check_in_out>> buscarEntrada(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entradapatio) {
        List<Registro_check_in_out> registros = rgS.buscarPorEntrada(entradapatio);

        if (registros == null || registros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registros);
    }


    @GetMapping("/permanencia/moto/{idmoto}")
    public ResponseEntity<Long> permanenciaPorMoto(@PathVariable("idmoto") long idmoto) {
        Long dias = rgS.calcularPermanenciaPorIdMoto(idmoto);
        return ResponseEntity.ok(dias);
    }


    @GetMapping("/lista/por_permanencia")
    public ResponseEntity<Page<RegistroPermanenciaDTO>> listarPorPermanencia(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegistroPermanenciaDTO> resultado = rgS.listarPorPermanenciaOrdenada(pageable);
        return ResponseEntity.ok(resultado);
    }

    // Limpar cache (delete sem corpo)
    @DeleteMapping("/cache")
    public ResponseEntity<String> limparCache() {
        rgS.limparCache();
        return ResponseEntity.ok("Cache limpo com sucesso.");
    }

}
