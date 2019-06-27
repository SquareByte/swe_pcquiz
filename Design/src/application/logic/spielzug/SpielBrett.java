package application.logic.spielzug;

import java.util.ArrayList;

public class SpielBrett {
	private final int MAX_SPIELER = 4;
	private final int MAX_SPIELFIGUREN = 3;
	private final int LEN_WISSENSPFAD = 48;
	private final int LEER = -1; // nicht-Spieler-Index
	
	private int[] heimatfeldBelegung;
	private int[] wissensPfad;
	
	public SpielBrett() {
		this.heimatfeldBelegung = new int[MAX_SPIELER];
		for (int i = 0; i < MAX_SPIELER; i++) {
			this.heimatfeldBelegung[i] = MAX_SPIELFIGUREN;
		}
		this.wissensPfad = new int[LEN_WISSENSPFAD];
		for (int i = 0; i < LEN_WISSENSPFAD; i++) {
			this.wissensPfad[i] = LEER;
		}
	}

	public boolean istHeimatfeldBesetzt(int spieler) {
		return this.heimatfeldBelegung[spieler] > 0;
	}

	public int streiterImSpiel(int spieler) {
		return MAX_SPIELFIGUREN - this.heimatfeldBelegung[spieler];
	}

	public ArrayList<Integer> streiterIndizes(int spieler) {
		ArrayList<Integer> result = new ArrayList<>();
		for (int i = 0; i < LEN_WISSENSPFAD; i++) {
			if (this.wissensPfad[i] == spieler) {
				result.add(i);
			}
		}
		return result;
	}
	
	public void zieheRaus(int spieler) {
		this.heimatfeldBelegung[spieler]--;
		this.belegeFeld(spieler, (LEN_WISSENSPFAD / MAX_SPIELER) * spieler); // Spieler 0 Startfeld 0, Spieler 1 hat Startfeld 12, ...
	}
	
	public void ziehe(int vonFeld, int wuerfelZahl) {
		int lastBelegung = this.wissensPfad[vonFeld];
		this.gebeFeldFrei(vonFeld);
		this.belegeFeld(lastBelegung, (vonFeld + wuerfelZahl) % LEN_WISSENSPFAD);
	}
	
	private void belegeFeld(int spieler, int feldIndex) {
		if (this.wissensPfad[feldIndex] == LEER) {
			this.wissensPfad[feldIndex] = spieler;
		} else {
			this.belegeFeld(spieler, (feldIndex+1) % LEN_WISSENSPFAD); // XXX hacks while collision is not implemented
		}
	}
	
	private void gebeFeldFrei(int feldIndex) {
		this.wissensPfad[feldIndex] = LEER;
	}
}