package co.edu.pascualbravo.banco.service;

import co.edu.pascualbravo.banco.exception.ClienteNoValidoException;
import co.edu.pascualbravo.banco.exception.MontoNoValidoException;
import co.edu.pascualbravo.banco.model.Prestamo;
import co.edu.pascualbravo.banco.repository.PrestamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoService prestamoService;

    @BeforeEach
    void setUp() {
        lenient().when(prestamoRepository.save(any(Prestamo.class))).thenAnswer(invocation -> {
            Prestamo prestamo = invocation.getArgument(0);
            prestamo.setId(1L);
            return prestamo;
        });
    }

    @Test
    void debeRechazarCreditoSuperiorA50000() {
        Prestamo prestamo = prestamoService.procesarSolicitud(10L, 50_001, "PREMIUM");

        assertEquals("RECHAZADO", prestamo.getEstado());
        assertEquals(0.0, prestamo.getTasaInteres());
    }

    @Test
    void debeAplicarTasaPreferencialParaPremium() {
        Prestamo prestamo = prestamoService.procesarSolicitud(10L, 15_000, "PREMIUM");

        assertEquals("APROBADO", prestamo.getEstado());
        assertEquals(3.5, prestamo.getTasaInteres());
    }

    @Test
    void debeAplicarTasaRegular() {
        Prestamo prestamo = prestamoService.procesarSolicitud(10L, 15_000, "REGULAR");

        assertEquals("APROBADO", prestamo.getEstado());
        assertEquals(5.0, prestamo.getTasaInteres());
    }

    @Test
    void debeAplicarTasaPorDefectoParaOtrosClientes() {
        Prestamo prestamo = prestamoService.procesarSolicitud(10L, 15_000, "NUEVO");

        assertEquals("APROBADO", prestamo.getEstado());
        assertEquals(6.0, prestamo.getTasaInteres());
    }

    @Test
    void debeLanzarExcepcionCuandoClienteEsNulo() {
        ClienteNoValidoException exception = assertThrows(ClienteNoValidoException.class,
                () -> prestamoService.procesarSolicitud(null, 10_000, "PREMIUM"));

        assertEquals("El cliente no puede ser nulo", exception.getMessage());
    }

    @Test
    void debeLanzarExcepcionCuandoMontoEsNegativo() {
        MontoNoValidoException exception = assertThrows(MontoNoValidoException.class,
                () -> prestamoService.procesarSolicitud(10L, -1, "PREMIUM"));

        assertEquals("El monto debe ser mayor a cero", exception.getMessage());
    }

    @Test
    void debeCalcularDescuentoCarteraAprobada() {
        List<Prestamo> cartera = List.of(
                crearPrestamo(12_000, "APROBADO"),
                crearPrestamo(7_000, "APROBADO"),
                crearPrestamo(20_000, "RECHAZADO")
        );

        double totalDescuento = prestamoService.calcularDescuentoCartera(cartera);

        assertEquals(12_000 * 0.05 + 7_000 * 0.02, totalDescuento);
    }

    private Prestamo crearPrestamo(double monto, String estado) {
        Prestamo prestamo = new Prestamo();
        prestamo.setMonto(monto);
        prestamo.setEstado(estado);
        return prestamo;
    }
}
