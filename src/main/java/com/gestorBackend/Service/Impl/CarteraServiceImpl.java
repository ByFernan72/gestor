package com.gestorBackend.Service.Impl;

import com.gestorBackend.Dto.Request.CarteraListadoRequestDto;
import com.gestorBackend.Dto.Response.CarteraListadoResponseDto;
import com.gestorBackend.Dto.Request.CrearCarteraRequestDto;
import com.gestorBackend.Dto.Request.ObtenerDatosCompletosCarteraRequestDto;
import com.gestorBackend.Model.Activo;
import com.gestorBackend.Model.Cartera;
import com.gestorBackend.Repository.ActivoRepository;
import com.gestorBackend.Repository.CarteraRepository;
import com.gestorBackend.Service.CarteraService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class CarteraServiceImpl implements CarteraService {

    private final CarteraRepository carteraRepository;
    private final ActivoRepository activoRepository;

    @Override
    public List<Cartera> listarCarteras() {
        return carteraRepository.findAll();
    }

    @Override
    public Cartera guardarCartera(Cartera cartera) {
        cartera.setCreatedAt(LocalDateTime.now());
        return carteraRepository.save(cartera);
    }

    @Override
    @Transactional
    public Cartera guardarCarteraDto(CrearCarteraRequestDto carteraDto) {
        Cartera cartera = new Cartera();
        cartera.setNombreCartera(carteraDto.getNombreCartera());
        cartera.setDescripcion(carteraDto.getDescripcion());
        cartera.setCreatedAt(LocalDateTime.now());

        Cartera carteraGuardada = carteraRepository.save(cartera);

        if (carteraDto.getActivos() != null && !carteraDto.getActivos().isEmpty()) {
            for (CrearCarteraRequestDto.crearActivoRequestDto activoRequestDto : carteraDto.getActivos()) {
                Activo activo = new Activo();
                activo.setIdCartera(carteraGuardada.getIdCartera());
                activo.setNombreActivo(activoRequestDto.getNombreActivo());
                activo.setBalance(activoRequestDto.getBalance());
                activo.setTipoActivo(activoRequestDto.getTipo());
                activo.setFechaObtencionActivo(LocalDateTime.now());

                activoRepository.save(activo);
            }
        }
        return carteraGuardada;
    }

    @Override
    public ObtenerDatosCompletosCarteraRequestDto obtenerDatosCompletos(Long idCartera, Sort sort) {
        if (!carteraRepository.existsByIdCartera(idCartera)) {
            throw new IllegalArgumentException("La cartera con id " + idCartera + " no existe");
        }
        Cartera cartera = carteraRepository.getCarteraByIdCartera(idCartera);
        List<Activo> activos = activoRepository.findAllByIdCartera(idCartera, sort);

        List<ObtenerDatosCompletosCarteraRequestDto.ObtenerDatosCompletosActivoDto> activosDto = activos.stream()
                .map(a -> new ObtenerDatosCompletosCarteraRequestDto.ObtenerDatosCompletosActivoDto(
                        a.getIdActivo(),
                        a.getNombreActivo(),
                        a.getTipoActivo(),
                        a.getBalance(),
                        a.getFechaObtencionActivo()
                ))
                .toList();

        return new ObtenerDatosCompletosCarteraRequestDto(
                cartera.getIdCartera(),
                cartera.getNombreCartera(),
                cartera.getDescripcion(),
                cartera.getCreatedAt(),
                activosDto
        );
    }

    @Override
    public void eliminarCartera(Long id) {
        if (!carteraRepository.existsByIdCartera(id)) {
            throw new IllegalArgumentException("La cartera con id " + id + " no existe");
        }
        carteraRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void modificarNombreCartera(Long id, String nuevoNombre) {
        if (!carteraRepository.existsByIdCartera(id)) {
            throw new IllegalArgumentException("La cartera con id " + id + " no existe");
        }
        Cartera cartera = carteraRepository.getCarteraByIdCartera(id);

        cartera.setNombreCartera(nuevoNombre);
        carteraRepository.save(cartera);
    }

    @Override
    @Transactional
    public void modificarDescripcionCartera(Long id, String nuevaDescripcion) {
        if (!carteraRepository.existsByIdCartera(id)) {
            throw new IllegalArgumentException("La cartera con id " + id + " no existe");
        }
        Cartera cartera = carteraRepository.getCarteraByIdCartera(id);

        cartera.setDescripcion(nuevaDescripcion);
        carteraRepository.save(cartera);
    }

    @Override
    public CarteraListadoResponseDto obtenerCarteras(Pageable pageable) {
        Page<CarteraListadoRequestDto> pagina = carteraRepository.listadoCarteras(pageable);
        return new CarteraListadoResponseDto(
                pagina.getContent(),
                pagina.getNumber(),
                pagina.getTotalPages(),
                pagina.getTotalElements()
        );
    }


}

