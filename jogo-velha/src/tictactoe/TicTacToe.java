package tictactoe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//Server
public class TicTacToe extends UnicastRemoteObject implements TicTacToeInterface {
	private int numOfPlayers;
	private Player player1;
	private Player player2;
	private int permission = 1;
	private int[][] tabuleiro;
	private int ganhador = 0;

	protected TicTacToe() throws RemoteException {
		super();

		this.numOfPlayers = 0;
		this.tabuleiro = new int[3][3];
	}

	public int getGanhador() throws Exception {
		return this.ganhador;
	}


	@Override
	public ReturnMessage enter(Player player) throws Exception {
		ReturnMessage returnMessage = new ReturnMessage();

		if(this.numOfPlayers < 2){
			returnMessage.setCode(1);
			returnMessage.setMessage("Player " + player.getName() + " has entered the game");
			if (numOfPlayers == 0) {
				this.player1 = player;
			} else {
				this.player2 = player;
			}
			numOfPlayers++;
			return returnMessage;
		}else {
			returnMessage.setCode(3);
			returnMessage.setMessage("Game is full");
			return returnMessage;
		}
	}  

	public int verificarVitoria() throws Exception{
		for(int l=0 ; l<3 ;l++){
			int c1 = tabuleiro[l][0];
			int c2 = tabuleiro[l][1];
			int c3 = tabuleiro[l][2];

			if((c1 != 0) &&  (c1 == c2) && (c2 == c3)){
				return c1;
			}
		}

		for(int c = 0 ; c < 3 ;c++){
			int c1 = tabuleiro[0][c];
			int c2 = tabuleiro[1][c];
			int c3 = tabuleiro[2][c];
			if((c1 != 0) && (c1 == c2) && (c2 == c3)){

				return c1;

			}
		}

		int c1 = tabuleiro[0][0];
		int c2 = tabuleiro[1][1];
		int c3 = tabuleiro[2][2];

		if((c1 != 0) && (c1 == c2) && (c2 == c3)){

			return c1;

		}

		c1 = tabuleiro[0][2];
		c2 = tabuleiro[1][1];
		c3 = tabuleiro[2][0];

		if((c1 != 0) && (c1 == c2) && (c2 == c3)){

			return c1;

		}

		return 0;
	}

	@Override
	public String play(Player player, int row, int column) throws Exception {
		System.out.println("Jogador " + player.getId() + " jogou na linha: " + row + ", coluna: " + column);


		if (this.tabuleiro[row - 1][column - 1] == 0) {
			this.tabuleiro[row - 1][column - 1] = player.getId();

			// MUDANDO A PERMISSÃO DE ACORDO COM QUEM ACABOU DE JOGAR
			if (player.getId() == player1.getId()) {
				this.permission = 2;
			}

			if (player.getId() == player2.getId()) {
				this.permission = 1;
			}
			return "sucesso";
		} else {
			return "erro";
		}
		
		
	}



	@Override
	public boolean hasPermissionToPlay(Player player) throws Exception {
		if (player1 != null && player2 != null) {
			if (player.getId() == player1.getId() && this.permission == 1) {
				return true;
			}
			if (player.getId() == player2.getId() && this.permission == 2) {
				return true;
			}

			return false;
		}

		return false;
	}

	public void printTabuleiro() throws Exception {
		for(int l = 0 ;l < 3 ;l++){
			for(int c=0 ;c<3 ;c++){
				if(tabuleiro[l][c] != 0){
					System.out.print("["+Integer.toString(tabuleiro[l][c])+ "]");
				} else {
					System.out.print("[ ]");
				}
			}
			System.out.println();
		}
	}

	public int[][] getTabuleiro() throws Exception {
		return this.tabuleiro;
	}

}