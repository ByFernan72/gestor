package com.gestorBackend.Controller;

import com.gestorBackend.Dto.Response.DashboardResumenResponseDto;
import com.gestorBackend.Service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @CrossOrigin(origins = "*") // @CrossOrigin(origins = "http://localhost:puerto")
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/resumen")
    public ResponseEntity<DashboardResumenResponseDto> getResumen() {
        return ResponseEntity.ok(dashboardService.obtenerResumenDashboard());
    }
}
