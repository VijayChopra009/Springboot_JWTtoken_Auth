package exception;

public class BadApiRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public BadApiRequestException() {
		super("Invalid UserName and Password !");
	}
	public BadApiRequestException(String str) {
		super(str);
	}

}
