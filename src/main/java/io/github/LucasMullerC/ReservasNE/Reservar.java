
package io.github.LucasMullerC.ReservasNE;

import com.noahhusby.sledgehammer.datasets.projection.ModifiedAirocean;
import org.bukkit.Bukkit;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Location;
import com.noahhusby.sledgehammer.datasets.projection.ScaleProjection;
import com.noahhusby.sledgehammer.datasets.projection.GeographicProjection;
import io.github.LucasMullerC.Objetos.Gerenciador;
import io.github.LucasMullerC.Objetos.DoneAreas;
import io.github.LucasMullerC.Objetos.Areas;
import io.github.LucasMullerC.Objetos.Regions;
import io.github.LucasMullerC.ReservasNE.util.AreaFinalizada;
import io.github.LucasMullerC.ReservasNE.util.AreaEmConstrucao;
import io.github.LucasMullerC.ReservasNE.util.Pendente;
import io.github.LucasMullerC.ReservasNE.util.Mapscsv;
import io.github.LucasMullerC.ReservasNE.util.PlayerStore;
import io.github.LucasMullerC.ReservasNE.util.ListStore;

public class Reservar
{
    public static ListStore area;
    public static PlayerStore player;
    public static Mapscsv pendente;
    public static Pendente AP;
    public static Pendente ASR;
    public static AreaEmConstrucao AC;
    public static AreaFinalizada AF;
    static Regions R;
    static Areas A;
    static DoneAreas D;
    static Gerenciador G;
    private static final GeographicProjection projection;
    private static final GeographicProjection uprightProj;
    private static final ScaleProjection scaleProj;
    
    public static String addarea(final String regiao, final String Dif, final Location P) {
        final int N = Integer.parseInt(Dif);
        final String Nome = regiao + Dif + Reservar.area.getValues().size();
        (Reservar.R = new Regions(Nome)).setEstado(false);
        Reservar.R.setDificuldade(N);
        Reservar.R.setPos(P);
        Reservar.area.add(Reservar.R);
        return Nome;
    }
    
    public static void addBuilder(final String UUID, final int QE, final int QM, final int QH, final int QP, final int Rank) {
        (Reservar.G = new Gerenciador(UUID)).setQtdEasy(QE);
        Reservar.G.setQtdMedium(QM);
        Reservar.G.setQtdHard(QH);
        Reservar.G.setQtdPRO(QP);
        Reservar.G.setRank(Rank);
        Reservar.G.setGrupo(false);
        Reservar.G.setRegiao("nulo");
        Reservar.G.setDiscord("nulo");
        Reservar.player.add(Reservar.G);
    }
    
    public static void AddArea4(final String Nome, final Location L) {
        (Reservar.R = new Regions(Nome)).setEstado(false);
        Reservar.R.setDificuldade(4);
        Reservar.R.setPos(L);
        Reservar.area.add(Reservar.R);
    }
    
    public static void RemoverArea(final String Nome) {
        Reservar.area.remove(getRegionPos(Nome));
    }
    
    public static void ASRtoAC(final String Nome, final String Player) {
        (Reservar.A = getASRPos(Nome)).setCredito(Player);
        Reservar.AC.add(Reservar.A);
        Reservar.ASR.remove(Reservar.A);
    }
    
    public static void ACtoASR(final String Nome) {
        (Reservar.A = getACPos(Nome)).setCredito("nulo");
        Reservar.ASR.add(Reservar.A);
        Reservar.AC.remove(Reservar.A);
    }
    
    public static void ACtoAF(final String Nome) {
        Reservar.A = getACPos(Nome);
        Reservar.AF.add(Reservar.A);
        Reservar.AC.remove(Reservar.A);
    }
    
    public static void RemoverPendente() {
        Reservar.pendente.clear();
    }
    
    public static void addSemReserva(final Areas p) {
        Reservar.ASR.add(p);
    }
    
    public static void LinkDiscord(final String UUID, final String Discord) {
        if (getCategoryPos(UUID) == null) {
            (Reservar.G = new Gerenciador(UUID)).setRank(0);
            Reservar.G.setRegiao("nulo");
            Reservar.G.setDiscord("nulo");
            Reservar.player.add(Reservar.G);
        }
        else {
            Reservar.G.setDiscord(Discord);
        }
    }
    
