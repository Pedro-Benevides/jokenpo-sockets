import java.io.EOFException;
import java.io.IOException;

public class Match extends Thread {
    private TCPConnection firstPlayer;
    private TCPConnection secondPlayer;
    private TCPConnection winner;

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

    public TCPConnection getWinner() {
        return winner;
    }

    public void setWinner(TCPConnection winner) {
        this.winner = winner;
    }

    private void calcWinner(String jogada1, String jogada2) {
        String pedra = String.valueOf(MoveEnum.PEDRA);
        String tesoura = String.valueOf(MoveEnum.TESOURA);
        String papel = String.valueOf(MoveEnum.PAPEL);

        if (jogada1 == pedra && jogada2 == tesoura) {
            this.setWinner(firstPlayer);
        } else if (jogada1 == tesoura && jogada2 == papel) {
            this.setWinner(firstPlayer);
        } else if (jogada1 == papel && jogada2 == pedra) {
            this.setWinner(firstPlayer);
        } else {
            this.setWinner(secondPlayer);
        }

    }

    @Override
    public void run() {
        boolean starMatch = true;
        boolean endMatch = false;
        try {
            while (!endMatch) {
                // envio de mensagem de inicio de partida
                this.getFirstPlayer().getOut().writeBoolean(starMatch);
                this.getSecondPlayer().getOut().writeBoolean(starMatch);

                // Recebimento de jogadas
                String jogada1 = this.getFirstPlayer().getIn().readUTF();
                String jogada2 = this.getSecondPlayer().getIn().readUTF();

                // Verifica resultado
                if (jogada1.equals(jogada2)) {
                    this.getFirstPlayer().getOut().writeUTF("Empate! , joguem novamente!");
                    this.getSecondPlayer().getOut().writeUTF("Empate! , joguem novamente!");
                } else {
                    // Manda jogadas pro cliente
                    this.getFirstPlayer().getOut().writeUTF("A jogada do adversário foi " + MoveEnum.valueOf(jogada2));
                    this.getSecondPlayer().getOut().writeUTF("A jogada do adversário foi " + MoveEnum.valueOf(jogada1));

                    this.calcWinner(jogada1, jogada2);

                    // informa o vencedor
                    if (this.winner.equals(this.getFirstPlayer())) {
                        this.getFirstPlayer().getOut().writeUTF("Vencedor!");
                        this.getSecondPlayer().getOut().writeUTF("Perdeu!");

                    } else {
                        this.getSecondPlayer().getOut().writeUTF("Vencedor!");
                        this.getFirstPlayer().getOut().writeUTF("Perdeu!");
                    }
                    endMatch = true;

                }

                if (endMatch) {
                    // Manda confirmação se o jogo acabou
                    firstPlayer.getOut().writeBoolean(endMatch);
                    secondPlayer.getOut().writeBoolean(endMatch);
                    break;
                }

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
