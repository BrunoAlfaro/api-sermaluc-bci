package com.sermaluc.userapi.service;

import com.sermaluc.userapi.dto.UserDTO;
import com.sermaluc.userapi.entity.User;
import com.sermaluc.userapi.exception.UserAlreadyExistsException;
import com.sermaluc.userapi.repository.UsuarioRepository;
import com.sermaluc.userapi.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Value("${password.regex}")
    private String passwordRegex;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(usuarioRepository, jwtUtil, passwordRegex);
        Mockito.when(jwtUtil.generateToken(any())).thenReturn("mocked-token");  // Simula la generación del token JWT
    }

    @Test
    void registerUser_ShouldSaveUser_WhenValidData() {
        // Crear el DTO de usuario
        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setName("Juan Perez");
        usuarioDTO.setEmail("juan.perez@example.com");
        usuarioDTO.setPassword("Password123");

        // Simulamos que el correo no está registrado
        Mockito.when(usuarioRepository.existsByEmail(any())).thenReturn(false);

        // Simulamos que el usuario se guarda correctamente
        Mockito.when(usuarioRepository.save(any())).thenAnswer(invocation -> {
            User usuario = invocation.getArgument(0);
            usuario.setId(12345L); 
            usuario.setCreated(LocalDateTime.now());
            usuario.setModified(LocalDateTime.now());
            usuario.setLastLogin(LocalDateTime.now());
            return usuario;
        });

        // Llamamos al método de registro
        User usuario = usuarioService.registerUser(usuarioDTO);

        // Verificamos que el ID no sea null
        assertNotNull(usuario.getId());
        // Verificamos que el correo del usuario sea el correcto
        assertEquals("juan.perez@example.com", usuario.getEmail());
        // Verificamos que el token JWT sea el correcto
        assertEquals("mocked-token", usuario.getToken());
        // Verificamos que el estado 'isActive' sea true
        assertTrue(usuario.getIsActive());
        // Verificamos que las fechas no sean null
        assertNotNull(usuario.getCreated());
        assertNotNull(usuario.getModified());
        assertNotNull(usuario.getLastLogin());
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailAlreadyExists() {
        // Crear el DTO de usuario con un correo ya existente
        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setEmail("juan.perez@example.com");

        // Simulamos que el correo ya está registrado
        Mockito.when(usuarioRepository.existsByEmail(any())).thenReturn(true);

        // Verificamos que se lance la excepción correcta
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> usuarioService.registerUser(usuarioDTO));

        // Verificamos el mensaje de la excepción
        assertEquals("El correo ya está registrado.", exception.getMessage());
    }

    @Test
    void saveTestUser_ShouldSaveUserDirectly() {
        // Crear un usuario de prueba
        User usuario = new User();
        usuario.setName("Test User");
        usuario.setEmail("test@example.com");
        usuario.setPassword("Password123");
        usuario.setCreated(LocalDateTime.now());

        // Simulamos el guardado del usuario
        Mockito.when(usuarioRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(12345L); // Simulamos la generación del ID
            return savedUser;
        });

        // Llamamos al método para guardar el usuario
        User savedUser = usuarioService.saveTestUser(usuario);

        // Verificamos que el ID del usuario guardado no sea null
        assertNotNull(savedUser.getId());
        assertEquals(12345L, savedUser.getId());  // Verificamos que el ID generado sea el esperado
        assertEquals("test@example.com", savedUser.getEmail());  // Verificamos que el correo del usuario guardado sea el esperado
    }

    @Test
    void registerUser_ShouldThrowException_WhenInvalidEmail() {
        // Crear el DTO de usuario con un correo inválido
        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setEmail("invalid-email");

        // Verificamos que la excepción sea lanzada al registrar un correo inválido
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registerUser(usuarioDTO));
    }

    @Test
    void registerUser_ShouldThrowException_WhenInvalidPassword() {
        // Crear el DTO de usuario con una contraseña inválida
        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setEmail("juan.perez@example.com");
        usuarioDTO.setPassword("123");  // Contraseña demasiado corta

        // Verificamos que la excepción sea lanzada por la validación de contraseña
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registerUser(usuarioDTO));
    }
}

