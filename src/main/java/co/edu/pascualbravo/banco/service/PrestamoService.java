package co.edu.pascualbravo.banco.service;

import co.edu.pascualbravo.banco.exception.ClienteNoValidoException;
import co.edu.pascualbravo.banco.exception.MontoNoValidoException;
import co.edu.pascualbravo.banco.model.Prestamo;
import co.edu.pascualbravo.banco.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PrestamoService {

    private static final double MAXIMO_MONTO = 50_000.0;

    private final PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo procesarSolicitud(Long clienteId, double monto, String tipoCliente) {
        validarDatosSolicitante(clienteId, monto);

        Prestamo prestamo = new Prestamo();
        prestamo.setClienteId(clienteId);
        prestamo.setMonto(monto);

        if (monto > MAXIMO_MONTO) {
            prestamo.setEstado("RECHAZADO");
            prestamo.setTasaInteres(0.0);
            return prestamoRepository.save(prestamo);
        }

        prestamo.setEstado("APROBADO");
        prestamo.setTasaInteres(obtenerTasa(tipoCliente));
        return prestamoRepository.save(prestamo);
    }

    public double calcularDescuentoCartera(List<Prestamo> prestamos) {
        if (prestamos == null || prestamos.isEmpty()) {
            return 0.0;
        }

        return prestamos.stream()
                .filter(Objects::nonNull)
                .filter(prestamo -> "APROBADO".equals(prestamo.getEstado()))
                .mapToDouble(this::calcularDescuentoPrestamo)
                .sum();
    }

    private void validarDatosSolicitante(Long clienteId, double monto) {
        if (clienteId == null) {
            throw new ClienteNoValidoException("El cliente no puede ser nulo");
        }

        if (monto <= 0) {
            throw new MontoNoValidoException("El monto debe ser mayor a cero");
        }
    }

    private double obtenerTasa(String tipoCliente) {
        if ("PREMIUM".equalsIgnoreCase(tipoCliente)) {
            return 3.5;
        }

        if ("REGULAR".equalsIgnoreCase(tipoCliente)) {
            return 5.0;
        }

        return 6.0;
    }

    private double calcularDescuentoPrestamo(Prestamo prestamo) {
        double monto = prestamo.getMonto();

        if (monto > 10_000) {
            return monto * 0.05;
        }

        if (monto > 5_000) {
            return monto * 0.02;
        }

        return 0.0;
    }
}