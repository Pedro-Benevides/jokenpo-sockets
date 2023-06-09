import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient implements Client, Player {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private MoveEnum move;

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
            System.out.println("EOF while connecting server:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO while connecting server:" + e.getMessage());
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

    public MoveEnum getMove() {
        return move;
    }

    public void Move(MoveEnum move) {
        this.move = move;
    }

    private static int checkInput() {
        Scanner input = new Scanner(System.in);

        try {
            int move = input.nextInt();
            
            if(move > 0 && move < 4) return move;
            else throw new Exception();
            
        } catch (Exception e){
            System.out.println("Opção inválida! Digite uma das opções informadas.");
            return checkInput();
        }
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient();

        try {

            boolean start = client.getIn().readBoolean();
            System.out.println("Partida iniciada!");

            while (start) {

                // Conversa entre o Cliente e o Servidor. (inicio)

                System.out.println("PEDRA   [1] : ");
                System.out.println("PAPEL   [2] : ");
                System.out.println("TESOURA [3] : ");
                System.out.println("Escolha a sua jogada? : ");
                int move = checkInput();

                client.Move(MoveEnum.getMove(move));

                client.getOut().writeUTF(String.valueOf(client.getMove()));

                // Mostra a jogada do oponente
                String opponentMove = client.getIn().readUTF();
                System.out.println(opponentMove);

                // Mostra o resultado
                String result = client.getIn().readUTF();
                System.out.println(result);

                // Fim de jogo?
                boolean endMatch = client.getIn().readBoolean();

                if (endMatch) {
                    break;
                }

            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted:" + e.getMessage());
            }
            // Conversa entre o Cliente e o Servidor. (final)

        } catch (IOException e) {
            System.out.println("IO Client:" + e.getMessage());
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
