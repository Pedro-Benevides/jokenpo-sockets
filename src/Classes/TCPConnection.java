import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * TCPConnection
 */
public class TCPConnection {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;

    // Conexao da Thread-Servidora com o socket do Cliente
    public TCPConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.in = new DataInputStream(clientSocket.getInputStream());
            this.out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}