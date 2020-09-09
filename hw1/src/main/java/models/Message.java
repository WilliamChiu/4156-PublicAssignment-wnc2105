package models;

public class Message {

	public Message(boolean moveValidity, int code, String message) {
		super();
		this.moveValidity = moveValidity;
		this.code = code;
		this.message = message;
	}

	private boolean moveValidity;
	
	private int code;
	
	private String message;

	public boolean isMoveValidity() {
		return moveValidity;
	}

	public void setMoveValidity(boolean moveValidity) {
		this.moveValidity = moveValidity;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
