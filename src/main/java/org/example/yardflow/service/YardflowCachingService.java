package org.example.yardflow.service;


import org.example.yardflow.model.Moto;
import org.example.yardflow.model.Yardflow;
import org.example.yardflow.repository.MotoRepositorio;
import org.example.yardflow.repository.YardflowRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class YardflowCachingService {

    @Autowired
    private YardflowRepositorio yfRep;

    @Autowired
    private MotoRepositorio mtR;

    @CacheEvict(value = "yfCache", allEntries = true)
    @Transactional
    public Yardflow criarNovoYardFlow(Yardflow yf) {
        try {
            // Validar dados obrigatórios
            if (yf.getSerial() == null || yf.getSerial().trim().isEmpty()) {
                throw new IllegalArgumentException("Serial é obrigatório");
            }
            
            // Verificar se já existe um YardFlow com o mesmo serial
            Optional<Yardflow> existingYf = yfRep.findBySerial(yf.getSerial());
            if (existingYf.isPresent()) {
                throw new IllegalArgumentException("Já existe um YardFlow com o serial: " + yf.getSerial());
            }
            
            // Se há uma moto associada, verificar se ela já tem um YardFlow
            if (yf.getMoto() != null) {
                Moto moto = yf.getMoto();
                if (moto.getYardflow() != null) {
                    throw new IllegalArgumentException("A moto " + moto.getPlaca() + " já possui um YardFlow associado");
                }
                // Estabelecer o relacionamento bidirecional
                moto.setYardflow(yf);
                yf.setMoto(moto);
            }
            
            return yfRep.save(yf);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar novo YardFlow: " + e.getMessage(), e);
        }
    }

    public Moto localizarMotoPorYardFlow(Long idyf) {
        return yfRep.findById(idyf)
                .map(Yardflow::getMoto)
                .orElseThrow(() -> new IllegalArgumentException("YardFlow não encontrado para o ID: " + idyf));
    }

    @Cacheable(value = "yfCache", key = "#ativar")
    public Yardflow ativarYardFlow(long idyf, long idmoto){

        Yardflow yf = yfRep.findById(idyf).orElseThrow(()-> new IllegalArgumentException("YardFlow não localizado"));

        Moto moto = mtR.findById(idmoto).orElseThrow(()-> new IllegalArgumentException("Moto não localizada"));

        if (moto.getYardflow() != null){
            throw new IllegalArgumentException("Moto já possui uma yardflow ativo");
        }

        moto.setYardflow(yf);
        yf.setMoto(moto);
        yf.setDtUltimoAcionamento(LocalDateTime.now());

        return yfRep.save(yf);
    }

    @Cacheable(value = "yfCache", key = "'serial' + #serial")
    public Optional<Yardflow> buscarSerial ( String serial) {
        return yfRep.findBySerial(serial);
    }

    @Cacheable(value = "yfCache", key = "'acionamento' + #dt_ultimo_acionamento")
    public List<Yardflow> buscarDtUltimoAcionamento (LocalDate dt_ultimo_acionamento) {
        LocalDateTime startOfDay = dt_ultimo_acionamento.atStartOfDay();
        LocalDateTime endOfDay = dt_ultimo_acionamento.atTime(23, 59, 59);
        return yfRep.findByDtUltimoAcionamentoBetween(startOfDay, endOfDay);
    }

    @Cacheable(value = "yfCache", key = "#desativar")
    public Yardflow desativarYardFlow(long idyf){
        Yardflow yf = yfRep.findById(idyf).orElseThrow(()-> new IllegalArgumentException("YardFlow não encontrado"));
        Moto moto = yf.getMoto();
        if (moto == null){
            throw new IllegalArgumentException("YardFlow não esta associado a nenhuma moto");
        }

        moto.setYardflow(null);
        yf.setMoto(null);

        return yfRep.save(yf);
    }

    @Cacheable(value = "yfCache", key = "#idyf")
    public Moto localizarMotoPorYardFlow(long idyf){
        Yardflow yf = yfRep.findById(idyf).orElseThrow(()-> new IllegalArgumentException("YardFlow não encontrado"));

        return yf.getMoto();
    }

    @CacheEvict(value = "yfCache", allEntries = true)
    @Transactional
    public void removerYardFlow(long idyf){
        Yardflow yardflow = yfRep.findById(idyf)
                .orElseThrow(() -> new IllegalArgumentException("YardFlow não encontrado"));
        
        // Desassociar todas as motos que referenciam este Yardflow
        if (yardflow.getMoto() != null) {
            Moto moto = yardflow.getMoto();
            moto.setYardflow(null);
            yardflow.setMoto(null);
            // Salvar a moto para atualizar a referência
            mtR.save(moto);
        }
        
        // Agora pode deletar o Yardflow sem problemas de constraint
        yfRep.deleteById(idyf);
    }

    @CacheEvict(value = "yfCache", allEntries = true)
    public void limparCache(){
        System.out.println(" Removendo arquivos de cache de YardFlow! ");

    }

}

