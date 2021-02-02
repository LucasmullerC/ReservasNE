package io.github.LucasMullerC.ReservasNE;

public class DoneAreas implements Comparable <DoneAreas>{
	private String area,nome,lat,lon;
	
	public DoneAreas (String A) {
		this.area = A;
	}
	public String getArea () {
		return this.area;
	}
	public void setNome (String N) {
		this.nome = N;
	}
	public String getNome () {
		return this.nome;
	}
	public void setLat (String LA) {
		this.lat = LA;
	}
	public String getLat () {
		return this.lat;
	}
	public void setLon (String LO) {
		this.lon = LO;
	}
	public String getLon () {
		return this.lon;
	}
 	@Override
    public String toString () {
        return this.lat+ "," +this.lon+ ","+this.area+","+this.nome;
    }
	@Override
	public int compareTo(DoneAreas D) {
		return this.area.compareTo(D.getArea());
	}
	

}
