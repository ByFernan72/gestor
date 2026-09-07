package com.gestor.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestorBackend.Controller.CarteraController;
import com.gestorBackend.Dto.Request.CarteraListadoRequestDto;
import com.gestorBackend.Dto.Response.CarteraListadoResponseDto;
import com.gestorBackend.Dto.Request.CrearCarteraRequestDto;
import com.gestorBackend.Model.Cartera;
import com.gestorBackend.Service.CarteraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.gestorBackend.MainApp;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarteraController.class)
@ContextConfiguration(classes = MainApp.class)
class CarteraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarteraService carteraService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearCartera_debeDevolverOk() throws Exception {
        CrearCarteraRequestDto request = new CrearCarteraRequestDto("MiCartera", "desc", null);
        Cartera saved = new Cartera(1L, "MiCartera", "desc", LocalDateTime.now());

        when(carteraService.guardarCarteraDto(any(CrearCarteraRequestDto.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/cartera/crearCartera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCartera").value(saved.getIdCartera()))
                .andExpect(jsonPath("$.nombreCartera").value("MiCartera"));
    }

    @Test
    void listarCarteras_debeDevolverListado() throws Exception {
        Cartera c1 = new Cartera(1L, "C1", "d1", LocalDateTime.now());
        Cartera c2 = new Cartera(2L, "C2", "d2", LocalDateTime.now());

        when(carteraService.listarCarteras()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/v1/cartera"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCartera").value(1))
                .andExpect(jsonPath("$[1].idCartera").value(2));
    }

    @Test
    void listarTodasCarteras_debeDevolverListadoResponseDto() throws Exception {
        CarteraListadoRequestDto dto1 = new CarteraListadoRequestDto(1L, "Cartera1", "Desc1", new BigDecimal("100.50"), 5L);
        CarteraListadoRequestDto dto2 = new CarteraListadoRequestDto(2L, "Cartera2", "Desc2", new BigDecimal("200.75"), 10L);
        
        CarteraListadoResponseDto responseDto = new CarteraListadoResponseDto(List.of(dto1, dto2), 0, 1, 2L);

        when(carteraService.obtenerCarteras(any())).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/cartera/listado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carteras[0].idCartera").value(1))
                .andExpect(jsonPath("$.carteras[0].nombreCartera").value("Cartera1"))
                .andExpect(jsonPath("$.carteras[0].descripcion").value("Desc1"))
                .andExpect(jsonPath("$.carteras[0].totalCartera").value(100.50))
                .andExpect(jsonPath("$.carteras[0].totalActivosCartera").value(5))
                .andExpect(jsonPath("$.carteras[1].idCartera").value(2))
                .andExpect(jsonPath("$.carteras[1].nombreCartera").value("Cartera2"))
                .andExpect(jsonPath("$.carteras[1].totalCartera").value(200.75))
                .andExpect(jsonPath("$.carteras[1].totalActivosCartera").value(10));
    }

    @Test
    void crearCartera_conNombreVacio_debeDevolverBadRequest() throws Exception {
        CrearCarteraRequestDto request = new CrearCarteraRequestDto("", "desc", null);

        mockMvc.perform(post("/api/v1/cartera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearCartera_standardRestPost_debeDevolverOk() throws Exception {
        CrearCarteraRequestDto request = new CrearCarteraRequestDto("MiCartera", "desc", null);
        Cartera saved = new Cartera(1L, "MiCartera", "desc", LocalDateTime.now());

        when(carteraService.guardarCarteraDto(any(CrearCarteraRequestDto.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/cartera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCartera").value(saved.getIdCartera()))
                .andExpect(jsonPath("$.nombreCartera").value("MiCartera"));
    }
}