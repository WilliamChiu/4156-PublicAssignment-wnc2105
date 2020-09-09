package models;

public class Move {

  /**
   * A Move must be made by a player to a spot in the 3x3 grid.
   *
   * @param player The player performing the move
   * @param moveX The x-coordinate of the move
   * @param moveY The y-coordinate of the move
   */
  public Move(Player player, int moveX, int moveY) {
    super();
    this.player = player;
    this.moveX = moveX;
    this.moveY = moveY;
  }

  private Player player;

  private int moveX;

  private int moveY;

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public int getMoveX() {
    return moveX;
  }

  public void setMoveX(int moveX) {
    this.moveX = moveX;
  }

  public int getMoveY() {
    return moveY;
  }

  public void setMoveY(int moveY) {
    this.moveY = moveY;
  }
}
