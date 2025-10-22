package org.example.yardflow.service;

import org.example.yardflow.dto.RegistroPermanenciaDTO;
import org.example.yardflow.model.Moto;
import org.example.yardflow.model.Registro_check_in_out;

import org.example.yardflow.repository.MotoRepositorio;
import org.example.yardflow.repository.Registro_check_in_outRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class Registro_check_in_outCachingService {

    @Autowired
    private Registro_check_in_outRepositorio rgR;

    @Autowired
    private MotoRepositorio mtR;



    @CacheEvict(value = "registroCache", allEntries = true)
    public Registro_check_in_out inserirDataEntrada(long idmoto, LocalDate entrada) {
        Moto moto = mtR.findById(idmoto).orElseThrow(() ->
                new IllegalArgumentException("Moto não encontrada com id: " + idmoto));

        rgR.findTopByMoto_IdmotoAndSaidapatioIsNullOrderByEntradapatioDesc(idmoto)
                .ifPresent(r -> { throw new IllegalStateException("Já existe um registro aberto para essa moto."); });

        Registro_check_in_out reg = new Registro_check_in_out();
        reg.setMoto(moto);
        reg.setEntradapatio(entrada);
        reg.setPeriodo(1);
        return rgR.save(reg);
    }


    @CacheEvict(value = "registroCache", allEntries = true)
    public Registro_check_in_out inserirDataSaida(long idmoto, LocalDate saida) {
        Registro_check_in_out reg = rgR.findTopByMoto_IdmotoAndSaidapatioIsNullOrderByEntradapatioDesc(idmoto)
                .orElseThrow(() -> new IllegalArgumentException("Nenhum registro aberto encontrado para a moto id: " + idmoto));

        if (saida.isBefore(reg.getEntradapatio())) {
            throw new IllegalArgumentException("Data de saída não pode ser anterior à entrada.");
        }

        reg.setSaidapatio(saida);
        long periodo = calcularPeriodoEntre(reg.getEntradapatio(), saida);
        reg.setPeriodo(periodo);
        return rgR.save(reg);
    }

    @Cacheable(value = "registroCache", key = "'entrada:' + #entradapatio")
    public List<Registro_check_in_out> buscarPorEntrada(LocalDate entradapatio) {
        return rgR.findAllByEntradapatio(entradapatio);
    }

    @Cacheable(value = "registroCache", key = "'saida:' + #saidapatio")
    public List<Registro_check_in_out> buscarPorSaida(LocalDate saidapatio) {
        return rgR.findAllBySaidapatio(saidapatio);
    }


    // Calcula permanência (se existir saida usa saida, senão agora)
    @Cacheable(value = "registroCache", key = "'permanencia:moto:' + #idmoto")
    public long calcularPermanenciaPorIdMoto(long idmoto) {
        // tenta primeiro registro aberto, senão o último registro existente
        Registro_check_in_out reg = rgR.findTopByMoto_IdmotoAndSaidapatioIsNullOrderByEntradapatioDesc(idmoto)
                .orElseGet(() -> rgR.findTopByMoto_IdmotoOrderByEntradapatioDesc(idmoto)
                        .orElseThrow(() -> new IllegalArgumentException("Registro não encontrado para moto id: " + idmoto)));

        LocalDate saida = reg.getSaidapatio();
        return calcularPeriodoEntre(reg.getEntradapatio(), saida);
    }

    private long calcularPeriodoEntre(LocalDate entrada, LocalDate saida) {
        if (entrada == null) throw new IllegalArgumentException("Data de entrada é obrigatória.");
        LocalDate fim = (saida != null) ? saida : LocalDate.now();
        if (fim.isBefore(entrada)) throw new IllegalArgumentException("Data de saída anterior à entrada.");
        long dias = ChronoUnit.DAYS.between(entrada, fim);
        return (long) Math.max(dias, 1);
    }

    // Lista todos os registros com entrada e retorna paginado/ordenado pela permanência (desc)
    public Page<RegistroPermanenciaDTO> listarPorPermanenciaOrdenada(Pageable pageable) {
        List<RegistroPermanenciaDTO> all = rgR.findByEntradapatioIsNotNull().stream()
                .map(r -> {
                    long periodo = calcularPeriodoEntre(r.getEntradapatio(), r.getSaidapatio());
                    return new RegistroPermanenciaDTO(
                            0,
                            r.getMoto() != null ? r.getMoto().getIdmoto() : 0,
                            r.getMoto() != null ? r.getMoto().getModelo() : null,
                            r.getEntradapatio(),
                            r.getSaidapatio(),
                            periodo
                    );
                })
                .sorted(Comparator.comparingLong(RegistroPermanenciaDTO::getPeriodo).reversed())
                .collect(Collectors.toList());

        final int start = Math.toIntExact(pageable.getOffset());
        final int end = Math.min(start + pageable.getPageSize(), all.size());
        List<RegistroPermanenciaDTO> content = (start <= end) ? all.subList(start, end) : List.of();

        return new PageImpl<>(content, pageable, all.size());
    }


    @CacheEvict(value = "registroCache", allEntries = true)
    public void limparCache(){
        System.out.println(" Removendo arquivos de cache dos Registros ");

    }
}
