import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Server, Playable {

    public int port;
    public ServerSocket socket;
    public ArrayList<Match> partidas;
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
    public void StartMatch() {
        // TODO: ajustar para aguardar conexao de dois clientes para iniciar partida

        while (this.isServerRunning()) {
            try {
                // Servidor aguarda a conexao de algum Cliente.
                Socket clientSocket = this.socket.accept();

                // Criacao da Thread e conexao desta ao socket do Cliente.
                TCPConnection c = new TCPConnection(clientSocket);
                System.out.println("Conectou!");
            } catch (IOException e) {
                System.out.println("Connection Failed:" + e.getMessage());
            }
        }

    }

    @Override
    public void CloseMatch() {
        // TODO: finalizar a partida caso alguns dos clients encerre a conexao
    }

}
