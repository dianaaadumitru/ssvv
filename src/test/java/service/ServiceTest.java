package service;

import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;
import validation.StudentValidator;
import validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    public static final String NAME_OK = "Diana";
    public static final int GROUP_OK = 933;
    public static final String EMAIL_OK = "diana@yahoo.com";
    public static final String ID_STUDENT_OK = "986";
    public static final String ID_INCORRECT_ERROR_MESSAGE = "Id incorect!";
    public static final String NAME_INCORRECT_ERROR_MESSAGE = "Nume incorect!";
    public static final String EMAIL_INCORRECT_ERROR_MESSAGE = "Email incorect!";
    public static final String GROUP_INCORRECT_ERROR_MESSAGE = "Grupa incorecta!";
    public static final String ONE_CHARACTER_VALUE = "v";
    public static final String TWO_CHARACTERS_VALUE = "v1";

    private static Service service;

    @BeforeAll
    static void initService() {
        String filenameStudent = "fisiere/Studenti.xml";
        StudentValidator studentValidator = new StudentValidator();
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        service = new Service(studentXMLRepository, studentValidator, null, null, null, null);
    }

    @Test
    void validStudent_addStudent_StudentAdded() {
        //
        Student student = new Student(ID_STUDENT_OK, "Diana", 933, "diana@yahoo.com");

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ID_STUDENT_OK);
    }

    @Test
    void existingStudent_addStudent_StudentNotAdded() {

        Student student = new Student("1", NAME_OK, GROUP_OK, EMAIL_OK);

        Student newStudent = service.addStudent(student);
        assertEquals(newStudent, student);
    }

    @Test
    void studentWithNullId_addStudent_StudentNotAdded() {

        Student student = new Student(null, NAME_OK, GROUP_OK, EMAIL_OK);

        assertThrows(ValidationException.class, () -> service.addStudent(student), ID_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void studentWithEmptyId_addStudent_StudentNotAdded() {

        Student student = new Student("", NAME_OK, GROUP_OK, EMAIL_OK);

        assertThrows(ValidationException.class, () -> service.addStudent(student), ID_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void studentWithNullName_addStudent_StudentNotAdded() {
        Student student = new Student(ID_STUDENT_OK, null, GROUP_OK, EMAIL_OK);

        assertThrows(ValidationException.class, () -> service.addStudent(student), NAME_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void studentWithEmptyName_addStudent_StudentNotAdded() {
        Student student = new Student(ID_STUDENT_OK, "", GROUP_OK, EMAIL_OK);

        assertThrows(ValidationException.class, () -> service.addStudent(student), NAME_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void studentWithNullEmail_addStudent_StudentNotAdded() {
        Student student = new Student(ID_STUDENT_OK, NAME_OK, GROUP_OK, null);

        assertThrows(ValidationException.class, () -> service.addStudent(student), EMAIL_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void studentWithEmptyEmail_addStudent_StudentNotAdded() {
        Student student = new Student(ID_STUDENT_OK, NAME_OK, GROUP_OK, "");

        assertThrows(ValidationException.class, () -> service.addStudent(student), EMAIL_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void studentWithMaxIntGroup_addStudent_StudentAdded() {
        Student student = new Student(ID_STUDENT_OK, NAME_OK, Integer.MAX_VALUE, EMAIL_OK);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ID_STUDENT_OK);
    }

    @Test
    void studentWithGroup0_addStudent_StudentNotAdded() {

        Student student = new Student(ID_STUDENT_OK, NAME_OK, 0, EMAIL_OK);

        assertThrows(ValidationException.class, () -> service.addStudent(student), GROUP_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void studentWithGroup1_addStudent_StudentAdded() {

        Student student = new Student(ID_STUDENT_OK, NAME_OK, 1, EMAIL_OK);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ID_STUDENT_OK);
    }

    @Test
    void studentWithNegativeGroup_addStudent_StudentNotAdded() {

        Student student = new Student(ID_STUDENT_OK, NAME_OK, -1, EMAIL_OK);

        assertThrows(ValidationException.class, () -> service.addStudent(student), GROUP_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void studentWithMaxIntMinus1Group_addStudent_StudentAdded() {

        Student student = new Student(ID_STUDENT_OK, NAME_OK, Integer.MAX_VALUE - 1, EMAIL_OK);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ID_STUDENT_OK);
    }

    @Test
    void studentWithOneCharacterId_addStudent_StudentAdded() {

        Student student = new Student(ONE_CHARACTER_VALUE, NAME_OK, GROUP_OK, EMAIL_OK);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ONE_CHARACTER_VALUE);
    }

    @Test
    void studentWithTwoCharactersId_addStudent_StudentAdded() {

        Student student = new Student(TWO_CHARACTERS_VALUE, NAME_OK, GROUP_OK, EMAIL_OK);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(TWO_CHARACTERS_VALUE);
    }

    @Test
    void studentWithOneCharacterName_addStudent_StudentAdded() {

        Student student = new Student(ID_STUDENT_OK, ONE_CHARACTER_VALUE, GROUP_OK, EMAIL_OK);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ID_STUDENT_OK);
    }

    @Test
    void studentWithTwoCharactersName_addStudent_StudentAdded() {

        Student student = new Student(ID_STUDENT_OK, TWO_CHARACTERS_VALUE, GROUP_OK, EMAIL_OK);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ID_STUDENT_OK);
    }

    @Test
    void studentWithOneCharacterEmail_addStudent_StudentAdded() {

        Student student = new Student(ID_STUDENT_OK, NAME_OK, GROUP_OK, ONE_CHARACTER_VALUE);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ID_STUDENT_OK);
    }

    @Test
    void studentWithTwoCharactersEmail_addStudent_StudentAdded() {

        Student student = new Student(ID_STUDENT_OK, NAME_OK, GROUP_OK, TWO_CHARACTERS_VALUE);

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent(ID_STUDENT_OK);
    }
}