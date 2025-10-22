package org.example.yardflow.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.yardflow.dto.MotoDTO;
import org.example.yardflow.model.Moto;
import org.example.yardflow.model.Yardflow;
import org.example.yardflow.repository.MotoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class MotoCachingService {

    @Autowired
    private MotoRepositorio mtRp;

    @Autowired
    private ModelMapper mm;

    @Cacheable(value = "motoCache", key = "#idmoto")
    public Optional<Moto> findById(long idmoto) {
        return mtRp.findById(idmoto);
    }

    @Cacheable(value = "motoCache", key = "'placa:' + #placa")
    public MotoDTO findByPlaca(String placa) {
        Moto moto = mtRp.findByPlaca(placa);
        return mm.map(moto, MotoDTO.class);
    }

    @Cacheable(value = "motoCache", key = "'chassi:' + #chassi")
    public MotoDTO findByChassi(String chassi) {
        Moto moto = mtRp.findByChassi(chassi);
        return mm.map(moto, MotoDTO.class);
    }

    @Cacheable(value = "motoCache", key = "'historico' + #idmoto")
    public MotoDTO buscarHistorico(long idmoto) {
        String moto = mtRp.historicoMoto(idmoto);
        return mm.map(moto, MotoDTO.class);
    }

    @Cacheable(value = "motoCache", key = "'paginado' + #idmoto")
    public Page<MotoDTO> findAllPaginado(Pageable pageable) {
        Page<Moto> motosPage = mtRp.findAll(pageable);
        return motosPage.map(moto -> mm.map(moto, MotoDTO.class));
    }

    @CacheEvict(value = "motoCache", allEntries = true)
    public MotoDTO criarNovaMoto(MotoDTO motoDTO) {
        try {
            // Validar dados obrigatórios
            if (motoDTO.getPlaca() == null || motoDTO.getPlaca().trim().isEmpty()) {
                throw new IllegalArgumentException("Placa é obrigatória");
            }
            if (motoDTO.getChassi() == null || motoDTO.getChassi().trim().isEmpty()) {
                throw new IllegalArgumentException("Chassi é obrigatório");
            }
            if (motoDTO.getModelo() == null) {
                throw new IllegalArgumentException("Modelo é obrigatório");
            }
            if (motoDTO.getHistorico() == null || motoDTO.getHistorico().trim().isEmpty()) {
                throw new IllegalArgumentException("Histórico é obrigatório");
            }

            motoDTO.setIdmoto(0);
            Moto moto = mm.map(motoDTO, Moto.class);
            Moto savedMoto = mtRp.save(moto);
            return mm.map(savedMoto, MotoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar nova moto: " + e.getMessage(), e);
        }
    }


    @CachePut(value = "motoCache", key = "#idmoto")
    @Caching(evict = {
            @CacheEvict(value = "motoCache", key = "'placa:' + #result.placa", condition = "#result != null"),
            @CacheEvict(value = "motoCache", key = "'chassi:' + #result.chassi", condition = "#result != null")
    })
    public MotoDTO atualizarRegistroMoto(long idmoto, MotoDTO motoDTO) {

        mtRp.findById(idmoto).orElseThrow(() -> new EntityNotFoundException("Moto não encontrada: " + idmoto));

        Moto motoToUpdate = mm.map(motoDTO, Moto.class);
        motoToUpdate.setIdmoto(idmoto);
        Moto updatedMoto = mtRp.save(motoToUpdate);
        return mm.map(updatedMoto, MotoDTO.class);
    }



    @CacheEvict(value = "motoCache", allEntries = true)
    @Transactional
    public boolean deletarRegistroMoto(long idmoto) {
        Moto moto = mtRp.findById(idmoto)
                .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada: " + idmoto));
        
        // Desassociar o Yardflow se existir
        if (moto.getYardflow() != null) {
            Yardflow yardflow = moto.getYardflow();
            yardflow.setMoto(null);
            moto.setYardflow(null);
        }
        
        // Limpar os registros de check-in/out associados
        if (moto.getRegistrosCheckInOut() != null && !moto.getRegistrosCheckInOut().isEmpty()) {
            moto.getRegistrosCheckInOut().clear();
        }
        
        mtRp.delete(moto);
        return true;
    }


    @CacheEvict(value = "motoCache", allEntries = true)
    public void limparCache() {
        System.out.println(" Removendo todos os caches de 'motoCache' ");
    }


}
