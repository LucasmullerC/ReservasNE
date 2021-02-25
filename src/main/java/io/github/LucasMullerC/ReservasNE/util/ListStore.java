
package io.github.LucasMullerC.ReservasNE.util;

import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import io.github.LucasMullerC.ReservasNE.ReservasNE;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import io.github.LucasMullerC.Objetos.Regions;
import java.util.ArrayList;
import java.io.File;

public class ListStore
{
    private File storageFile;
    private ArrayList<Regions> values;
    private Regions R;
    
    public ListStore(final File file) {
        this.storageFile = file;
        this.values = new ArrayList<Regions>();
        if (!this.storageFile.exists()) {
            try {
                this.storageFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void load() {
        try {
            final DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
            final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            int cont = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (!this.Contains(new Regions(line))) {
                    switch (cont) {
                        case 0: {
                            this.R = new Regions(line);
                            ++cont;
                            continue;
                        }
                        case 1: {
                            this.R.setEstado(Boolean.parseBoolean(line));
                            ++cont;
                            continue;
                        }
                        case 2: {
                            this.R.setDificuldade(Integer.parseInt(line));
                            ++cont;
                            continue;
                        }
                        case 3: {
                            this.R.setPos(ReservasNE.getLocationString(line));
                            this.values.add(this.R);
                            cont = 0;
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                }
            }
            reader.close();
            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        try {
            final FileWriter stream = new FileWriter(this.storageFile);
            final BufferedWriter out = new BufferedWriter(stream);
            for (final Regions value : this.values) {
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean Contains(final Regions value) {
        return this.values.contains(value);
    }
    
    public void add(final Regions value) {
        if (!this.Contains(value)) {
            this.values.add(value);
        }
    }
    
    public void remove(final Regions value) {
        this.values.remove(value);
    }
    
    public void clear() {
        this.values.clear();
    }
    
    public ArrayList<Regions> getValues() {
        return this.values;
    }
}
