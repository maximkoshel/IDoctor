package JUNIT;

import GUI.RegisterFormDoctor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterFormDoctorTest {

    private RegisterFormDoctor registerFormDoctor = new RegisterFormDoctor();
    @Test
    void validateId() {
        assertTrue(registerFormDoctor.validateId("205635204"));
        assertFalse(registerFormDoctor.validateId("2056204"));
    }

    @Test
    void validatePassword() {
        assertTrue(registerFormDoctor.validatePassword("akdsow123a"));
        assertFalse(registerFormDoctor.validatePassword("akdso"));
    }

    @Test
    void validateName() {
        assertTrue(registerFormDoctor.validateName("isajd","dsaijdi"));
        assertFalse(registerFormDoctor.validateName("isaj21d","dsaijdi"));
        assertFalse(registerFormDoctor.validateName("isajd","dsaij23di"));
    }

    @Test
    void validatePhoneNumber() {
        assertTrue(registerFormDoctor.validatePhoneNumber("0544244321","0934501231"));
        assertFalse(registerFormDoctor.validatePhoneNumber("0544221","0934501231"));
        assertFalse(registerFormDoctor.validatePhoneNumber("0544244321","0934231"));
    }

    @Test
    void validateEmail() {
        assertTrue(registerFormDoctor.validateEmail("kobis@gmail.com"));
        assertFalse(registerFormDoctor.validateEmail("kobisgmail.com"));
        assertFalse(registerFormDoctor.validateEmail("kobis@gmailcom"));
    }

    @Test
    void validateCity() {
        assertTrue(registerFormDoctor.validateCity("yavne","yavne"));
        assertFalse(registerFormDoctor.validateCity("yavn213e","tel aviv"));
        assertFalse(registerFormDoctor.validateCity("yavne","tel a12viv"));
    }

    @Test
    void validateStreet() {
        assertTrue(registerFormDoctor.validateStreet("adasd","sadsad"));
        assertFalse(registerFormDoctor.validateStreet("adas2d","sadsad"));
        assertFalse(registerFormDoctor.validateStreet("adasd","sadsa2d"));
    }

    @Test
    void validateUniversity() {
        assertTrue(registerFormDoctor.validateUniversity("dasdasdad"));
        assertFalse(registerFormDoctor.validateUniversity("dasdas21dad"));
    }
}