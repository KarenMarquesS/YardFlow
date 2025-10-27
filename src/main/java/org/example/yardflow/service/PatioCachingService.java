package org.example.yardflow.service;

import org.example.yardflow.dto.PatioDTO;
import org.example.yardflow.model.Patio;
import org.example.yardflow.repository.PatioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PatioCachingService {

    @Autowired
    private PatioRepositorio ptR;


    @Cacheable(value="patioCache", key = "#idpatio")
    public Optional<Patio> buscarPatioPorId(long idpatio){
        return ptR.findById(idpatio);
    }

    @Cacheable(value = "patioCache", key = "'qtdvagas' + #qtdvagas")
    public List<Patio> buscarQtdVagas(long qtdvagas){
        if (qtdvagas > 0){
            throw new IllegalArgumentException("Quantidade de vagas deve ser maoir de ZERO");
        }
        List<Patio> patios = ptR.findByQtdvagas(qtdvagas);
        if (patios.isEmpty()){throw new IllegalArgumentException("Nenhuma pátio encontrado com a quaindade de vaga informado");
        }
        return patios;
    }

    @Cacheable(value="patioCache", key = "#name")
    public Patio buscarPatioPorNome(String name){
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("O nome do pátio não pode ser vazio");
        }
        Patio patio = ptR.findByName(name);
        if (patio == null){
            throw new IllegalArgumentException("Nome " + name + "Não encontrado");
        }
        return patio;
    }


    @CacheEvict(value="patioCache", allEntries=true)
    public Patio inserirPatio(PatioDTO dto){
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("O nome do pátio é obrigatório");
        }
        Patio patio = dto.toEntity();
        return ptR.save(patio);
    }


    @CacheEvict(value="patioCache", allEntries=true)
    public Patio atualizarPatio(long idpatio, PatioDTO dto){
        Patio existente = ptR.findById(idpatio)
                .orElseThrow(() -> new IllegalArgumentException("ID informado não localizado"));

        existente.setName(dto.getName());
        existente.setQtdvagas(dto.getQtdvagas());

        return ptR.save(existente);
    }


    @CacheEvict(value = "patioCache", allEntries = true)
    public void deletarPatio(long idpatio){
        if (!ptR.existsById(idpatio)){
            throw new IllegalArgumentException("Pátio não localizado");
        }
        ptR.deleteById(idpatio);
    }


    @CacheEvict(value = "patioCache", allEntries = true)
    public void limparCache(){
        System.out.println(" Removendo arquivos de cache de Patio! ");

    }

    @Cacheable(value = "patioCache", key = "'todos'")
    public List<Patio> buscarTodosPatios() {
        return ptR.findAll();
    }
}
