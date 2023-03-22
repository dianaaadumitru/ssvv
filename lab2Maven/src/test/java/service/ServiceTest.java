package service;

import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.StudentXMLRepo;
import validation.StudentValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
    private static Service service;

    @BeforeAll
    static void initService() {
        String filenameStudent = "fisiere/Studenti.xml";
        StudentValidator studentValidator = new StudentValidator();
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        service = new Service(studentXMLRepository, studentValidator, null, null, null, null);
    }

    @Test
    void validStudentGiven_addStudent_StudentAdded() {
        //given
        Student student = new Student("986", "Diana", 933, "diana@yahoo.com");

        Student newStudent = service.addStudent(student);
        assertNull(newStudent);

        service.deleteStudent("986");
    }

    @Test
    void existingStudentGiven_addStudent_StudentNotAdded() {
        //given
        Student student = new Student("1", "Diana", 933, "diana@yahoo.com");

        Student newStudent = service.addStudent(student);
        assertEquals(newStudent, student);
    }

}