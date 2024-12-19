package com.sermaluc.userapi.util;

public class ValidationUtil {

    public static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("El correo no tiene un formato válido.");
        }
    }

    public static void validatePassword(String password, String regex) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La clave no puede ser nula o vacía.");
        }
        if (regex == null || regex.isEmpty()) {
            throw new IllegalArgumentException("La expresión regular para validar la clave no puede ser nula o vacía.");
        }
        if (!password.matches(regex)) {
            throw new IllegalArgumentException("La clave no cumple con los requisitos de seguridad.");
        }
    }
}
