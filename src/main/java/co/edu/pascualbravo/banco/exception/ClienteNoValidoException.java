package co.edu.pascualbravo.banco.exception;

public class ClienteNoValidoException extends RuntimeException {
    public ClienteNoValidoException(String message) {
        super(message);
    }
}
