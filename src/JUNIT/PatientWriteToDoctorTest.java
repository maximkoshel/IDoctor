package JUNIT;

import GUI.PatientWriteToDoctor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientWriteToDoctorTest {

    private PatientWriteToDoctor patientWriteToDoctor = new PatientWriteToDoctor();

    @Test
    void validateMassage() {
        assertTrue(patientWriteToDoctor.validateTextLength("jkabkjsajbsakj askjdsakjd"));
        assertFalse(patientWriteToDoctor.validateTextLength(""));
    }

}