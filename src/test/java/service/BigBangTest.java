package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BigBangTest {
    public static final String NAME_OK = "Diana";
    public static final int GROUP_OK = 933;
    public static final String EMAIL_OK = "diana@yahoo.com";
    public static final String ID_INCORRECT_ERROR_MESSAGE = "Id incorect!";

    public static final String DESCRIERE_OK = "descriere ok";
    public static final int DEADLINE_OK = 6;
    public static final int PRIMIRE_OK = 1;
    private static final String TEMA_ID_INCORRECT_ERROR_MESSAGE = "Numar tema invalid!";

    private static Service service;

    @BeforeAll
    static void initService() {
        String filenameStudent = "fisiere/Studenti.xml";
        StudentValidator studentValidator = new StudentValidator();
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);

        String filenameTema = "fisiere/Teme.xml";
        TemaValidator temaValidator = new TemaValidator();
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);

        String filenameNota = "fisiere/Teme.xml";
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    void studentWithNullId_addStudent_StudentNotAdded() {

        Student student = new Student(null, NAME_OK, GROUP_OK, EMAIL_OK);

        assertThrows(ValidationException.class, () -> service.addStudent(student), ID_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void temaWithNullId_addTema_temaNotAdded() {
        Tema tema = new Tema(null, DESCRIERE_OK, DEADLINE_OK, PRIMIRE_OK);

        assertThrows(ValidationException.class, () -> service.addTema(tema), TEMA_ID_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void notaWithStudentIdInvalid_addNota_notaNotAdded() {
        Nota nota = new Nota("100", "100", "100", 10, LocalDate.of(2023,4,26));

        assertThrows(ValidationException.class, () -> service.addNota(nota, "ok"),"\"Studentul nu exista!\"");
    }

    @Test
    void notaWithDateNotOk_addNota_notaNotAdded() {
        Tema tema = new Tema("101", DESCRIERE_OK, 2, 1);
        Tema newTema = service.addTema(tema);
        assertNull(newTema);

        Student student = new Student("101", NAME_OK, GROUP_OK, EMAIL_OK);
        Student studentSaved = service.addStudent(student);
        assertNull(studentSaved);

        Nota nota = new Nota("101", student.getID(), tema.getID(),7, LocalDate.of(2018,10,7));
        assertThrows(ValidationException.class, () -> service.addNota(nota, "ok"),"\"Studentul nu mai poate preda aceasta tema !\"");


        service.deleteNota("101");
        service.deleteStudent("101");
        service.deleteTema("101");
    }
}
