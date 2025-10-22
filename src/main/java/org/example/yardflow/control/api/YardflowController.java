package org.example.yardflow.control.api;


import jakarta.validation.Valid;
import org.example.yardflow.dto.YardflowDTO;
import org.example.yardflow.model.Moto;
import org.example.yardflow.model.Yardflow;
import org.example.yardflow.service.YardflowCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/apiyardflow")
public class YardflowController {

    @Autowired
    private YardflowCachingService yfS;

    @PostMapping("/criar")
    public ResponseEntity<Yardflow> criarYardFlow(@RequestBody @Valid YardflowDTO yfDTO) {
        Yardflow yf = new Yardflow();
        yf.setSerial(yfDTO.getSerial());
        Yardflow savedYf = yfS.criarNovoYardFlow(yf);
        return ResponseEntity.ok(savedYf);
    }

    @PostMapping("/acionar")
    public ResponseEntity<Yardflow> acionarYardFlow(@RequestBody @Valid YardflowDTO yfDTO) {
        Optional<Yardflow> yfOpt = yfS.buscarSerial(yfDTO.getSerial());
        if (yfOpt.isPresent()) {
            Yardflow yf = yfOpt.get();
            yf.setDtUltimoAcionamento(java.time.LocalDateTime.now());
            Yardflow updatedYf = yfS.criarNovoYardFlow(yf);
            return ResponseEntity.ok(updatedYf);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/ativar/{idyf}/{idmoto}")
    public ResponseEntity<Yardflow> ativarYardFlow(@PathVariable int idyf, @PathVariable int idmoto) {
        return ResponseEntity.ok(yfS.ativarYardFlow(idyf, idmoto));
    }

    @PostMapping("/desativar/{idyf}")
    public ResponseEntity<Yardflow> desativarYardFlow(@PathVariable int idyf) {
        return ResponseEntity.ok(yfS.desativarYardFlow(idyf));
    }

    @GetMapping("/{idyf}")
    public ResponseEntity<Moto> localizarMoto(@PathVariable int idyf) {
        return ResponseEntity.ok(yfS.localizarMotoPorYardFlow(idyf));
    }

    @GetMapping("/serial/{serial}")
    public ResponseEntity<Optional<Yardflow>> localizarSerialYf(@PathVariable String serial) {
        return ResponseEntity.ok(yfS.buscarSerial(serial));
    }

    @GetMapping("/acionamento")
    public ResponseEntity<List<Yardflow>> buscarUltimoAcionamento(@RequestParam("data")
                                                                  @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
                                                                  LocalDateTime dtultimoacionamento) {
        List<Yardflow> yf = yfS.buscarDtUltimoAcionamento(dtultimoacionamento.toLocalDate());

        if(yf == null || yf .isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(yf);
    }

    @DeleteMapping("/deletar/{idyf}")
    public ResponseEntity<Void> removerYardFlow(@PathVariable int idyf) {
        yfS.removerYardFlow(idyf);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/limparcache")
    public ResponseEntity<Void> limparCache() {
        yfS.limparCache();
        return ResponseEntity.noContent().build();
    }

}

