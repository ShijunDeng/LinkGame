package cn.fouad.exception;

 
public class GameException extends Exception {
	private static final long serialVersionUID = 1L;

	public GameException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GameException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public GameException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public GameException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public GameException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
