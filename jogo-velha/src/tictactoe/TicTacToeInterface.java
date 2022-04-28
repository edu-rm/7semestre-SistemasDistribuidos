package tictactoe;

import java.rmi.Remote;

//Server
public interface TicTacToeInterface extends Remote {

    public ReturnMessage enter(Player player) throws Exception;
    String play(Player player, int row, int column) throws Exception;
	boolean hasPermissionToPlay(Player player) throws Exception;
	public void printTabuleiro() throws Exception;
	public int[][] getTabuleiro() throws Exception;
	public int getGanhador() throws Exception;
	public int verificarVitoria() throws Exception;
}