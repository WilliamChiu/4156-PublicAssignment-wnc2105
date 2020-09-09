package controllers;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import java.io.IOException;
import java.util.Queue;
import org.eclipse.jetty.websocket.api.Session;

import models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PlayGame {

  private static int PORT_NUMBER = 8080;

  private static Javalin app;

  private static GameBoard board;

  // Lets us know if a board shows a draw
  private static boolean isDraw(char[][] boardState) {
    for (int x = 0; x < boardState.length; x++) {
      for (int y = 0; y < boardState[x].length; y++) {
        if (boardState[x][y] == '\u0000') return false;
      }
    }
    return true;
  }

  // Goes over the rows and then the columns, and finally, diagonals, to determine possible winner or draw
  private static int getBoardStatus(char[][] boardState, int x, int y) {
    for (int row = 0; row < 3; row++) {
      if (boardState[row][0] == boardState[row][1] && boardState[row][1] == boardState[row][2]) {
        if (boardState[row][0] == 'X') {
          return x;
        } else if (boardState[row][0] == 'O') {
          return y;
        }
      }
    }
    for (int col = 0; col < 3; col++) {
      if (boardState[0][col] == boardState[1][col] && boardState[1][col] == boardState[2][col]) {
        if (boardState[0][col] == 'X') {
          return x;
        } else if (boardState[0][col] == 'O') {
          return y;
        }
      }
    }
    if (boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2]) {
      if (boardState[0][0] == 'X') {
        return x;
      } else if (boardState[0][0] == 'O') {
        return y;
      }
    } else if (boardState[0][2] == boardState[1][1] && boardState[1][1] == boardState[2][0]) {
      if (boardState[0][2] == 'X') {
        return x;
      } else if (boardState[0][2] == 'O') {
        return y;
      }
    } else if (isDraw(boardState)) return -1;
    return 0;
  }

  /** Main method of the application.
   * @param args Command line arguments
   */
  public static void main(String[] args) {

    app =
        Javalin.create(
                config -> {
                  config.addStaticFiles("/public");
                })
            .start(PORT_NUMBER);

    Gson gson = new GsonBuilder().create();
    JavalinJson.setFromJsonMapper(gson::fromJson);
    JavalinJson.setToJsonMapper(gson::toJson);

    // Test Echo Server
    app.post(
        "/echo",
        ctx -> {
          ctx.result(ctx.body());
        });

    // Redirects the first player to the view
    app.get(
        "/newgame",
        ctx -> {
          ctx.redirect("tictactoe.html");
        });

    // Starts a game by instantiating a board object
    app.post(
        "/startgame",
        ctx -> {
          char type = ctx.formParam("type").charAt(0);
          Player p1 = new Player(type, 1);
          char[][] boardState = {
            {'\u0000', '\u0000', '\u0000'},
            {'\u0000', '\u0000', '\u0000'},
            {'\u0000', '\u0000', '\u0000'}
          };
          board = new GameBoard(p1, null, false, 1, boardState, 0, false);
          ctx.json(board);
        });

    // Joins a game if there is one. Undefined behavior when joining an existing game with two players.
    app.get(
        "/joingame",
        ctx -> {
          try {
            char p1Type = board.getP1().getType();
            Player p2;
            if (p1Type == 'X') p2 = new Player('O', 2);
            else p2 = new Player('X', 2);
            board.setP2(p2);
            board.setGameStarted(true);
            ctx.redirect("tictactoe.html?p=2");
            sendGameBoardToAllPlayers(gson.toJson(board));
          } catch (NullPointerException e) {
            ctx.result("No game has been started");
          }
        });

    // Handles movement, uses IOExceptions to pinpoint illegal moves
    app.post(
        "/move/:playerId",
        ctx -> {
          String playerId = ctx.pathParam("playerId");
          int x = Integer.parseInt(ctx.formParam("x"));
          int y = Integer.parseInt(ctx.formParam("y"));
          int xPlayer;
          int yPlayer;

          if (board.getP1().getType() == 'X') {
            xPlayer = 1;
            yPlayer = 2;
          } else {
            xPlayer = 2;
            yPlayer = 1;
          }

          boolean moveValidity;
          int code;
          String message;

          try {
            if (!board.isGameStarted()) {
              throw new IOException("Both players must have joined");
            } else if (board.isDraw() || board.getWinner() > 0) {
              throw new IOException("The game is already over");
            }
            char[][] boardState = board.getBoardState();
            char type;
            if (playerId.equals("1")) {
              type = board.getP1().getType();
            } else {
              type = board.getP2().getType();
            }
            if (board.getTurn() == 1 && playerId.equals("2")) {
              throw new IOException("Player 1 did not move first");
            } else if ((board.getTurn() % 2 == 0 && playerId.equals("1"))
                || (board.getTurn() % 2 == 1 && playerId.equals("2"))) {
              throw new IOException("Player cannot make two moves in their turn");
            } else if (boardState[x][y] != '\u0000') {
              throw new IOException("Please make a legal move");
            }
            boardState[x][y] = type;
            board.setBoardState(boardState);
            int status = getBoardStatus(boardState, xPlayer, yPlayer);
            if (status == -1) {
              board.setDraw(true);
            } else if (status > 0) {
              board.setWinner(status);
            }
            moveValidity = true;
            code = 100;
            message = "";
            board.setTurn(board.getTurn() == 1 ? 2 : 1);
            ctx.json(new Message(moveValidity, code, message));
          } catch (IOException e) {
            moveValidity = false;
            code = 200;
            message = e.getMessage();
            ctx.json(new Message(moveValidity, code, message));
          }
          sendGameBoardToAllPlayers(gson.toJson(board));
        });

    // Web sockets - DO NOT DELETE or CHANGE
    app.ws("/gameboard", new UiWebSocket());
  }

  /** Send message to all players.
   * @param gameBoardJson Gameboard JSON
   * @throws IOException Websocket message send IO Exception
   */
  private static void sendGameBoardToAllPlayers(final String gameBoardJson) {
    Queue<Session> sessions = UiWebSocket.getSessions();
    for (Session sessionPlayer : sessions) {
      try {
        sessionPlayer.getRemote().sendString(gameBoardJson);
      } catch (IOException e) {
        // Add logger here
      }
    }
  }

  public static void stop() {
    app.stop();
  }
}
