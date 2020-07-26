package JUNIT;

import GUI.RegisterFormPatient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterFormPatientTest {

    private RegisterFormPatient registerFormPatient = new RegisterFormPatient();
    @Test
    void validateId() {
        assertTrue(registerFormPatient.validateId("205635204"));
        assertFalse(registerFormPatient.validateId("2056204"));
    }

    @Test
    void validatePassword() {
        assertTrue(registerFormPatient.validatePassword("akdosjf923"));
        assertFalse(registerFormPatient.validatePassword("akdosjf"));
        assertFalse(registerFormPatient.validatePassword("123213213"));
    }

    @Test
    void validateName() {
        assertTrue(registerFormPatient.validateName("kovi","sasda"));
        assertFalse(registerFormPatient.validateName("kadas123","sasd123a"));
    }

    @Test
    void validatePhoneNumber() {
        assertTrue(registerFormPatient.validatePhoneNumber("0544244322"));
        assertFalse(registerFormPatient.validatePhoneNumber("054424sad322"));
        assertFalse(registerFormPatient.validatePhoneNumber("05442"));
    }

    @Test
    void validateEmail() {
        assertTrue(registerFormPatient.validateEmail("kobisbah@gmail.com"));
        assertFalse(registerFormPatient.validateEmail("kobisbahgmail.com"));
        assertFalse(registerFormPatient.validateEmail("kobisbahgmailcom"));
    }

    @Test
    void validateCity() {
        assertTrue(registerFormPatient.validateCity("yavne"));
        assertFalse(registerFormPatient.validateCity("yavn1e"));
    }

    @Test
    void validateStreet() {
        assertTrue(registerFormPatient.validateStreet("dkoasd"));
        assertFalse(registerFormPatient.validateStreet("d"));
    }
}