package JUNIT;

import GUI.DoctorLocalAnswer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoctorLocalAnswerTest {

    private DoctorLocalAnswer doctorLocalAnswer = new DoctorLocalAnswer();
    @Test
    void validateTextLength() {
        assertTrue(doctorLocalAnswer.validateTextLength("asdsajdas","asdhbsadjas"));
        assertFalse(doctorLocalAnswer.validateTextLength("a","asdhbsadjas"));
    }
}