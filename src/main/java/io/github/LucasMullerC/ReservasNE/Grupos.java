package io.github.LucasMullerC.ReservasNE;

public class Grupos implements Comparable <Grupos>{
	private String UUID,Player1,Player2,Player3,Pendente;
	public Grupos (String G) {
		this.UUID = G;
	}
	public String getUUID () {
		return this.UUID;
	}
	public void setPlayer1 (String P1) {
		this.Player1 = P1;
	}
	public String getPlayer1 () {
		return this.Player1;
	}
	public void setPlayer2 (String P2) {
		this.Player2 = P2;
	}
	public String getPlayer2 () {
		return this.Player2;
	}
	public void setPlayer3 (String P3) {
		this.Player3 = P3;
	}
	public String getPlayer3 () {
		return this.Player3;
	}	
	public void setPendente (String PD) {
		this.Pendente = PD;
	}
	public String getPendente () {
		return this.Pendente;
	}	

	@Override
	public int compareTo(Grupos G) {
		return this.UUID.compareTo(G.getUUID());
	}
	

}
