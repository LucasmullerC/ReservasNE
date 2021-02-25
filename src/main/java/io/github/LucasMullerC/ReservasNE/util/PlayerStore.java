

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
import io.github.LucasMullerC.Objetos.Gerenciador;
import java.util.ArrayList;
import java.io.File;

public class PlayerStore
{
    private File storageFile;
    private ArrayList<Gerenciador> values;
    private Gerenciador G;
    
    public PlayerStore(final File file) {
        this.storageFile = file;
        this.values = new ArrayList<Gerenciador>();
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
                if (!this.Contains(new Gerenciador(line))) {
                    switch (cont) {
                        case 0: {
                            this.G = new Gerenciador(line);
                            ++cont;
                            continue;
                        }
                        case 1: {
                            this.G.setRegiao(line);
                            ++cont;
                            continue;
                        }
                        case 2: {
                            this.G.setQtdEasy(Integer.parseInt(line));
                            ++cont;
                            continue;
                        }
                        case 3: {
                            this.G.setQtdMedium(Integer.parseInt(line));
                            ++cont;
                            continue;
                        }
                        case 4: {
                            this.G.setQtdHard(Integer.parseInt(line));
                            ++cont;
                            continue;
                        }
                        case 5: {
                            this.G.setQtdPRO(Integer.parseInt(line));
                            ++cont;
                            continue;
                        }
                        case 6: {
                            this.G.setDiscord(line);
                            ++cont;
                            continue;
                        }
                        case 7: {
                            this.G.setRank(Integer.parseInt(line));
                            ++cont;
                            continue;
                        }
                        case 8: {
                            this.G.setGrupo(Boolean.parseBoolean(line));
                            ++cont;
                            continue;
                        }
                        case 9: {
                            this.G.setCargo(Boolean.parseBoolean(line));
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
            for (final Gerenciador value : this.values) {
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
                out.write(String.valueOf(value.getDiscord()));
                out.newLine();
                out.write(String.valueOf(value.getRank()));
                out.newLine();
                final Boolean GP = value.getGrupo();
                out.write(GP.toString());
                out.newLine();
                final Boolean CA = value.getCargo();
                out.write(CA.toString());
                out.newLine();
            }
            out.close();
            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean Contains(final Gerenciador value) {
        return this.values.contains(value);
    }
    
    public void add(final Gerenciador value) {
        if (!this.Contains(value)) {
            this.values.add(value);
        }
    }
    
    public void remove(final Gerenciador value) {
        this.values.remove(value);
    }
    
    public ArrayList<Gerenciador> getValues() {
        return this.values;
    }
}
