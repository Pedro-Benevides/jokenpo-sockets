import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Server, Jogavel {

    public int port;
    public ServerSocket socket;
    public ArrayList<Partida> partidas;
    public ArrayList<TCPClient> clients;
    private boolean serverRunning;

    public boolean isServerRunning() {
        return serverRunning;
    }

    public void setServerRunning(boolean serverRunning) {
        this.serverRunning = serverRunning;
    }

    @Override
    public void StartServer() {
        this.port = 8000;

        try {
            this.socket = new ServerSocket(this.port);
            this.setServerRunning(true);
        } catch (IOException e) {
            System.out.println("Listen:" + e.getMessage());
        }
    }

    @Override
    public void CloseServer() {
        this.setServerRunning(false);
    }

    @Override
    public void StartPartida() {
        // TODO: ajustar para aguardar conexao de dois clientes para iniciar partida

        while (this.isServerRunning()) {
            // Servidor aguarda a conexao de algum Cliente.
            Socket clientSocket = this.socket.accept();

            // Criacao da Thread e conexao desta ao socket do Cliente.
            ConnectionTCP c = new ConnectionTCP(clientSocket);
            System.out.println("Conectou!");
        }

    }

    @Override
    public void ClosePartida() {
        // TODO: finalizar a partida caso alguns dos clients encerre a conexao
    }

}
