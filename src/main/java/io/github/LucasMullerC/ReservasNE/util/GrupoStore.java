

package io.github.LucasMullerC.ReservasNE.util;

import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import io.github.LucasMullerC.Objetos.Grupos;
import java.util.ArrayList;
import java.io.File;

public class GrupoStore
{
    private File storageFile;
    private ArrayList<Grupos> values;
    private Grupos G;
    
    public GrupoStore(final File file) {
        this.storageFile = file;
        this.values = new ArrayList<Grupos>();
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
                if (!this.Contains(new Grupos(line))) {
                    switch (cont) {
                        case 0: {
                            this.G = new Grupos(line);
                            ++cont;
                            continue;
                        }
                        case 1: {
                            this.G.setPlayer1(line);
                            ++cont;
                            continue;
                        }
                        case 2: {
                            this.G.setPlayer2(line);
                            ++cont;
                            continue;
                        }
                        case 3: {
                            this.G.setPlayer3(line);
                            ++cont;
                            continue;
                        }
                        case 4: {
                            this.G.setPendente(line);
                            ++cont;
                            this.values.add(this.G);
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
            for (final Grupos value : this.values) {
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean Contains(final Grupos value) {
        return this.values.contains(value);
    }
    
    public void add(final Grupos value) {
        if (!this.Contains(value)) {
            this.values.add(value);
        }
    }
    
    public void remove(final Grupos value) {
        this.values.remove(value);
    }
    
    public ArrayList<Grupos> getValues() {
        return this.values;
    }
}
