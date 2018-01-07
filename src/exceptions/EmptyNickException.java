package exceptions;

public class EmptyNickException extends Exception {
	public EmptyNickException(){
		super();
	}
	public EmptyNickException(String message) {
        super(message);
    }
}
