package io.github.LucasMullerC.ReservasNE.Objetos;

public class Grupos implements Comparable<Grupos>
{
    private String UUID;
    private String Player1;
    private String Player2;
    private String Player3;
    private String Pendente;
    
    public Grupos(final String G) {
        this.UUID = G;
    }
    
    public String getUUID() {
        return this.UUID;
    }
    
    public void setPlayer1(final String P1) {
        this.Player1 = P1;
    }
    
    public String getPlayer1() {
        return this.Player1;
    }
    
    public void setPlayer2(final String P2) {
        this.Player2 = P2;
    }
    
    public String getPlayer2() {
        return this.Player2;
    }
    
    public void setPlayer3(final String P3) {
        this.Player3 = P3;
    }
    
    public String getPlayer3() {
        return this.Player3;
    }
    
    public void setPendente(final String PD) {
        this.Pendente = PD;
    }
    
    public String getPendente() {
        return this.Pendente;
    }
    
    @Override
    public int compareTo(final Grupos G) {
        return this.UUID.compareTo(G.getUUID());
    }
}
