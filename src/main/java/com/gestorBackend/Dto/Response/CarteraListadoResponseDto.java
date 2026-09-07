package com.gestorBackend.Dto.Response;

import com.gestorBackend.Dto.Request.CarteraListadoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteraListadoResponseDto {
    List<CarteraListadoRequestDto> carteras;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
}
