public enum JogadaEnum {
    PEDRA(1), PAPEL(2), TESOURA(3);
    
    private final int jogada;
    
    JogadaEnum(int valorJogada){
        jogada = valorJogada;
    }

    public int getJogada() {
        return jogada;
    }
}
