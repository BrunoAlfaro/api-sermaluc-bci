package com.sermaluc.userapi.controller;

import com.sermaluc.userapi.dto.UserDTO;
import com.sermaluc.userapi.dto.UserResponseDTO;
import com.sermaluc.userapi.entity.User;
import com.sermaluc.userapi.exception.UserAlreadyExistsException;
import com.sermaluc.userapi.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UserResponseDTO registerUser(@RequestBody UserDTO usuarioDTO) {
        try {
            User usuario = usuarioService.registerUser(usuarioDTO); 
            
            UserResponseDTO response = new UserResponseDTO();
            response.setId(usuario.getId());
            response.setCreated(usuario.getCreated());
            response.setModified(usuario.getModified());
            response.setLastLogin(usuario.getLastLogin());
            response.setToken(usuario.getToken());
            response.setIsActive(usuario.getIsActive());
            return response;
            
        } catch (UserAlreadyExistsException e) {
            // Capturamos la excepción personalizada y devolvemos el mensaje adecuado
            UserResponseDTO response = new UserResponseDTO();
            response.setMessage(e.getMessage());  // "El correo ya está registrado."
            return response;
        } catch (Exception e) {
            // Captura cualquier otro error general
            UserResponseDTO response = new UserResponseDTO();
            response.setMessage("Ocurrió un error inesperado");
            return response;
        }
    }
}
