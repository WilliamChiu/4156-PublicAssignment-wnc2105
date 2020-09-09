package models;

public class Player {

  /**
   * A Player is a user who connects to the game through /newgame or /joingame.
   *
   * @param type Either 'X' or 'O'
   * @param id Either 1 or 2
   */
  public Player(char type, int id) {
    super();
    this.type = type;
    this.id = id;
  }

  private char type;

  private int id;

  public char getType() {
    return type;
  }

  public void setType(char type) {
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
