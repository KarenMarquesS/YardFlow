//package org.example.yardflow;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.example.yardflow.control.api.MotoController;
//import org.example.yardflow.dto.MotoDTO;
//import org.example.yardflow.model.Moto;
//import org.example.yardflow.service.MotoCachingService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@WebMvcTest(MotoController.class)
//public class testeEndpointAPImoto {
//
//        @Autowired
//        private MockMvc mockMvc;
//
//        @Autowired
//        private MotoCachingService mtS;
//
//        @Autowired
//        private ModelMapper mm;/**/
//
//        private MotoDTO motoDTO;
//        private Moto moto;
//
//        @BeforeEach
//        void setUp() {
//            motoDTO = new MotoDTO();
//            motoDTO.setIdmoto(1);
//            motoDTO.setPlaca("ABC-1234");
//            // Inicializar outros campos conforme necessário
//
//            moto = new Moto();
//            moto.setIdmoto(1);
//            moto.setPlaca("ABC-1234");
//        }
//
//    @Test
//    void testFindAll() throws Exception {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<MotoDTO> motoPage = new PageImpl<>(Collections.singletonList(motoDTO), pageable, 1);
//        when(mtS.findAllPaginado(any(Pageable.class))).thenReturn(motoPage);
//
//        mockMvc.perform(get("/moto")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].idmoto").value(1))
//                .andExpect(jsonPath("$.content[0].placa").value("ABC-1234"));
//    }
//
//    @Test
//    void testFindByIdSuccess() throws Exception {
//        when(mtS.findById(1)).thenReturn(Optional.of(moto));
//        when(mm.map(any(Moto.class), eq(MotoDTO.class))).thenReturn(motoDTO);
//
//        mockMvc.perform(get("/moto/{idmoto}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.idmoto").value(1))
//                .andExpect(jsonPath("$.placa").value("ABC-1234"));
//    }
//
//    @Test
//    void testFindByIdNotFound() throws Exception {
//        when(mtS.findById(any(Integer.class))).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/moto/{idmoto}", 999))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testCriarNovaMoto() throws Exception {
//        when(mtS.criarNovaMoto(any(MotoDTO.class))).thenReturn(motoDTO);
//
//        mockMvc.perform(post("/moto")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"placa\":\"ABC-1234\"}")) // Exemplo de JSON de entrada
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.idmoto").value(1))
//                .andExpect(jsonPath("$.placa").value("ABC-1234"));
//    }
//
//    @Test
//    void testAtualizarRegistroMoto() throws Exception {
//        when(mtS.atualizarRegistroMoto(eq(1), any(MotoDTO.class))).thenReturn(motoDTO);
//
//        mockMvc.perform(put("/moto/{idmoto}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"placa\":\"XYZ-5678\"}")) // Exemplo de JSON de entrada
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.idmoto").value(1))
//                .andExpect(jsonPath("$.placa").value("ABC-1234"));
//    }
//
//    @Test
//    void testDeletarRegistroMoto() throws Exception {
//        doNothing().when(mtS).deletarRegistroMoto(1);
//
//        mockMvc.perform(delete("/moto/{idmoto}", 1))
//                .andExpect(status().isNoContent());
//
//        verify(mtS, times(1)).deletarRegistroMoto(1);
//    }
//}
