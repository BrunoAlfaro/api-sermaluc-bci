package com.sermaluc.userapi.controller;

import com.sermaluc.userapi.dto.UserDTO;
import com.sermaluc.userapi.dto.UserResponseDTO;
import com.sermaluc.userapi.entity.User;
import com.sermaluc.userapi.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioControllerTest {

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioController controller;

    @Test
    void registerUser_ShouldReturnResponseDTO_WhenValidData() {
        // Configurar el mock
        Mockito.when(usuarioService.registerUser(Mockito.any(UserDTO.class))).thenAnswer(invocation -> {
            User user = new User();
            user.setId(12345L); // Cambia a Long
            user.setCreated(LocalDateTime.now());
            user.setModified(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());
            user.setToken("mocked-token");
            user.setIsActive(true);
            return user;
        });
    
        // Crear el DTO de entrada
        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setName("Test User");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setPassword("Password123");
    
        // Llamar al método del controlador
        UserResponseDTO response = controller.registerUser(usuarioDTO);
    
        // Validar los resultados
        assertNotNull(response, "La respuesta no debe ser null");
        assertEquals(12345L, response.getId(), "El ID devuelto no coincide");
        assertNotNull(response.getCreated(), "La fecha de creación no debe ser null");
        assertNotNull(response.getModified(), "La fecha de modificación no debe ser null");
        assertNotNull(response.getToken(), "El token no debe ser null");
        assertEquals("mocked-token", response.getToken(), "El token devuelto no es el esperado");
        assertTrue(response.getIsActive(), "El estado 'isActive' debería ser true");
    }
    
    @Test
    void registerUser_ShouldReturnError_WhenInvalidData() {
        // Configurar el mock para un caso de error (datos inválidos)
        Mockito.when(usuarioService.registerUser(Mockito.any(UserDTO.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));
        
        // Crear un DTO con datos inválidos
        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setEmail("invalid-email");  // Aquí el email no tiene formato correcto
        
        // Llamar al método del controlador y verificar que la excepción es capturada
        UserResponseDTO response = controller.registerUser(usuarioDTO);
        
        // Validar que la respuesta tiene el mensaje de error adecuado
        assertNotNull(response.getMessage(), "El mensaje de error no debe ser null");
        assertEquals("Ocurrió un error inesperado", response.getMessage(), "El mensaje de error no es el esperado");
    }
}
