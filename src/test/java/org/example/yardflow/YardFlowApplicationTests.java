//package org.example.yardflow;
//
//import org.example.yardflow.model.Registro_check_in_out;
//import org.example.yardflow.service.PatioCachingService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class YardFlowApplicationTests {
//
//    @Test
//    void contextLoads() {
//    }
//
//
//    @Test
//    void deveCalcularPeriodoComEntradaESaida() {
//        Registro_check_in_out registro = new Registro_check_in_out();
//        registro.setEntradapatio(LocalDate.now().minusDays(3));
//        registro.setSaidapatio(LocalDate.now());
//
//        registro.calcularPeriodoPermanencia();
//
//        assertEquals(3, registro.getPeriodo());
//    }
//
//    @Test
//    void deveCalcularPeriodoComSaidaNula() {
//        Registro_check_in_out registro = new Registro_check_in_out();
//        registro.setEntradapatio(LocalDate.now().minusDays(2));
//        registro.setSaidapatio(null); // usuário não informou saída
//
//        registro.calcularPeriodoPermanencia();
//
//        assertEquals(2, registro.getPeriodo());
//    }
//
//    @Test
//    void deveRetornarUmDiaQuandoEntradaIgualSaida() {
//        Registro_check_in_out registro = new Registro_check_in_out();
//        registro.setEntradapatio(LocalDate.now());
//        registro.setSaidapatio(LocalDate.now());
//
//        registro.calcularPeriodoPermanencia();
//
//        assertEquals(1, registro.getPeriodo());
//    }
//
//    @Test
//    void deveLancarExcecaoQuandoEntradaNula() {
//        Registro_check_in_out registro = new Registro_check_in_out();
//
//        assertThrows(IllegalStateException.class, registro::calcularPeriodoPermanencia);
//    }
//
//    @Test
//    void deveLancarExcecaoQuandoSaidaAntesDaEntrada() {
//        Registro_check_in_out registro = new Registro_check_in_out();
//        registro.setEntradapatio(LocalDate.now());
//        registro.setSaidapatio(LocalDate.now().minusDays(1));
//
//        assertThrows(IllegalArgumentException.class, registro::calcularPeriodoPermanencia);
//    }
//
//
//
//    @Autowired
//    private PatioCachingService patioService;
//
//    @Test
//    void testarBuscaPorId() {
//        var patio = patioService.buscarPatioPorId(1);
//        assertNotNull(patio);
//        System.out.println("Patio encontrado: ");
//    }
//
//}
