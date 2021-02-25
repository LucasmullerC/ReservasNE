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

import io.github.LucasMullerC.ReservasNE.Gerenciador;
import io.github.LucasMullerC.ReservasNE.Grupos;

public class GrupoStore {
	private File storageFile;
	private ArrayList<Grupos> values;
	private Grupos G;

	public GrupoStore(File file) {
		this.storageFile = file;
		this.values = new ArrayList<Grupos>();
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
				if (this.Contains(new Grupos(line)) == false) {
					switch (cont) {
					case 0:
						G = new Grupos(line);
						cont++;
						break;
					case 1:
						G.setPlayer1(line);
						cont++;
						break;
					case 2:
						G.setPlayer2(line);
						cont++;
						break;
					case 3:
						G.setPlayer3(line);
						cont++;
						break;
					case 4:
						G.setPendente(line);
						cont++;
						values.add(G);
						cont = 0;
						break;
					default:
						break;
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
			for (Grupos value : this.values) {
				out.write(value.getUUID());
				out.newLine();
				out.write(value.getPlayer1());
				out.newLine();
				out.write(value.getPlayer2());
				out.newLine();
				out.write(value.getPlayer3());
				out.newLine();
				out.write(value.getPendente());
				out.newLine();
			}
			out.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean Contains(Grupos value) {
		return this.values.contains(value);
	}

	public void add(Grupos value) {
		if (this.Contains(value) == false) {
			this.values.add(value);
		}
	}

	public void remove(String value) {
		this.values.remove(value);
	}

	public ArrayList<Grupos> getValues() {
		return this.values;
	}
	
	

}
