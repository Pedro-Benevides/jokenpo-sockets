import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Match {
    private TCPConnection firstPlayer;
    private TCPConnection secondPlayer;

    public Match(TCPConnection firstPlayer, TCPConnection secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public TCPConnection getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(TCPConnection firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public TCPConnection getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(TCPConnection secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public void calcWinner(){
        try {
            boolean endGame = false;
            while (!endGame) {
                
                // Recebe jogadas
                String jogada1 = firstPlayer.getIn().readUTF();
                String jogada2 = secondPlayer.getIn().readUTF();

                // Manda jogadas pro cliente
                firstPlayer.getOut().writeUTF("A jogada do adversário foi " + jogada2);
                secondPlayer.getOut().writeUTF("A jogada do adversário foi " + jogada1);

                // Verifica resultado
                if(jogada1.equals(jogada2)){
                    firstPlayer.getOut().writeUTF("Empate! , joguem novamente!");
                    secondPlayer.getOut().writeUTF("Empate! , joguem novamente!");
                }

                else {
                    firstPlayer.getOut().writeUTF("O vencedor foi");
                    secondPlayer.getOut().writeUTF("O vencedor foi");
                    endGame = true;
                }

                // Manda confirmação se o jogo acabou
                firstPlayer.getOut().writeBoolean(endGame);
                secondPlayer.getOut().writeBoolean(endGame);

            }
            // Conversa entre a Thread-Servidor com o Cliente. (final)

            // Tratamento de Excecoes. (inicio)
        } catch (EOFException e) {
            System.out.println("EOF: Conexao Encerrada");
            System.out.println("Match");
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
            System.out.println("Match");

        } finally {
            try {
                firstPlayer.getClientSocket().close();
                secondPlayer.getClientSocket().close();
            } catch (IOException e) {
                System.out.println("Match");
                System.out.println("IO:" + e.getMessage());
            }
        }
        // Tratamento de Excecoes. (final)
    }        
}

