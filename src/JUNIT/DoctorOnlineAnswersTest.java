package JUNIT;

import GUI.DoctorOnlineAnswers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoctorOnlineAnswersTest {

    private DoctorOnlineAnswers doctorOnlineAnswers = new DoctorOnlineAnswers();
    @Test
    void validateTextLength() {
       assertTrue(doctorOnlineAnswers.validateTextLength("dasdasdas","sadsadsa"));
       assertFalse(doctorOnlineAnswers.validateTextLength("d","sadsadsa"));
    }
}