package io.github.LucasMullerC.ReservasNE.Objetos;

import org.bukkit.Location;

public class Regions implements Comparable<Regions>
{
    private String Nome;
    private boolean Estado;
    private int Dificuldade;
    private Location pos;
    
    public Regions(final String N) {
        this.Nome = N;
    }
    
    public String getNome() {
        return this.Nome;
    }
    
    public void setEstado(final boolean E) {
        this.Estado = E;
    }
    
    public boolean getEstado() {
        return this.Estado;
    }
    
    public void setDificuldade(final int D) {
        this.Dificuldade = D;
    }
    
    public int getDificuldade() {
        return this.Dificuldade;
    }
    
    public void setPos(final Location P) {
        this.pos = P;
    }
    
    public Location getPos() {
        return this.pos;
    }
    
    @Override
    public String toString() {
        return "ID: " + this.Nome + " Estado: " + this.Estado + " Dificuldade: " + this.Dificuldade;
    }
    
    @Override
    public int compareTo(final Regions R) {
        return this.Nome.compareTo(R.getNome());
    }
}