    private static Regions Apply(final String UUID) {
        final Random randomGenerator = new Random();
        final ArrayList<Regions> applylist = new ArrayList<Regions>();
        for (final Regions string : Reservar.area.getValues()) {
            if (string.getNome().matches("(?i)(apply0).*")) {
                applylist.add(string);
            }
        }
        do {
            final int index = randomGenerator.nextInt(applylist.size());
            final String nome = applylist.get(index).getNome();
            Reservar.R = getRegionPos(nome);
        } while (Reservar.R.getEstado());
        if (getCategoryPos(UUID) == null) {
            (Reservar.G = new Gerenciador(UUID)).setRank(0);
            Reservar.R.setEstado(true);
            Reservar.G.setRegiao(Reservar.R.getNome());
            Reservar.G.setDiscord("nulo");
            Reservar.player.add(Reservar.G);
        }
        else {
            Reservar.R.setEstado(true);
            Reservar.G.setRegiao(Reservar.R.getNome());
        }
        return Reservar.R;
    }
    
    public static boolean ClaimPlayer(final String UUID, final String Regiao) {
        Reservar.R = getRegionPos(Regiao);
        Reservar.G = getCategoryPos(UUID);
        if (!Reservar.R.getEstado()) {
            Reservar.R.setEstado(true);
            Reservar.G.setRegiao(Reservar.R.getNome());
            return true;
        }
        return false;
    }
    
    public static Regions reservatier(final String UUID, final String Regiao) {
        Reservar.G = getCategoryPos(UUID);
        if (Reservar.G.getRegiao().matches("nulo")) {
            (Reservar.R = getRegionPos(Regiao)).setEstado(true);
            Reservar.G.setRegiao(Reservar.R.getNome());
            return Reservar.R;
        }
        return null;
    }
    
    private static Regions Claim(final String UUID, final String Regiao, final String Dif, final Gerenciador G) {
        final Random randomGenerator = new Random();
        final ArrayList<Regions> claimlist = new ArrayList<Regions>();
        final String RM = Regiao + Dif;
        for (final Regions string : Reservar.area.getValues()) {
            if (string.getNome().matches("(?i)(" + RM.toString() + ").*")) {
                claimlist.add(string);
            }
        }
        if (claimlist.size() <= 0) {
            return null;
        }
        do {
            final int index = randomGenerator.nextInt(claimlist.size());
            final String nome = claimlist.get(index).getNome();
            Reservar.R = getRegionPos(nome);
        } while (Reservar.R.getEstado());
        Reservar.R.setEstado(true);
        G.setRegiao(Reservar.R.getNome());
        return Reservar.R;
    }
    
    public static Regions checkplayer(final String UUID, final String Regiao, final String Dif) {
        Reservar.G = getCategoryPos(UUID);
        if (Reservar.G.getRank() == 0) {
            return null;
        }
        if (Reservar.G.getRank() < Integer.parseInt(Dif)) {
            return null;
        }
        if (Reservar.G.getRegiao().matches("nulo")) {
            return Claim(UUID, Regiao, Dif, Reservar.G);
        }
        return null;
    }
    
    public static Location getCoord(final String UUID) {
        Reservar.G = getCategoryPos(UUID);
        if (Reservar.G == null) {
            return null;
        }
        Reservar.R = getRegionPos(Reservar.G.getRegiao());
        return Reservar.R.getPos();
    }
    
    public static Gerenciador getPlayer(final String UUID) {
        return getCategoryPos(UUID);
    }
    
    public static String getPlayerbyname(final String UUID) {
        if (Bukkit.getPlayer(UUID) != null) {
            return Bukkit.getPlayer(UUID).getName();
        }
        return "falso";
    }
    
    public static Regions getRegiao(final String Nome) {
        return getRegionPos(Nome);
    }
    
    public static boolean Cancelar(final String UUID) {
        Reservar.G = getCategoryPos(UUID);
        if (Reservar.G.getRegiao().matches("nulo")) {
            return false;
        }
        if (Reservar.G.getGrupo()) {
            Reservar.G.setRegiao("nulo");
            return true;
        }
        (Reservar.R = getRegionPos(Reservar.G.getRegiao())).setEstado(false);
        Reservar.G.setRegiao("nulo");
        return true;
    }
    
