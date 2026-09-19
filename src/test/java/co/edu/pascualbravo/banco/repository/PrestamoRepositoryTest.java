package co.edu.pascualbravo.banco.repository;

import co.edu.pascualbravo.banco.model.Prestamo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PrestamoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Test
    void debeRetornarPrestamosPorEstado() {
        guardarPrestamo(1L, 10_000, 3.5, "APROBADO");
        guardarPrestamo(2L, 15_000, 5.0, "APROBADO");
        guardarPrestamo(3L, 40_000, 6.0, "RECHAZADO");

        List<Prestamo> aprobados = prestamoRepository.findByEstado("APROBADO");

        assertEquals(2, aprobados.size());
        assertEquals(10_000, aprobados.get(0).getMonto());
        assertEquals(15_000, aprobados.get(1).getMonto());
    }

    private void guardarPrestamo(Long clienteId, double monto, double tasaInteres, String estado) {
        Prestamo prestamo = new Prestamo();
        prestamo.setClienteId(clienteId);
        prestamo.setMonto(monto);
        prestamo.setTasaInteres(tasaInteres);
        prestamo.setEstado(estado);
        entityManager.persistAndFlush(prestamo);
    }
}
