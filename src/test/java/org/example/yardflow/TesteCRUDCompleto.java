package org.example.yardflow;

import org.example.yardflow.model.EnumModelo;
import org.example.yardflow.model.Moto;
import org.example.yardflow.model.Yardflow;
import org.example.yardflow.service.MotoCachingService;
import org.example.yardflow.service.YardflowCachingService;
import org.example.yardflow.dto.MotoDTO;
import org.example.yardflow.dto.YardflowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@SpringBootApplication
public class TesteCRUDCompleto {

    public static void main(String[] args) {
        SpringApplication.run(TesteCRUDCompleto.class, args);
    }

    @Component
    public static class TesteCRUD implements CommandLineRunner {

        @Autowired
        private MotoCachingService motoService;

        @Autowired
        private YardflowCachingService yardflowService;

        @Override
        public void run(String... args) throws Exception {
            System.out.println("=== TESTE DAS FUNCIONALIDADES CRUD ===");
            
            // Teste 1: Criar Yardflow
            System.out.println("\n1. Testando criação de Yardflow...");
            Yardflow yardflow = new Yardflow();
            yardflow.setSerial("YF001");
            yardflow.setDtUltimoAcionamento(LocalDateTime.now());
            Yardflow savedYardflow = yardflowService.criarNovoYardFlow(yardflow);
            System.out.println("✓ Yardflow criado com ID: " + savedYardflow.getIdyf());

            // Teste 2: Criar Moto
            System.out.println("\n2. Testando criação de Moto...");
            MotoDTO motoDTO = new MotoDTO();
            motoDTO.setModelo(EnumModelo.MOTTU_SPORT);
            motoDTO.setChassi("12345678901234567");
            motoDTO.setPlaca("ABC1234");
            motoDTO.setHistorico("Moto para teste do sistema CRUD");
            MotoDTO savedMoto = motoService.criarNovaMoto(motoDTO);
            System.out.println("✓ Moto criada com ID: " + savedMoto.getIdmoto());

            // Teste 3: Buscar Moto por ID
            System.out.println("\n3. Testando busca de Moto por ID...");
            var motoEncontrada = motoService.findById(savedMoto.getIdmoto());
            if (motoEncontrada.isPresent()) {
                System.out.println("✓ Moto encontrada: " + motoEncontrada.get().getPlaca());
            } else {
                System.out.println("✗ Moto não encontrada");
            }

            // Teste 4: Buscar Yardflow por Serial
            System.out.println("\n4. Testando busca de Yardflow por Serial...");
            var yardflowEncontrado = yardflowService.buscarSerial("YF001");
            if (yardflowEncontrado.isPresent()) {
                System.out.println("✓ Yardflow encontrado: " + yardflowEncontrado.get().getSerial());
            } else {
                System.out.println("✗ Yardflow não encontrado");
            }

            // Teste 5: Ativar Yardflow com Moto
            System.out.println("\n5. Testando ativação de Yardflow com Moto...");
            try {
                Yardflow yardflowAtivado = yardflowService.ativarYardFlow(savedYardflow.getIdyf(), savedMoto.getIdmoto());
                System.out.println("✓ Yardflow ativado com sucesso");
            } catch (Exception e) {
                System.out.println("✗ Erro ao ativar Yardflow: " + e.getMessage());
            }

            // Teste 6: Atualizar Moto
            System.out.println("\n6. Testando atualização de Moto...");
            savedMoto.setHistorico("Histórico atualizado - teste CRUD");
            MotoDTO motoAtualizada = motoService.atualizarRegistroMoto(savedMoto.getIdmoto(), savedMoto);
            System.out.println("✓ Moto atualizada: " + motoAtualizada.getHistorico());

            // Teste 7: Buscar histórico da Moto
            System.out.println("\n7. Testando busca de histórico da Moto...");
            try {
                MotoDTO historico = motoService.buscarHistorico(savedMoto.getIdmoto());
                System.out.println("✓ Histórico encontrado: " + historico.getHistorico());
            } catch (Exception e) {
                System.out.println("✗ Erro ao buscar histórico: " + e.getMessage());
            }

            // Teste 8: Desativar Yardflow
            System.out.println("\n8. Testando desativação de Yardflow...");
            try {
                Yardflow yardflowDesativado = yardflowService.desativarYardFlow(savedYardflow.getIdyf());
                System.out.println("✓ Yardflow desativado com sucesso");
            } catch (Exception e) {
                System.out.println("✗ Erro ao desativar Yardflow: " + e.getMessage());
            }

            // Teste 9: Deletar Moto
            System.out.println("\n9. Testando deleção de Moto...");
            boolean deletado = motoService.deletarRegistroMoto(savedMoto.getIdmoto());
            if (deletado) {
                System.out.println("✓ Moto deletada com sucesso");
            } else {
                System.out.println("✗ Erro ao deletar Moto");
            }

            // Teste 10: Deletar Yardflow
            System.out.println("\n10. Testando deleção de Yardflow...");
            try {
                yardflowService.removerYardFlow(savedYardflow.getIdyf());
                System.out.println("✓ Yardflow deletado com sucesso");
            } catch (Exception e) {
                System.out.println("✗ Erro ao deletar Yardflow: " + e.getMessage());
            }

            System.out.println("\n=== TESTE CONCLUÍDO ===");
        }
    }
}
