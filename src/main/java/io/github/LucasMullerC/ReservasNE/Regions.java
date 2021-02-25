package io.github.LucasMullerC.ReservasNE;

import org.bukkit.Location;

public class Regions implements Comparable <Regions>{
	private String Nome;
	private boolean Estado;
	private int Dificuldade;
	private Location pos;
	
	public Regions (String N) {
		this.Nome = N;
	}
	public String getNome () {
		return this.Nome;
	}
	public void setEstado (boolean E) {
		this.Estado = E;
	}
	public boolean getEstado () {
		return this.Estado;
	}
	public void setDificuldade (int D) {
		this.Dificuldade = D;
	}
	public int getDificuldade () {
		return this.Dificuldade;
	}
	public void setPos (Location P) {
		this.pos = P;
	}
	public Location getPos () {
		return this.pos;
	}
 	@Override
    public String toString () {
        return "ID: " +this.Nome+ " Estado: " +this.Estado+ " Dificuldade: " +this.Dificuldade;
    }
	@Override
	public int compareTo(Regions R) {
		return this.Nome.compareTo(R.getNome());
	}

}
