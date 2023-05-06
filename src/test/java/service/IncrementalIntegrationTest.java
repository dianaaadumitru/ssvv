package service;


import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncrementalIntegrationTest {
    @Mock
    private StudentXMLRepo studentXMLRepo;

    @Mock
    private StudentValidator studentValidator;

    @Mock
    private TemaXMLRepo temaXMLRepo;

    @Mock
    private TemaValidator temaValidator;

    @Mock
    private NotaXMLRepo notaXMLRepo;

    @Mock
    private NotaValidator notaValidator;

    @InjectMocks
    private Service service;

    @Test
    public void studentGiven_addStudent_studentAdded() {
        // given
        Student student = new Student("1", "Diana", 933, "diana@yahoo.com");
        when(studentXMLRepo.save(ArgumentMatchers.any(Student.class))).thenReturn(null);

        // when
        Student studentAdded = service.addStudent(student);

        // then
        assertNull(studentAdded);
        verify(studentXMLRepo).save(ArgumentMatchers.any(Student.class));
    }

    @Test
    public void studentAndAssignmentGiven_addStudentAddAssignment_studentAndAssignmentAdded() {
        // given
        Student student = new Student("1", "Diana", 933, "diana@yahoo.com");
        when(studentXMLRepo.save(ArgumentMatchers.any(Student.class))).thenReturn(null);

        Tema tema = new Tema("1", "assignment1", 2, 1);
        when(temaXMLRepo.save(ArgumentMatchers.any(Tema.class))).thenReturn(null);

        // when
        Student studentAdded = service.addStudent(student);
        Tema temaAdded = service.addTema(tema);

        // then
        assertNull(studentAdded);
        verify(studentXMLRepo).save(ArgumentMatchers.any(Student.class));
        assertNull(temaAdded);
        verify(temaXMLRepo).save(ArgumentMatchers.any(Tema.class));
    }

    @Test
    public void studentAssignmentAndGradeGiven_addStudentAddAssignmentAddGrade_studentAssignmentAndGradeNotAddedDate() {
        // given
        Student student = new Student("1", "Diana", 933, "diana@yahoo.com");
        when(studentXMLRepo.save(ArgumentMatchers.any(Student.class))).thenReturn(null);

        Tema tema = new Tema("1", "assignment1", 2, 1);
        when(temaXMLRepo.save(ArgumentMatchers.any(Tema.class))).thenReturn(null);

        Nota nota = new Nota("1", student.getID(), tema.getID(),7, LocalDate.of(2018,10,15));
        when(notaXMLRepo.save(ArgumentMatchers.any(Nota.class))).thenReturn(null);

        when(studentXMLRepo.findOne(student.getID())).thenReturn(student);
        when(temaXMLRepo.findOne(tema.getID())).thenReturn(tema);

        // when
        Student studentAdded = service.addStudent(student);
        Tema temaAdded = service.addTema(tema);
        Nota notaAdded = service.addNota(nota, "ok");

        // then
        assertNull(studentAdded);
        verify(studentXMLRepo).save(ArgumentMatchers.any(Student.class));
        assertNull(temaAdded);
        verify(temaXMLRepo).save(ArgumentMatchers.any(Tema.class));
        assertNull(notaAdded);
        verify(notaXMLRepo).save(ArgumentMatchers.any(Nota.class));
    }

}
