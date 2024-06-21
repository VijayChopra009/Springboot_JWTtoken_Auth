package exception;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("Resource Not Found Exception ");
	}
	
	public ResourceNotFoundException(String str) {
		super(str);
	}

}
