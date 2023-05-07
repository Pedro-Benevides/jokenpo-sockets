import java.io.EOFException;
import java.io.IOException;

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

    private int calcWinner(String jogada1, String jogada2) {
        String pedra = String.valueOf(MoveEnum.PEDRA);
        String tesoura = String.valueOf(MoveEnum.TESOURA);
        String papel = String.valueOf(MoveEnum.PAPEL);

        if(jogada1 == pedra && jogada2 == tesoura) return 1;
        if(jogada1 == tesoura && jogada2 == papel) return 1;
        if(jogada1 == papel && jogada2 == pedra) return 1;
        
        return 2;
    }

    public void runMatch(){
        
        boolean starMatch = true;

        try {

            firstPlayer.getOut().writeBoolean(starMatch);
            secondPlayer.getOut().writeBoolean(starMatch);
            
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
                    int winner = calcWinner(jogada1, jogada2);
                    String player1Return = winner == 1 ? "Você ganhou!" : "Você perdeu =/";
                    String player2Return = winner == 2 ? "Você ganhou!" : "Você perdeu =/";

                    firstPlayer.getOut().writeUTF(player1Return);
                    secondPlayer.getOut().writeUTF(player2Return);

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

