package models;

public class Move {

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
