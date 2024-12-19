package com.sermaluc.userapi.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ValidationUtilTest {

    @Value("${password.regex}")
    private String passwordRegex;

    @Test
    void validatePassword_ShouldThrowException_WhenPasswordDoesNotMatchRegex() {
        String invalidPassword = "123";

        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> ValidationUtil.validatePassword(invalidPassword, passwordRegex));

        assertEquals("La clave no cumple con los requisitos de seguridad.", exception.getMessage());
    }

    @Test
    void validatePassword_ShouldPass_WhenPasswordMatchesRegex() {
        String validPassword = "Password123";

        ValidationUtil.validatePassword(validPassword, passwordRegex);
    }
}
