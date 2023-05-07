enum MoveEnum {
    PEDRA,
    PAPEL,
    TESOURA;

    public static MoveEnum getMove(int moveValue) {
        MoveEnum move = null;
        switch (moveValue) {
            case 1:
                move = MoveEnum.PEDRA;
                break;
            case 2:
                move = MoveEnum.PAPEL;
                break;
            case 3:
                move = MoveEnum.TESOURA;
                break;
        }

        return move;
    }
}
