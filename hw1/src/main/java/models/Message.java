package models;

public class Message {

  /**
   * A Message is sent as feedback for every move attempted.
   *
   * @param moveValidity Whether the move was valid or not
   * @param code An identifier for the type of Message
   * @param message Text to display as feedback to the user
   */
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
