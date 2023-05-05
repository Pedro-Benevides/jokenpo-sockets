public enum MoveEnum {
    PEDRA(1), PAPEL(2), TESOURA(3);

    private final int move;

    MoveEnum(int moveValue) {
        move = moveValue;
    }

    public int getMove() {
        return move;
    }
}
