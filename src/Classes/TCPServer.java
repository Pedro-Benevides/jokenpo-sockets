import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer implements Server, Playable {

    public int port;
    public ServerSocket socket;
    public ArrayList<Match> matches = new ArrayList<Match>();
    public ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private boolean serverRunning;

    public TCPServer() {
        this.StartServer();
    }

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
            System.out.println("Server running on port:" + this.port);
            this.ListenClientConnect();
        } catch (IOException e) {
            System.out.println("Server startup failed:" + e.getMessage());
        }
    }

    @Override
    public void CloseServer() {
        this.setServerRunning(false);
    }

    @Override
    public void ListenClientConnect() {

        while (this.isServerRunning()) {
            try {
                // Servidor aguarda a conexao de algum Cliente.
                Socket clientSocket = this.socket.accept();

                // Criacao da Thread e conexao desta ao socket do Cliente.
                this.connections.add(new TCPConnection(clientSocket));
                System.out.println("Connection Successful!");

                // aguarda conexao de dois clientes para iniciar partida
                if (this.connections.size() == 2) {
                    this.StartMatch();
                }

            } catch (IOException e) {
                System.out.println("Connection Failed:" + e.getMessage());
            }
        }

    }

    @Override
    public void StartMatch() {
        System.out.println("Jogadores conectados, iniciando partida");
        System.out.println("Loading...");

        List<TCPConnection> players = this.connections.subList(0, 2);
        TCPConnection firstPlayerConnection = players.get(0);
        TCPConnection secondPlayerConnection = players.get(1);

        this.connections.remove(firstPlayerConnection);
        this.connections.remove(secondPlayerConnection);

        this.matches.add(new Match(firstPlayerConnection, secondPlayerConnection));
    }

    @Override
    public void CloseMatch() {
        // TODO: finalizar a partida caso alguns dos clients encerre a conexao
    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        server.StartServer();
    }

}
