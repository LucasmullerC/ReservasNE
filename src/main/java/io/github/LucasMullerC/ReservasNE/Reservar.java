package io.github.LucasMullerC.ReservasNE;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;

import com.noahhusby.sledgehammer.Constants;
import com.noahhusby.sledgehammer.datasets.projection.GeographicProjection;
import com.noahhusby.sledgehammer.datasets.projection.ModifiedAirocean;
import com.noahhusby.sledgehammer.datasets.projection.ScaleProjection;

import io.github.LucasMullerC.ReservasNE.Comandos.reserva;
import io.github.LucasMullerC.ReservasNE.util.AreaEmConstrucao;
import io.github.LucasMullerC.ReservasNE.util.AreaFinalizada;
import io.github.LucasMullerC.ReservasNE.util.ListStore;
import io.github.LucasMullerC.ReservasNE.util.Mapscsv;
import io.github.LucasMullerC.ReservasNE.util.Pendente;
import io.github.LucasMullerC.ReservasNE.util.PlayerStore;

public class Reservar {
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
	private static final GeographicProjection projection = new ModifiedAirocean();
	private static final GeographicProjection uprightProj = GeographicProjection.orientProjection(projection,
			GeographicProjection.Orientation.upright);
	private static final ScaleProjection scaleProj = new ScaleProjection(uprightProj, Constants.SCALE, Constants.SCALE);

	public static String addarea(String regiao, String Dif, Location P) {
		int N = Integer.parseInt(Dif);
		String Nome = regiao + Dif + area.getValues().size();
		R = new Regions(Nome);
		R.setEstado(false);
		R.setDificuldade(N);
		R.setPos(P);
		area.add(R);
		return Nome;
	}
	public static void addBuilder(String UUID,int QE,int QM, int QH, int QP, int Rank) {
		G = new Gerenciador(UUID);
		G.setQtdEasy(QE);
		G.setQtdMedium(QM);
		G.setQtdHard(QH);
		G.setQtdPRO(QP);
		G.setRank(Rank);
		G.setGrupo(false);
		G.setRegiao("nulo");
		player.add(G);
	}
	public static void AddArea4(String Nome,Location L) {
		R = new Regions(Nome);
		R.setEstado(false);
		R.setDificuldade(4);
		R.setPos(L);
		area.add(R);
	}
	public static void RemoverArea(String Nome) {
		area.remove(getRegionPos(Nome));
	}
	public static void ASRtoAC(String Nome,String Player) {
		A = getASRPos(Nome);
		A.setCredito(Player);
		AC.add(A);
		ASR.remove(A);
	}
	public static void ACtoASR(String Nome) {
		A = getACPos(Nome);
		A.setCredito("nulo");
		ASR.add(A);
		AC.remove(A);
	}
	public static void ACtoAF(String Nome) {
		A = getACPos(Nome);
		AF.add(A);
		AC.remove(A);
	}
	public static void RemoverPendente() {
		pendente.clear();
	}
	public static void addSemReserva(Areas p) {
		ASR.add(p);
	}

	private static Regions Apply(String UUID) {
		Random randomGenerator = new Random();
		G = new Gerenciador(UUID);
		G.setRank(0);
		ArrayList<Regions> applylist = new ArrayList<Regions>();
		for (Regions string : area.getValues()) {
			if (string.getNome().matches("(?i)(apply0).*")) {
				applylist.add(string);
			}
		}
		do {
			int index = randomGenerator.nextInt(applylist.size());
			String nome = applylist.get(index).getNome();
			R = getRegionPos(nome);
		} while (R.getEstado() == true);
		R.setEstado(true);
		G.setRegiao(R.getNome());
		player.add(G);
		return R;
	}
	public static boolean ClaimPlayer (String UUID, String Regiao) {
		R = getRegionPos(Regiao);
		G = getCategoryPos(UUID);
		if (R.getEstado() == false) {
			R.setEstado(true);
			G.setRegiao(R.getNome());
			return true;
		}
		else {
			return false;
		}
	}
	public static Regions reservatier (String UUID, String Regiao) {
		G = getCategoryPos(UUID);
		if (G.getRegiao().matches("nulo")) {
			R = getRegionPos(Regiao);
			R.setEstado(true);
			G.setRegiao(R.getNome());
			return R;
		} else {
			return null;
		}
		
	}
	private static Regions Claim(String UUID, String Regiao, String Dif, Gerenciador G) {
		Random randomGenerator = new Random();
		ArrayList<Regions> claimlist = new ArrayList<Regions>();
		String RM = Regiao + Dif;
		for (Regions string : area.getValues()) {
			if (string.getNome().matches("(?i)("+RM.toString()+").*")) {
				claimlist.add(string);
			}
		}
		if (claimlist.size() <= 0) {
			return null;
		}
		else {
			do {
				int index = randomGenerator.nextInt(claimlist.size());
				String nome = claimlist.get(index).getNome();
				R = getRegionPos(nome);
			} while (R.getEstado() == true);
			R.setEstado(true);
			G.setRegiao(R.getNome());
			return R;
		}
	}
	public static Regions checkplayer(String UUID, String Regiao, String Dif) {
		G = getCategoryPos(UUID);
		if (G == null) {
			return Apply(UUID);
		} else {
			if (G.getRank() < Integer.parseInt(Dif)) {
				return null;
			} else {
				if (G.getRegiao().matches("nulo")) {
					return Claim(UUID, Regiao, Dif, G);
				} else {
					return null;
				}
			} 

		}
	}
	public static Location getCoord (String UUID) {
		G = getCategoryPos(UUID);
		if (G == null) {
			return null;
		}
		else {
			R = getRegionPos(G.getRegiao());
			return R.getPos();
		}
	}
	public static Gerenciador getPlayer (String UUID) {
		return getCategoryPos(UUID);
	}
	public static Regions getRegiao (String Nome) {
		return getRegionPos(Nome);
	}

