package io.github.LucasMullerC.ReservasNE.Objetos;

public class Areas implements Comparable<Areas>
{
    private String Nome;
    private String P1x;
    private String P1y;
    private String P2x;
    private String P2y;
    private String P3x;
    private String P3y;
    private String P4x;
    private String P4y;
    private String Dificuldade;
    private String Credito;
    
    public Areas(final String N) {
        this.Nome = N;
    }
    
    public void setNome(final String No) {
        this.Nome = No;
    }
    
    public String getNome() {
        return this.Nome;
    }
    
    public void setP1x(final String Pos1x) {
        this.P1x = Pos1x;
    }
    
    public String getP1x() {
        return this.P1x;
    }
    
    public void setP1y(final String Pos1y) {
        this.P1y = Pos1y;
    }
    
    public String getP1y() {
        return this.P1y;
    }
    
    public void setP2x(final String Pos2x) {
        this.P2x = Pos2x;
    }
    
    public String getP2x() {
        return this.P2x;
    }
    
    public void setP2y(final String Pos2y) {
        this.P2y = Pos2y;
    }
    
    public String getP2y() {
        return this.P2y;
    }
    
    public void setP3x(final String Pos3x) {
        this.P3x = Pos3x;
    }
    
    public String getP3x() {
        return this.P3x;
    }
    
    public void setP3y(final String Pos3y) {
        this.P3y = Pos3y;
    }
    
    public String getP3y() {
        return this.P3y;
    }
    
    public void setP4x(final String Pos4x) {
        this.P4x = Pos4x;
    }
    
    public String getP4x() {
        return this.P4x;
    }
    
    public void setP4y(final String Pos4y) {
        this.P4y = Pos4y;
    }
    
    public String getP4y() {
        return this.P4y;
    }
    
    public void setDificuldade(final String D) {
        this.Dificuldade = D;
    }
    
    public String getDificuldade() {
        return this.Dificuldade;
    }
    
    public void setCredito(final String C) {
        this.Credito = C;
    }
    
    public String getCredito() {
        return this.Credito;
    }
    
    @Override
    public String toString() {
        return " por: " + this.Credito;
    }
    
    @Override
    public int compareTo(final Areas R) {
        return this.Nome.compareTo(R.getNome());
    }
}
