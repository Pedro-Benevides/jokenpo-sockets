public class Match {
    private TCPClient firstPlayer;
    private TCPClient secondPlayer;

    public Match(TCPClient firstPlayer, TCPClient secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public TCPClient getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(TCPClient firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public TCPClient getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(TCPClient secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