	public static boolean Cancelar(String UUID) {
		G = getCategoryPos(UUID);
		if (G.getRegiao().matches("nulo")) {
			return false;
		} else {
			if (G.getGrupo() == true) {
				G.setRegiao("nulo");
				return true;
			}
			else {
				R = getRegionPos(G.getRegiao());
				R.setEstado(false);
				G.setRegiao("nulo");
				return true;
			}
		}
	}

	public static boolean Finalizar(String UUID) {
		G = getCategoryPos(UUID);
		if (G.getRegiao().matches("nulo")) {
			return false;
		} else {
			String r = G.getRegiao();
			R = getRegionPos(r);
			int dif = R.getDificuldade();
			if (dif == 1) {
				G.setQtdEasy(G.getQtdEasy() + 1);
			} else if (dif == 2) {
				G.setQtdMedium(G.getQtdMedium() + 1);
			} else if (dif == 3) {
				G.setQtdHard(G.getQtdHard() + 1);
			}
			G.setRegiao("nulo");
			if (G.getGrupo() == true) {
				G.setGrupo(false);
			}
			return true;
		}
	}
	public static boolean addPendente (String UUID, String Nome) {
		G = getCategoryPos(UUID);
		if (G.getRegiao().matches("nulo")) {
			return false;
		} else {
			String r = G.getRegiao();
			R = getRegionPos(r);
			D = new DoneAreas(R.getNome());
			double[] coords = toGeo(R.getPos().getX(),R.getPos().getZ());
			D.setLat(String.valueOf(coords[1]));
			D.setLon(String.valueOf(coords[0]));
			D.setNome(Nome);
		    pendente.add(D);
			return true;
		}
	}
	public static DoneAreas getPendente () {
		if (pendente.getValues().isEmpty() == true) {
			return null;
		}
		else {
			D = pendente.getValues().get(0);
			return D;
		}
	}

	public static int Promocao(String UUID) {
		G = getCategoryPos(UUID);
		if (G.getRank() == 1) {
			if (G.getQtdEasy() >= 5) {
				G.setRank(2);
				return 2;
			} else {
				return 0;
			}
		} else if (G.getRank() == 2) {
			if (G.getQtdEasy() >= 7 && G.getQtdMedium() >= 5) {
				G.setRank(3);
				return 3;
			} else {
				return 0;
			}
		} else if (G.getRank() == 3) {
			if (G.getQtdEasy() >= 9 && G.getQtdMedium() >= 8 && G.getQtdHard() >= 5) {
				G.setRank(4);
				return 4;
			} else {
				return 0;
			}
		} else if (G.getRank() == 0) {
			G.setRank(1);
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Gets the geographical location from in-game coordinates
	 * 
	 * @param x X-Axis in-game
	 * @param z Z-Axis in-game
	 * @return The geographical location (Long, Lat)
	 */
	public static double[] toGeo(double x, double z) {
		return scaleProj.toGeo(x, z);
	}

	/**
	 * Gets in-game coordinates from geographical location
	 * 
	 * @param lon Geographical Longitude
	 * @param lat Geographic Latitude
	 * @return The in-game coordinates (x, z)
	 */
	public static double[] fromGeo(double lon, double lat) {
		return scaleProj.fromGeo(lon, lat);
	}

	public static String ExibirArea(String Num) {
		int N = Integer.parseInt(Num);
		R = area.getValues().get(N);
		String info = R.toString();
		return info;
	}
	public static int AreaSize() {
		return area.getValues().size();
	}
	public static void limpapend() {
		AP.clear();
	}

	public static String ExibirRank(String UUID) {
		G = getCategoryPos(UUID);
		return G.toString();
	}
	

	private static Gerenciador getCategoryPos(String search) {
		for (Gerenciador d : player.getValues()) {
			if (d.getUUID() != null && d.getUUID().contains(search)) {
				return d;
			}
		}
		return null;
	}
	public static ArrayList<Areas> getAreaPos() {
		return AP.getValues();
	}
	private static Areas getASRPos(String search) {
		for (Areas d : ASR.getValues()) {
			if (d.getNome() != null && d.getNome().contains(search)) {
				return d;
			}
		}
		return null;
	}
	private static Areas getACPos(String search) {
		for (Areas d : AC.getValues()) {
			if (d.getNome() != null && d.getNome().contains(search)) {
				return d;
			}
		}
		return null;
	}

	private static Regions getRegionPos(String search) {
		for (Regions d : area.getValues()) {
			if (d.getNome() != null && d.getNome().contains(search)) {
				return d;
			}
		}
		return null;
	}

}
