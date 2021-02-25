package io.github.LucasMullerC.ReservasNE.Objetos;

public class DoneAreas extends Regions
{
    private String player;
    
    public DoneAreas(final String A) {
        super(A);
    }
    
    public void setPlayer(final String P) {
        this.player = P;
    }
    
    public String getPlayer() {
        return this.player;
    }
    
    public String toString() {
        return this.player;
    }
}
