package org.example.yardflow.control.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.yardflow.dto.PatioDTO;

import org.example.yardflow.model.Patio;
import org.example.yardflow.service.PatioCachingService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/apipatio")
public class PatioController {

    @Autowired
    private PatioCachingService ptS;

    @Operation(
            description = "Este endpoint realiza a inserção de um novo pátio",
            tags = "Inserção de informação",
            summary = "Inserir novo pátio"
    )
    @PostMapping("/inserirNovo")
    public ResponseEntity<PatioDTO> inserirPatio(@Valid @RequestBody PatioDTO dto) {
        Patio salvo = ptS.inserirPatio(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PatioDTO(salvo));

    }

    @PutMapping("/{idpatio}")
    public ResponseEntity<PatioDTO> atualizarPatio(@PathVariable long idpatio, @Valid @RequestBody PatioDTO dto) {
        Patio atualizado = ptS.atualizarPatio(idpatio, dto);

        return ResponseEntity.status(HttpStatus.OK).body(new PatioDTO(atualizado));
    }

    @Operation(
            description = "Este endpoint retorna um pátio por ID",
            tags = "Consulta de informação",
            summary = "Buscar pátio por ID"
    )
    @GetMapping("/{idpatio}")
    public ResponseEntity<PatioDTO> buscarPorId(@PathVariable long idpatio) {
        Optional<Patio> patio = ptS.buscarPatioPorId(idpatio);

        return patio.map(p-> ResponseEntity.ok(new PatioDTO(p))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{name}")
    public ResponseEntity<List<PatioDTO>> buscarPorNome(@PathVariable String name) {
        List<Patio> patio =ptS.buscarPatioPorNome(name);

        if (patio.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum pátio encontrado com este nome");
        }

        List<PatioDTO> dto = patio.stream().map(PatioDTO::new).toList();
        return ResponseEntity.ok(dto);
    }

    @Operation(
            description = "Este endpoint retorna todos os pátios com determinada quantidade de vagas",
            tags = "Consulta de informação",
            summary = "Buscar pátios por quantidade de vagas"
    )
    @GetMapping("/quantidadevagas/{idpatio}")
    public ResponseEntity<Long> mostrarQtdVagas(@PathVariable long idpatio) {
        Optional<Patio> patio = ptS.buscarPatioPorId(idpatio);

        if (patio.isPresent()) {
            return ResponseEntity.ok(patio.get().getQtdvagas());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado");
        }
    }


    @DeleteMapping("/{idpatio}")
    public ResponseEntity<Void> deletarPatio(@PathVariable long idpatio) {
        ptS.deletarPatio(idpatio);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/limparcache")
    public ResponseEntity<Void> limparCache() {
        ptS.limparCache();
        return ResponseEntity.noContent().build();
    }
}

