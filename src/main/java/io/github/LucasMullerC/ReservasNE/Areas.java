package io.github.LucasMullerC.ReservasNE;

public class Areas implements Comparable <Areas>{
	private String Nome,P1x,P1y,P2x,P2y,P3x,P3y,P4x,P4y;
	private String Dificuldade,Credito;
	public Areas (String N) {
		this.Nome = N;		
	}
	public void setNome (String No) {
		this.Nome = No;
	}
	public String getNome () {
		return this.Nome;
	}
	public void setP1x (String Pos1x) {
		this.P1x = Pos1x;
	}
	public String getP1x () {
		return this.P1x;
	}
	public void setP1y (String Pos1y) {
		this.P1y = Pos1y;
	}
	public String getP1y () {
		return this.P1y;
	}
	public void setP2x (String Pos2x) {
		this.P2x = Pos2x;
	}
	public String getP2x () {
		return this.P2x;
	}
	public void setP2y (String Pos2y) {
		this.P2y = Pos2y;
	}
	public String getP2y () {
		return this.P2y;
	}
	public void setP3x (String Pos3x) {
		this.P3x = Pos3x;
	}
	public String getP3x () {
		return this.P3x;
	}
	public void setP3y (String Pos3y) {
		this.P3y = Pos3y;
	}
	public String getP3y () {
		return this.P3y;
	}
	public void setP4x (String Pos4x) {
		this.P4x = Pos4x;
	}
	public String getP4x () {
		return this.P4x;
	}
	public void setP4y (String Pos4y) {
		this.P4y = Pos4y;
	}
	public String getP4y () {
		return this.P4y;
	}
	public void setDificuldade (String D) {
		this.Dificuldade = D;
	}
	public String getDificuldade () {
		return this.Dificuldade;
	}
	public void setCredito (String C) {
		this.Credito = C;
	}
	public String getCredito () {
		return this.Credito;
	}
	@Override
    public String toString () {
        return " por: " +this.Credito;
    }

	@Override
	public int compareTo(Areas R) {
		return this.Nome.compareTo(R.getNome());
	}
	

}
