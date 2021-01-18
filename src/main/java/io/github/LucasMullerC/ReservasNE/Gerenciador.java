package io.github.LucasMullerC.ReservasNE;

public class Gerenciador implements Comparable <Gerenciador> {
	private String UUID,Regiao;
	private int qtdEasy,qtdMedium,qtdHard,qtdPRO,Rank;
	private boolean Grupo;
	
	public Gerenciador (String U) {
		this.UUID = U;
	}
	public String getUUID () {
		return this.UUID;
	}
	public void setRegiao (String R) {
		this.Regiao = R;
	}
	public String getRegiao () {
		return this.Regiao;
	}
	public void setRank (int rank) {
		this.Rank = rank;
	}
	public int getRank () {
		return this.Rank;
	}
	public void setQtdEasy (int QE) {
		this.qtdEasy = QE;
	}
	public int getQtdEasy () {
		return this.qtdEasy;
	}
	public void setQtdMedium (int QM) {
		this.qtdMedium = QM;
	}
	public int getQtdMedium () {
		return this.qtdMedium;
	}
	public void setQtdHard (int QH) {
		this.qtdHard = QH;
	}
	public int getQtdHard () {
		return this.qtdHard;
	}
	public void setQtdPRO (int QP) {
		this.qtdPRO = QP;
	}
	public int getQtdPRO () {
		return this.qtdPRO;
	}
	public void setGrupo (boolean GP) {
		this.Grupo = GP;
	}
	public boolean getGrupo() {
		return this.Grupo;
	}
	@Override
    public String toString () {
        return "Você já completou: " +this.qtdEasy+ " Builds Simples | " +this.qtdMedium+ " Builds Medianas | " +this.qtdHard+ " Builds Complexas.";
    }
	@Override
	public int compareTo(Gerenciador G) {
		 return this.UUID.compareTo(G.getUUID());
	}
	

}
