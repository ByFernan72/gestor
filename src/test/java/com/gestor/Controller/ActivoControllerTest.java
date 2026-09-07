package com.gestor.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestorBackend.Controller.ActivoController;
import com.gestorBackend.Model.Activo;
import com.gestorBackend.Model.Enum.TipoActivo;
import com.gestorBackend.Service.ActivoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActivoController.class)
@ContextConfiguration(classes = MainApp.class)
class ActivoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ActivoService activoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearActivo_debeDevolverOk() throws Exception {
        Activo request = new Activo(null, 1L, "Activo1", TipoActivo.FIAT, new BigDecimal("100.00"), null);
        Activo saved = new Activo(1L, 1L, "Activo1", TipoActivo.FIAT, new BigDecimal("100.00"), LocalDateTime.now());

        when(activoService.guardarActivo(any(Activo.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/activo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idActivo").value(saved.getIdActivo()))
                .andExpect(jsonPath("$.nombreActivo").value("Activo1"));
    }

    @Test
    void crearActivo_conDatosInvalidos_debeDevolverBadRequest() throws Exception {
        Activo invalidRequest = new Activo(null, 1L, "", null, new BigDecimal("-10.00"), null);

        mockMvc.perform(post("/api/v1/activo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}