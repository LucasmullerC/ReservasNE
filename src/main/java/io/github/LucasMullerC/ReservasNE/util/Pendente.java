
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
import io.github.LucasMullerC.Objetos.Areas;
import java.util.ArrayList;
import java.io.File;

public class Pendente
{
    private File storageFile;
    private ArrayList<Areas> values;
    private Areas A;
    
    public Pendente(final File file) {
        this.storageFile = file;
        this.values = new ArrayList<Areas>();
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
            final int cont = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (!this.Contains(new Areas(line))) {
                    if (line.charAt(0) == '<' && line.charAt(1) == 'n') {
                        String[] nome = line.split(">");
                        nome = nome[1].split("<");
                        this.A = new Areas(nome[0]);
                    }
                    else if (line.charAt(0) == '<' && line.charAt(1) == 'd') {
                        String[] dif = line.split(">");
                        dif = dif[1].split("<");
                        this.A.setDificuldade(dif[0]);
                    }
                    else {
                        if (line.charAt(0) != '-') {
                            continue;
                        }
                        String aux = null;
                        final String[] coord = line.split(",");
                        for (int k = 0; k < 9; ++k) {
                            if (coord[k].charAt(0) == '0') {
                                final StringBuilder sb = new StringBuilder(coord[k]);
                                aux = sb.deleteCharAt(0).toString();
                            }
                            switch (k) {
                                case 0: {
                                    this.A.setP1y(coord[k]);
                                    break;
                                }
                                case 1: {
                                    this.A.setP1x(coord[k]);
                                    break;
                                }
                                case 2: {
                                    this.A.setP2y(aux);
                                    break;
                                }
                                case 3: {
                                    this.A.setP2x(coord[k]);
                                    break;
                                }
                                case 4: {
                                    this.A.setP3y(aux);
                                    break;
                                }
                                case 5: {
                                    this.A.setP3x(coord[k]);
                                    break;
                                }
                                case 6: {
                                    this.A.setP4y(aux);
                                    break;
                                }
                                case 7: {
                                    this.A.setP4x(coord[k]);
                                    this.values.add(this.A);
                                    break;
                                }
                            }
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
            for (final Areas value : this.values) {
                out.write("<Placemark>");
                out.newLine();
                out.write("<name>" + value.getNome() + "</name>");
                out.newLine();
                out.write("<description>" + value.getDificuldade() + "</description>");
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
                out.write(value.getP1y() + "," + value.getP1x() + ",0 " + value.getP2y() + "," + value.getP2x() + ",0 " + value.getP3y() + "," + value.getP3x() + ",0 " + value.getP4y() + "," + value.getP4x() + ",0 " + value.getP1y() + "," + value.getP1x() + ",0");
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean Contains(final Areas value) {
        return this.values.contains(value);
    }
    
    public void add(final Areas value) {
        if (!this.Contains(value)) {
            this.values.add(value);
        }
    }
    
    public void remove(final Areas value) {
        this.values.remove(value);
    }
    
    public void clear() {
        this.values.clear();
    }
    
    public ArrayList<Areas> getValues() {
        return this.values;
    }
}
