package io.github.LucasMullerC.ReservasNE.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bukkit.Location;

import io.github.LucasMullerC.ReservasNE.Gerenciador;
import io.github.LucasMullerC.ReservasNE.ReservasNE;

public class PlayerStore {
	private File storageFile;
	private ArrayList<Gerenciador> values;
	private Gerenciador G;

	public PlayerStore(File file) {
		this.storageFile = file;
		this.values = new ArrayList<Gerenciador>();
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
				if (this.Contains(new Gerenciador(line)) == false) {
					switch (cont) {
					case 0:
						G = new Gerenciador(line);
						cont++;
						break;
					case 1:
						G.setRegiao(line);
						cont++;
						break;
					case 2:
						G.setQtdEasy(Integer.parseInt(line));
						cont++;
						break;
					case 3:
						G.setQtdMedium(Integer.parseInt(line));
						cont++;
						break;
					case 4:
						G.setQtdHard(Integer.parseInt(line));
						cont++;
						break;
					case 5:
						G.setQtdPRO(Integer.parseInt(line));
						cont++;
						break;
					case 6:
						G.setRank(Integer.parseInt(line));
						cont++;
						break;
					case 7:
						G.setGrupo(Boolean.parseBoolean(line));
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
			for (Gerenciador value : this.values) {
				out.write(value.getUUID());
				out.newLine();
				out.write(value.getRegiao());
				out.newLine();
				out.write(String.valueOf(value.getQtdEasy()));
				out.newLine();
				out.write(String.valueOf(value.getQtdMedium()));
				out.newLine();
				out.write(String.valueOf(value.getQtdHard()));
				out.newLine();
				out.write(String.valueOf(value.getQtdPRO()));
				out.newLine();
				out.write(String.valueOf(value.getRank()));
				out.newLine();
				Boolean GP = value.getGrupo();
				out.write(GP.toString());
				out.newLine();
			}
			out.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean Contains(Gerenciador value) {
		return this.values.contains(value);
	}

	public void add(Gerenciador value) {
		if (this.Contains(value) == false) {
			this.values.add(value);
		}
	}

	public void remove(Gerenciador value) {
		this.values.remove(value);
	}

	public ArrayList<Gerenciador> getValues() {
		return this.values;
	}

}
