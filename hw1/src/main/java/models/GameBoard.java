package models;

public class GameBoard {

  /**
   * The object representation of the game state.
   *
   * @param p1 The first player
   * @param p2 The second player
   * @param gameStarted Whether both players have joined
   * @param turn Which player's turn it is
   * @param boardState The board representation
   * @param winner Whether there is a winner
   * @param isDraw Whether there is a draw
   */
  public GameBoard(
      Player p1,
      Player p2,
      boolean gameStarted,
      int turn,
      char[][] boardState,
      int winner,
      boolean isDraw) {
    super();
    this.p1 = p1;
    this.p2 = p2;
    this.gameStarted = gameStarted;
    this.turn = turn;
    this.boardState = boardState;
    this.winner = winner;
    this.isDraw = isDraw;
  }

  private Player p1;

  private Player p2;

  private boolean gameStarted;

  private int turn;

  private char[][] boardState;

  private int winner;

  private boolean isDraw;

  public Player getP1() {
    return p1;
  }

  public void setP1(Player p1) {
    this.p1 = p1;
  }

  public Player getP2() {
    return p2;
  }

  public void setP2(Player p2) {
    this.p2 = p2;
  }

  public boolean isGameStarted() {
    return gameStarted;
  }

  public void setGameStarted(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  public int getTurn() {
    return turn;
  }

  public void setTurn(int turn) {
    this.turn = turn;
  }

  public char[][] getBoardState() {
    return boardState;
  }

  public void setBoardState(char[][] boardState) {
    this.boardState = boardState;
  }

  public int getWinner() {
    return winner;
  }

  public void setWinner(int winner) {
    this.winner = winner;
  }

  public boolean isDraw() {
    return isDraw;
  }

  public void setDraw(boolean isDraw) {
    this.isDraw = isDraw;
  }
}
