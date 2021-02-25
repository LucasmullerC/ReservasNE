package io.github.LucasMullerC.ReservasNE.Objetos;

public class Gerenciador implements Comparable<Gerenciador>
{
    private String UUID;
    private String Regiao;
    private String Discord;
    private int qtdEasy;
    private int qtdMedium;
    private int qtdHard;
    private int qtdPRO;
    private int Rank;
    private boolean Grupo;
    private boolean Cargo;
    
    public Gerenciador(final String U) {
        this.UUID = U;
    }
    
    public String getUUID() {
        return this.UUID;
    }
    
    public void setRegiao(final String R) {
        this.Regiao = R;
    }
    
    public String getRegiao() {
        return this.Regiao;
    }
    
    public void setRank(final int rank) {
        this.Rank = rank;
    }
    
    public int getRank() {
        return this.Rank;
    }
    
    public void setQtdEasy(final int QE) {
        this.qtdEasy = QE;
    }
    
    public int getQtdEasy() {
        return this.qtdEasy;
    }
    
    public void setQtdMedium(final int QM) {
        this.qtdMedium = QM;
    }
    
    public int getQtdMedium() {
        return this.qtdMedium;
    }
    
    public void setQtdHard(final int QH) {
        this.qtdHard = QH;
    }
    
    public int getQtdHard() {
        return this.qtdHard;
    }
    
    public void setQtdPRO(final int QP) {
        this.qtdPRO = QP;
    }
    
    public int getQtdPRO() {
        return this.qtdPRO;
    }
    
    public void setGrupo(final boolean GP) {
        this.Grupo = GP;
    }
    
    public boolean getGrupo() {
        return this.Grupo;
    }
    
    public void setCargo(final boolean CA) {
        this.Cargo = CA;
    }
    
    public boolean getCargo() {
        return this.Cargo;
    }
    
    public void setDiscord(final String Disc) {
        this.Discord = Disc;
    }
    
    public String getDiscord() {
        return this.Discord;
    }
    
    @Override
    public String toString() {
        return "Voce ja completou: " + this.qtdEasy + " Builds Simples | " + this.qtdMedium + " Builds Medianas | " + this.qtdHard + " Builds Complexas.";
    }
    
    @Override
    public int compareTo(final Gerenciador G) {
        return this.UUID.compareTo(G.getUUID());
    }
}
