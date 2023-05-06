import java.io.*;
import java.net.*;

public class TCPClient implements Client, Player {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    public MoveEnum move;

    public TCPClient() {
        this.ConnectServer();
    }

    public void ConnectServer() {
        // Criacao do socket do Cliente, acessando o endereco/porta do Servidor e
        // criacao dos fluxos de I/O. (inicio)
        try {
            int serverPort = 8000;
            socket = new Socket("127.0.0.1", serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            // Criacao do socket do Cliente, acessando o endereco/porta do Servidor e
            // criacao dos fluxos de I/O. (final)

            // Tratamento de Excecoes. (inicio)
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        }
        // Tratamento de Excecoes. (final)
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }

    public void Move(MoveEnum move) {
        this.move = move;
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient();

        try {
            // Conversa entre o Cliente e o Servidor. (inicio)
            int i = 0;
            for (int j = 0; j < 10; ++j) {
                client.getOut().writeUTF("Pedido de Servico " + ++i);
                String data = client.getIn().readUTF();
                System.out.println("Recebido a " + data);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Thread Interrupted:" + e.getMessage());
                }
            }
            // Conversa entre o Cliente e o Servidor. (final)

        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if (client.getSocket() != null)
                try {
                    client.getSocket().close();
                } catch (IOException e) {
                    System.out.println("IO:" + e.getMessage());
                }
        }
    }
}