    public static boolean Finalizar(final String UUID) {
        Reservar.G = getCategoryPos(UUID);
        if (Reservar.G.getRegiao().matches("nulo")) {
            return false;
        }
        final String r = Reservar.G.getRegiao();
        Reservar.R = getRegionPos(r);
        final int dif = Reservar.R.getDificuldade();
        if (dif == 1) {
            Reservar.G.setQtdEasy(Reservar.G.getQtdEasy() + 1);
        }
        else if (dif == 2) {
            Reservar.G.setQtdMedium(Reservar.G.getQtdMedium() + 1);
        }
        else if (dif == 3) {
            Reservar.G.setQtdHard(Reservar.G.getQtdHard() + 1);
        }
        Reservar.G.setRegiao("nulo");
        if (Reservar.G.getGrupo()) {
            Reservar.G.setGrupo(false);
        }
        return true;
    }
    
    public static boolean addPendente(final String UUID, final String Nome) {
        Reservar.G = getCategoryPos(UUID);
        if (Reservar.G.getRegiao().matches("nulo")) {
            return false;
        }
        final String r = Reservar.G.getRegiao();
        Reservar.R = getRegionPos(r);
        (Reservar.D = new DoneAreas(Reservar.R.getNome())).setDificuldade(Reservar.R.getDificuldade());
        Reservar.D.setEstado(Reservar.R.getEstado());
        Reservar.D.setPlayer(Reservar.G.getUUID());
        Reservar.D.setPos(Reservar.R.getPos());
        Reservar.pendente.add(Reservar.D);
        return true;
    }
    
    public static DoneAreas getPendente() {
        if (Reservar.pendente.getValues().isEmpty()) {
            return null;
        }
        return Reservar.D = Reservar.pendente.getValues().get(0);
    }
    
    public static int Promocao(final String UUID) {
        Reservar.G = getCategoryPos(UUID);
        if (Reservar.G.getRank() == 1) {
            if (Reservar.G.getQtdEasy() >= 5) {
                Reservar.G.setRank(2);
                return 2;
            }
            return 0;
        }
        else if (Reservar.G.getRank() == 2) {
            if (Reservar.G.getQtdEasy() >= 7 && Reservar.G.getQtdMedium() >= 5) {
                Reservar.G.setRank(3);
                return 3;
            }
            return 0;
        }
        else if (Reservar.G.getRank() == 3) {
            if (Reservar.G.getQtdEasy() >= 9 && Reservar.G.getQtdMedium() >= 8 && Reservar.G.getQtdHard() >= 5) {
                Reservar.G.setRank(4);
                return 4;
            }
            return 0;
        }
        else {
            if (Reservar.G.getRank() == 0) {
                Reservar.G.setRank(1);
                return 1;
            }
            return 0;
        }
    }
    
    public static double[] toGeo(final double x, final double z) {
        return Reservar.scaleProj.toGeo(x, z);
    }
    
    public static double[] fromGeo(final double lon, final double lat) {
        return Reservar.scaleProj.fromGeo(lon, lat);
    }
    
    public static String ExibirArea(final String Num) {
        final int N = Integer.parseInt(Num);
        Reservar.R = Reservar.area.getValues().get(N);
        final String info = Reservar.R.toString();
        return info;
    }
    
    public static int AreaSize() {
        return Reservar.area.getValues().size();
    }
    
    public static void limpapend() {
        Reservar.AP.clear();
    }
    
    public static String ExibirRank(final String UUID) {
        Reservar.G = getCategoryPos(UUID);
        return Reservar.G.toString();
    }
    
    private static Gerenciador getCategoryPos(final String search) {
        for (final Gerenciador d : Reservar.player.getValues()) {
            if (d.getUUID() != null && d.getUUID().contains(search)) {
                return d;
            }
        }
        return null;
    }
    
    public static ArrayList<Areas> getAreaPos() {
        return (ArrayList<Areas>)Reservar.AP.getValues();
    }
    
    private static Areas getASRPos(final String search) {
        for (final Areas d : Reservar.ASR.getValues()) {
            if (d.getNome() != null && d.getNome().contains(search)) {
                return d;
            }
        }
        return null;
    }
    
    private static Areas getACPos(final String search) {
        for (final Areas d : Reservar.AC.getValues()) {
            if (d.getNome() != null && d.getNome().contains(search)) {
                return d;
            }
        }
        return null;
    }
    
    private static Regions getRegionPos(final String search) {
        for (final Regions d : Reservar.area.getValues()) {
            if (d.getNome() != null && d.getNome().contains(search)) {
                return d;
            }
        }
        return null;
    }
    
    static {
        projection = (GeographicProjection)new ModifiedAirocean();
        uprightProj = GeographicProjection.orientProjection(Reservar.projection, GeographicProjection.Orientation.upright);
        scaleProj = new ScaleProjection(Reservar.uprightProj, 7318261.522857145, 7318261.522857145);
    }
}
