package co.edu.pascualbravo.banco.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeResponderConHttp200YJsonEsperadoParaSolicitudValida() throws Exception {
        mockMvc.perform(post("/api/prestamos/solicitar")
                        .param("clienteId", "15")
                        .param("monto", "25000")
                        .param("tipoCliente", "PREMIUM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(15))
                .andExpect(jsonPath("$.monto").value(25000))
                .andExpect(jsonPath("$.estado").value("APROBADO"))
                .andExpect(jsonPath("$.tasaInteres").value(3.5));
    }
}
