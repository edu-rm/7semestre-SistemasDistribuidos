package tictactoe;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameClient {

	public static void printTabuleiro(int[][] tabuleiro) throws Exception {
		System.out.println("-------------- Estado do jogo --------------");
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

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost",8100);
			TicTacToeInterface ticTacToe = (TicTacToeInterface) registry.lookup("TicTacToe");
			Scanner in = new Scanner(System.in);
			System.out.println("Enter your name: ");
			String name = in.nextLine();

			int id;
			do {
				System.out.println("Enter your id [1 - 99999]: ");
				id = Integer.parseInt(in.nextLine());
			} while (id < 1);

			//ID pode ser gerado pelo servidor
			Player player = new Player(name, id);

			ReturnMessage msg = ticTacToe.enter(player);
			System.out.println(msg.getMessage());

			if(msg.getCode() == 1){
				int opt = -1;
				do{
					//System.out.println("Enter your move: ");
					//int move = Integer.parseInt(in.nextLine());

					if (ticTacToe.verificarVitoria() != 0) {
						if (ticTacToe.verificarVitoria() != player.getId()) {
							System.out.println("PARABENS, VOCE PERDEU!!!");
							printTabuleiro(ticTacToe.getTabuleiro());
							break;
						}
					}

					if (ticTacToe.hasPermissionToPlay(player)) {
						printTabuleiro(ticTacToe.getTabuleiro());
						String jogada;
						int nJogada = 1;

						do {
							if (nJogada > 1) {
								System.out.println("Por favor, faca uma jogada valida");
							}
							nJogada++;
							
							int row;
							do {
								System.out.println("Enter your row [1 - 3]: ");
								row = Integer.parseInt(in.nextLine());
							} while (row < 1 || row > 3);

							int column;
							do {
								System.out.println("Enter your column [1 - 3]: ");
								column = Integer.parseInt(in.nextLine());
							} while (column < 1 || column > 3);


							jogada = ticTacToe.play(player, row, column);
							printTabuleiro(ticTacToe.getTabuleiro());

							if (ticTacToe.verificarVitoria() != 0) {
								if (ticTacToe.verificarVitoria() == player.getId()) {
									System.out.println("PARABENS, VOCE FOI O VENCEDOR!!!");
									break;
								} else {
									System.out.println("PARABENS, VOCE PERDEU!!!");
									break;
								}

								// Printar k

							}

						} while (jogada.equals("erro"));

					} else {
						System.out.println("Esperando outro jogador...");
						TimeUnit.SECONDS.sleep(3);
					}
				}while (opt != 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}