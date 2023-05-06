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
}
