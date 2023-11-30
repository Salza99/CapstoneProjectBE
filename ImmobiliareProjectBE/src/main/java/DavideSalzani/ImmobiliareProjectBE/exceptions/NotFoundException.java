package DavideSalzani.ImmobiliareProjectBE.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String whichRecord){
        super(whichRecord + " non trovato a Database");
    }
}
