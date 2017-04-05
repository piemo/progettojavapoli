package online.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server che gestisce le partite via socket.
 * 
 */
public class ServerSocketSheep {
	public static final int PORT = 12345;
	private static final long TIME_OUT = 10000;
	private ArrayList<GestorePartitaSocket> listaPartite;
	private int currentSessionID;

	public ServerSocketSheep() {
		listaPartite = new ArrayList<GestorePartitaSocket>();
		currentSessionID = 0;
	}

	/**
	 * Metodo per far partire il server e metterlo in attesa dei giocatori per
	 * creare nuove partite.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public void startServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server ready on port " + PORT);
		ExecutorService executorService = Executors.newCachedThreadPool();
		while (true) {
			try {
				ArrayList<Socket> listaSocketTmp = new ArrayList<Socket>();
				int numGiocatori = 0;
				serverSocket.setSoTimeout(0);
				while (numGiocatori < 2) {
					Socket socket = serverSocket.accept();
					socket.setTcpNoDelay(true);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream()));
					int sessionID = in.read();
					if (sessionID < 0 || sessionID >= currentSessionID) {
						System.out.println("Giocatore " + numGiocatori);
						out.write(currentSessionID);
						out.flush();
						in.read();
						out.write(numGiocatori);
						out.flush();
						listaSocketTmp.add(socket);
						numGiocatori++;
					} else {
						out.write(sessionID);
						out.flush();
						listaPartite.get(sessionID).riconnetti(socket);
					}
				}
				long beforeTime = System.currentTimeMillis();
				boolean attesa = true;
				while (numGiocatori < 4 && attesa) {
					long currentTime = System.currentTimeMillis();
					if ((TIME_OUT - (currentTime - beforeTime)) > 0) {
						try {
							serverSocket
									.setSoTimeout((int) (TIME_OUT - (currentTime - beforeTime)));
							Socket socket = serverSocket.accept();
							socket.setTcpNoDelay(true);
							BufferedReader in = new BufferedReader(
									new InputStreamReader(
											socket.getInputStream()));
							BufferedWriter out = new BufferedWriter(
									new OutputStreamWriter(
											socket.getOutputStream()));
							int sessionID = in.read();
							if (sessionID < 0 || sessionID >= currentSessionID) {
								System.out.println("Giocatore " + numGiocatori);
								out.write(currentSessionID);
								out.flush();
								in.read();
								out.write(numGiocatori);
								out.flush();
								listaSocketTmp.add(socket);
								numGiocatori++;
							} else {
								out.write(sessionID);
								out.flush();
								listaPartite.get(sessionID).riconnetti(socket);
							}
						} catch (SocketTimeoutException ste) {
							attesa = false;
						}
					} else
						attesa = false;
				}

				Socket[] sockets = new Socket[listaSocketTmp.size()];
				for (int i = 0; i < sockets.length; i++)
					sockets[i] = listaSocketTmp.get(i);
				GestorePartitaSocket gestorePartitaSocket = new GestorePartitaSocket(
						sockets);
				listaPartite.add(gestorePartitaSocket);
				executorService.submit(gestorePartitaSocket);
				currentSessionID++;
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}

	}

	/**
	 * Metodo main per fr partire il server.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		ServerSocketSheep server = new ServerSocketSheep();
		try {
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
