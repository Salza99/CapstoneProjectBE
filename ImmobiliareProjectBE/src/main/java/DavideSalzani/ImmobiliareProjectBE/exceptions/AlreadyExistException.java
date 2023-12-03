package DavideSalzani.ImmobiliareProjectBE.exceptions;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String whichRecord){
        super(whichRecord + "è già presente a database");
    }
}
