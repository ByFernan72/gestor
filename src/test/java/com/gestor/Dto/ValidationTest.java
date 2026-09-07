package com.gestor.Dto;

import com.gestorBackend.Dto.Request.CrearCarteraRequestDto;
import com.gestorBackend.Dto.Request.MovimientoRequestDto;
import com.gestorBackend.Dto.Request.PerfilRequestDto;
import com.gestorBackend.Model.Activo;
import com.gestorBackend.Model.Cartera;
import com.gestorBackend.Model.Enum.TipoActivo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void crearCarteraRequestDto_valido_noDebeTenerErrores() {
        CrearCarteraRequestDto.crearActivoRequestDto activoDto =
                new CrearCarteraRequestDto.crearActivoRequestDto("Bitcoin", TipoActivo.CRYPTO, new BigDecimal("50000.00"));
        CrearCarteraRequestDto dto = new CrearCarteraRequestDto("Cartera Crypto", "Descripción válida", List.of(activoDto));

        Set<ConstraintViolation<CrearCarteraRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void crearCarteraRequestDto_nombreVacio_debeTenerError() {
        CrearCarteraRequestDto dto = new CrearCarteraRequestDto("", "Desc", null);

        Set<ConstraintViolation<CrearCarteraRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nombreCartera"));
    }

    @Test
    void crearCarteraRequestDto_activoConBalanceNegativo_debeTenerError() {
        CrearCarteraRequestDto.crearActivoRequestDto activoInvalido =
                new CrearCarteraRequestDto.crearActivoRequestDto("ETH", TipoActivo.CRYPTO, new BigDecimal("-10.00"));
        CrearCarteraRequestDto dto = new CrearCarteraRequestDto("Cartera", "Desc", List.of(activoInvalido));

        Set<ConstraintViolation<CrearCarteraRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().contains("balance"));
    }

    @Test
    void movimientoRequestDto_valido_noDebeTenerErrores() {
        MovimientoRequestDto dto = new MovimientoRequestDto(
                1L, 2L, 10L, 20L,
                new BigDecimal("100.50"), new BigDecimal("95.00"),
                null, null
        );

        Set<ConstraintViolation<MovimientoRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void movimientoRequestDto_cantidadNegativa_debeTenerError() {
        MovimientoRequestDto dto = new MovimientoRequestDto(
                1L, 2L, 10L, null,
                new BigDecimal("-50.00"), new BigDecimal("0.00"),
                "Nuevo", TipoActivo.FIAT
        );

        Set<ConstraintViolation<MovimientoRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void perfilRequestDto_valido_noDebeTenerErrores() {
        PerfilRequestDto dto = new PerfilRequestDto("Mi Perfil", List.of("https://example.com"));

        Set<ConstraintViolation<PerfilRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void perfilRequestDto_nombreVacio_debeTenerError() {
        PerfilRequestDto dto = new PerfilRequestDto("  ", List.of());

        Set<ConstraintViolation<PerfilRequestDto>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void activoEntity_balanceBigDecimal_precisionYValidacion() {
        Activo activo = new Activo(1L, 1L, "S&P 500", TipoActivo.ACCION, new BigDecimal("123456789.987654321"), null);

        Set<ConstraintViolation<Activo>> violations = validator.validate(activo);
        assertThat(violations).isEmpty();
        assertThat(activo.getBalance()).isEqualByComparingTo("123456789.987654321");
    }

    @Test
    void carteraEntity_validaciones() {
        Cartera carteraValida = new Cartera(1L, "Ahorro", "Fondo de emergencia", null);
        Set<ConstraintViolation<Cartera>> violations = validator.validate(carteraValida);
        assertThat(violations).isEmpty();

        Cartera carteraInvalida = new Cartera(1L, "", null, null);
        Set<ConstraintViolation<Cartera>> violationsInvalida = validator.validate(carteraInvalida);
        assertThat(violationsInvalida).isNotEmpty();
    }
}