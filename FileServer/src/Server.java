import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private static DataOutputStream saida = null;
	private static DataInputStream entrada = null;

	public static void main(String[] args) {



		try (ServerSocket socket = new ServerSocket(8000)) {
			System.out.println("Aguardando por conexoes em: " +
					socket.getInetAddress() + ":" + socket.getLocalPort());


			//			while (true) {
			while (true) {

				try (Socket clientSocket = socket.accept()) {
					// Estabelecendo fluxos de entrada e saída
					entrada = new DataInputStream(clientSocket.getInputStream());
					BufferedReader entradaBuffered = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

					saida = new DataOutputStream(clientSocket.getOutputStream());

					// lendo mensagem enviada pelo cliente
					String nomeArquivo = entradaBuffered.readLine();
					System.out.println("Nome arquivo: "+ nomeArquivo);

					FileOutputStream fileInputStream = new FileOutputStream(nomeArquivo.replace("\n", "").replace("\r", ""));
					BufferedOutputStream bos = new BufferedOutputStream(fileInputStream);
										
					byte[] buffer = new byte[9696];
					
					InputStream is = clientSocket.getInputStream();
					int bytesLidos = is.read(buffer, 0, buffer.length);

					bos.write(buffer, 0, bytesLidos);
					bos.close();
					saida.writeBytes("end");
					
					clientSocket.close();

					// enviando mensagem para o cliente

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
			//			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}

	}





	//		ServerSocket server = new ServerSocket(3322);
	//		
	//		while (true) {
	//			Socket cliente = server.accept();
	//			
	//		}

	//		File arq = new File("arquivo.txt");
	//		
	//		Scanner arqMemoria = new Scanner(arq);
	//		
	//		while (arqMemoria.hasNextLine()) {
	//			System.out.println(arqMemoria.nextLine());
	//		}	}
}
