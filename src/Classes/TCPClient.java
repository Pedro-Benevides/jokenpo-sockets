import java.io.*;
import java.net.*;
import java.util.*;  

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

            boolean start = client.in.readBoolean(); 

            if(start){

            while(true){

            // Conversa entre o Cliente e o Servidor. (inicio)

            Scanner input = new Scanner(System.in);
            System.out.println("PEDRA   [1] : ");
            System.out.println("PAPEL   [2] : ");
            System.out.println("TESOURA [3] : ");
            System.out.println("Escolha a sua jogada? : ");
            int move = input.nextInt();

            if(move == 1)
                client.move = MoveEnum.PEDRA;
            if(move == 2)
                client.move = MoveEnum.PAPEL;
            if(move == 3)
                client.move = MoveEnum.TESOURA;

            client.out.writeUTF(String.valueOf(client.move));
                         
             // Mostra a jogada do oponente
             String opponentMove = client.in.readUTF();                
             System.out.println(opponentMove);

             // Mostra o resultado
            String result = client.in.readUTF();                
            System.out.println(result);

            // Fim de jogo? 
            boolean endGame = client.in.readBoolean();                
            if(endGame) break;

        }
    }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Thread Interrupted:" + e.getMessage());
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
