package validation;

import domain.Tema;

public class TemaValidator implements Validator<Tema> {

    /**
     * Valideaza o tema
     * @param entity - tema pe care o valideaza
     * @throws ValidationException daca tema nu e valida
     */
    @Override
    public void validate(Tema entity) throws ValidationException {
        if(entity.getID() == null || entity.getID().equals("")) { //exchanged order
            throw new ValidationException("Numar tema invalid!");
        }
        if(entity.getDescriere() == null || entity.getDescriere().equals("")){ //added null validation
            throw new ValidationException("Descriere invalida!");
        }
        if(entity.getDeadline() < 1 || entity.getDeadline() > 14) {
            throw new ValidationException("Deadlineul trebuie sa fie intre 1-14.");
        }
        if(entity.getPrimire() < 1 || entity.getPrimire() > 14) {
            throw new ValidationException("Saptamana primirii trebuie sa fie intre 1-14.");
        }

        // added more validation
        if (entity.getPrimire() > entity.getDeadline()) {
            throw new ValidationException("Data primirii trebuie sa fie mai mica decat data primirii.");
        }
    }
}
