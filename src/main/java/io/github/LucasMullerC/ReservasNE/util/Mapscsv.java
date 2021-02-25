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

import io.github.LucasMullerC.ReservasNE.DoneAreas;
import io.github.LucasMullerC.ReservasNE.Grupos;

public class Mapscsv {
	private File storageFile;
	private ArrayList<DoneAreas> values;
	private DoneAreas G;

	public Mapscsv(File file) {
		this.storageFile = file;
		this.values = new ArrayList<DoneAreas>();
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
				if (this.Contains(new DoneAreas(line)) == false) {
					if (cont == 0) {
						cont++;
					}
					else {
						String[] Parts = line.split(",");
						G = new DoneAreas(Parts[2]);
						G.setLat(Parts[0]);
						G.setLon(Parts[1]);
						G.setNome(Parts[3]);
						values.add(G);
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
			out.write("lat,lon,title,Construido por,Discord");
			out.newLine();
			for (DoneAreas value : this.values) {
				out.write(value.toString());
				out.newLine();
			}
			out.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean Contains(DoneAreas value) {
		return this.values.contains(value);
	}

	public void add(DoneAreas value) {
		if (this.Contains(value) == false) {
			this.values.add(value);
		}
	}

	public void remove(DoneAreas value) {
		this.values.remove(value);
	}
	public void clear() {
		this.values.clear();
	}

	public ArrayList<DoneAreas> getValues() {
		return this.values;
	}
	
	

}
