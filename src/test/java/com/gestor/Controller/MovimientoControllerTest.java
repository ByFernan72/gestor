package com.gestor.Controller;

import com.gestorBackend.Controller.MovimientoController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestorBackend.Dto.Request.MovimientoRequestDto;
import com.gestorBackend.Service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.gestorBackend.MainApp;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovimientoController.class)
@ContextConfiguration(classes = MainApp.class)
class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovimientoService movimientoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void transferirBalance_debeDevolverOkYMensaje() throws Exception {
        MovimientoRequestDto dto = new MovimientoRequestDto(1L, 2L, 1L, 2L, new BigDecimal("10"), new BigDecimal("10"), "NuevoActivo", null);

        doNothing().when(movimientoService).transferirBalance(
                dto.getIdCarteraOrigen(),
                dto.getIdCarteraDestino(),
                dto.getIdActivoOrigen(),
                dto.getIdActivoDestino(),
                dto.getCantidadOrigen(),
                dto.getCantidadDestino(),
                dto.getNombreNuevoActivo(),
                dto.getTipoNuevoActivo()
        );

        mockMvc.perform(post("/api/v1/movimiento/transferencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transferencia realizada con éxito"));
    }

    @Test
    void transferirBalance_conCantidadesNegativas_debeDevolverBadRequest() throws Exception {
        MovimientoRequestDto invalidDto = new MovimientoRequestDto(1L, 2L, 1L, 2L, new BigDecimal("-10"), new BigDecimal("-5"), null, null);

        mockMvc.perform(post("/api/v1/movimiento/transferencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}