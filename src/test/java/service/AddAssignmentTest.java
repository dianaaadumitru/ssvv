package service;

import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddAssignmentTest {
    public static final String ID_TEMA_OK = "100";
    public static final String DESCRIERE_OK = "descriere ok";
    private static final String ID_INCORRECT_ERROR_MESSAGE = "Numar tema invalid!";

    private static final String DESCRIERE_INCORRECT_ERROR_MESSAGE = "Descriere invalida!";
    private static final String DEADLINE_INCORRECT_ERROR_MESSAGE = "Deadlineul trebuie sa fie intre 1-14.";
    private static final String PRIMIRE_INCORRECT_ERROR_MESSAGE = "Saptamana primirii trebuie sa fie intre 1-14.";

    private static final String PRIMIRE_DEADLINE_INCORRECT_ERROR_MESSAGE = "Data primirii trebuie sa fie mai mica decat data primirii.";
    public static final int DEADLINE_OK = 2;
    public static final int PRIMIRE_OK = 1;
    public static final int DEADLINE_NOT_OK = 15;
    public static final int PRIMIRE_NOT_OK = 15;
    private static Service service;

    @BeforeAll
    static void initService() {
        String filename = "fisiere/Teme.xml";
        TemaValidator temaValidator = new TemaValidator();
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filename);
        service = new Service(null, null, temaXMLRepository, temaValidator, null, null);
    }

    @Test
    void validTema_addTema_temaAdded() {
        Tema tema = new Tema(ID_TEMA_OK, DESCRIERE_OK, 2, 1);

        Tema newTema = service.addTema(tema);

        assertNull(newTema);

        service.deleteTema(ID_TEMA_OK);
    }

    @Test
    void temaWithNullId_addTema_temaNotAdded() {
        Tema tema = new Tema(null, DESCRIERE_OK, DEADLINE_OK, PRIMIRE_OK);

        assertThrows(ValidationException.class, () -> service.addTema(tema), ID_INCORRECT_ERROR_MESSAGE);
    }
    @Test
    void temaCuDescriereEmptyString_addTema_temaNotAdded() {
        Tema tema = new Tema(ID_TEMA_OK, "", DEADLINE_OK, PRIMIRE_OK);

        assertThrows(ValidationException.class, () -> service.addTema(tema), DESCRIERE_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void temaWithInvalidDeadline_addTema_temaNotAdded() {
        Tema tema = new Tema(ID_TEMA_OK, DESCRIERE_OK, DEADLINE_NOT_OK, PRIMIRE_OK);

        assertThrows(ValidationException.class, () -> service.addTema(tema), DEADLINE_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void temaCuPrimireInvalida_addTema_temaNotAdded() {
        Tema tema = new Tema(ID_TEMA_OK, DESCRIERE_OK, DEADLINE_OK, PRIMIRE_NOT_OK);

        assertThrows(ValidationException.class, () -> service.addTema(tema), PRIMIRE_INCORRECT_ERROR_MESSAGE);
    }

    @Test
    void temaCuPrimireMaiMicaDecatDeadline_addTema_temaNotAdded() {
        Tema tema = new Tema(ID_TEMA_OK, DESCRIERE_OK, 2, 3);

        assertThrows(ValidationException.class, () -> service.addTema(tema), PRIMIRE_DEADLINE_INCORRECT_ERROR_MESSAGE);
    }
}
