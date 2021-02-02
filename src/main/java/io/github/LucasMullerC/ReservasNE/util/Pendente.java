package io.github.LucasMullerC.ReservasNE.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.github.LucasMullerC.ReservasNE.Areas;

public class Pendente {
	private File storageFile;
	private ArrayList<Areas> values;
	private Areas A;

	public Pendente(File file) {
		this.storageFile = file;
		this.values = new ArrayList<Areas>();
		if (this.storageFile.exists() == false) {
			try {
				this.storageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void load() {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line;
			int cont = 0;
			while ((line = reader.readLine()) != null) {
				if (this.Contains(new Areas(line)) == false) {
					if (line.charAt(0) == '<' && line.charAt(1) == 'n') {
						String[] nome = line.split(">");
						nome = nome[1].split("<");
						A = new Areas(nome [0]);
					}
					else if (line.charAt(0) == '<' && line.charAt(1) == 'd'){
						String[] dif = line.split(">");
						dif = dif[1].split("<");
						A.setDificuldade(dif[0]);	
					}
					else if (line.charAt(0) == '-'){
						String P1X,P1Y,P2X,P2Y,P3X,P3Y,P4X,P4Y,aux = null;
						String[] coord = line.split(",");
						for (int k=0;k<9;k++) {
							if (coord[k].charAt(0) == '0') {
								StringBuilder sb = new StringBuilder(coord[k]);
								aux = sb.deleteCharAt(0).toString();
								
							}
							switch (k) {
							case 0:
								A.setP1y(coord[k]);
								break;
							case 1:
								A.setP1x(coord[k]);
								break;
							case 2:
								A.setP2y(aux);
								break;
							case 3:
								A.setP2x(coord[k]);
								break;
							case 4:
								A.setP3y(aux);
								break;
							case 5:
								A.setP3x(coord[k]);
								break;
							case 6:
								A.setP4y(aux);
								break;
							case 7:
								A.setP4x(coord[k]);
								values.add(A);
								break;
							default:
								break;
							}
						}
					}
				}
			}
			reader.close();
			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			FileWriter stream = new FileWriter(this.storageFile);
			BufferedWriter out = new BufferedWriter(stream);
			for (Areas value : this.values) {
				out.write("<Placemark>");
				out.newLine();	
				out.write("<name>"+value.getNome()+"</name>");
				out.newLine();
				out.write("<description>"+value.getDificuldade()+"</description>");
				out.newLine();
				out.write("<styleUrl>#m_ylw-pushpin</styleUrl>");
				out.newLine();
				out.write("<Polygon>");
				out.newLine();
				out.write("<tessellate>1</tessellate>");
				out.newLine();
				out.write("<outerBoundaryIs>");
				out.newLine();
				out.write("<LinearRing>");
				out.newLine();
				out.write("<coordinates>");
				out.newLine();
				out.write(value.getP1y()+","+value.getP1x()+",0 "+value.getP2y()+","+value.getP2x()+",0 "+value.getP3y()+","+value.getP3x()+",0 "+value.getP4y()+","+value.getP4x()+",0 "+value.getP1y()+","+value.getP1x()+",0");
				out.newLine();
				out.write("</coordinates>");
				out.newLine();
				out.write("</LinearRing>");
				out.newLine();
				out.write("</outerBoundaryIs>");
				out.newLine();
				out.write("</Polygon>");
				out.newLine();
				out.write("</Placemark>");
				out.newLine();
			}
			out.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean Contains(Areas value) {
		return this.values.contains(value);
	}

	public void add(Areas value) {
		if (this.Contains(value) == false) {
			this.values.add(value);
		}
	}

	public void remove(Areas value) {
		this.values.remove(value);
	}
	public void clear() {
		this.values.clear();
	}

	public ArrayList<Areas> getValues() {
		return this.values;
	}
	
	
}
