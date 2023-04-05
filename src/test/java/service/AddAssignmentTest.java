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
        Tema tema = new Tema(null, DESCRIERE_OK, 2, 1);

        assertThrows(ValidationException.class, () -> service.addTema(tema), ID_INCORRECT_ERROR_MESSAGE);
    }
}
