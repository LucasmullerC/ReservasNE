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

import io.github.LucasMullerC.ReservasNE.Regions;
import io.github.LucasMullerC.ReservasNE.ReservasNE;

public class ListStore {
	private File storageFile;
	private ArrayList<Regions> values;
	private Regions R;

	public ListStore(File file) {
		this.storageFile = file;
		this.values = new ArrayList<Regions>();
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
				if (this.Contains(new Regions(line)) == false) {
					switch (cont) {
					case 0:
						R = new Regions(line);
						cont++;
						break;
					case 1:
						R.setEstado(Boolean.parseBoolean(line));
						cont++;
						break;
					case 2:
						R.setDificuldade(Integer.parseInt(line));
						cont++;
						break;
					case 3:
						R.setPos(ReservasNE.getLocationString(line));
						values.add(R);
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
			for (Regions value : this.values) {
				out.write(String.valueOf(value.getNome()));
				out.newLine();
				out.write(String.valueOf(value.getEstado()));
				out.newLine();
				out.write(String.valueOf(value.getDificuldade()));
				out.newLine();
				out.write(ReservasNE.getStringLocation(value.getPos()));
				out.newLine();
			}
			out.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean Contains(Regions value) {
		return this.values.contains(value);
	}

	public void add(Regions value) {
		if (this.Contains(value) == false) {
			this.values.add(value);
		}
	}

	public void remove(Regions value) {
		this.values.remove(value);
	}

	public ArrayList<Regions> getValues() {
		return this.values;
	}

}
