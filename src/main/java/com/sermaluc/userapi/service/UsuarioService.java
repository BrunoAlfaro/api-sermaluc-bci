package com.sermaluc.userapi.service;

import com.sermaluc.userapi.dto.UserDTO;
import com.sermaluc.userapi.entity.Phone;
import com.sermaluc.userapi.entity.User;
import com.sermaluc.userapi.exception.UserAlreadyExistsException;
import com.sermaluc.userapi.repository.UsuarioRepository;
import com.sermaluc.userapi.security.JwtUtil;
import com.sermaluc.userapi.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final String passwordRegex;

    public UsuarioService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, @Value("${password.regex}") String passwordRegex) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordRegex = passwordRegex;
    }

    public User registerUser(UserDTO usuarioDTO) {
        // Verificar si el correo ya está registrado
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new UserAlreadyExistsException("El correo ya está registrado.");
        }

        ValidationUtil.validateEmail(usuarioDTO.getEmail());
        ValidationUtil.validatePassword(usuarioDTO.getPassword(), passwordRegex);

        User usuario = new User();

        usuario.setName(usuarioDTO.getName());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setCreated(LocalDateTime.now());
        usuario.setModified(LocalDateTime.now());
        usuario.setLastLogin(LocalDateTime.now());
        usuario.setIsActive(true);

        // Generar token JWT
        try {
            usuario.setToken(jwtUtil.generateToken(usuarioDTO.getEmail()));
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el token JWT: " + e.getMessage());
        }

        // Mapeo de la lista de PhoneDTO a Phone
        if (usuarioDTO.getPhones() != null) {
            usuario.setPhones(usuarioDTO.getPhones().stream().map(phoneDTO -> {
                Phone phone = new Phone();
                phone.setNumber(phoneDTO.getNumber());
                phone.setCitycode(phoneDTO.getCitycode());
                phone.setContrycode(phoneDTO.getContrycode());
                return phone;
            }).collect(Collectors.toList()));
        }

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    public User saveTestUser(User usuario) {
        // Este método se usará para crear un usuario de prueba directamente en la base de datos
        return usuarioRepository.save(usuario);
    }
}
