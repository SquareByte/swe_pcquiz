
package application.logic.spielzug.impl;

import java.util.ArrayList;
import java.util.Random;

import application.logic.port.SpielzugPort;
import application.logic.spielzug.SpielBrett;
import application.logic.spielzug.port.SpieleSpielzug;
import application.statemachine.StateMachineFactoryImpl;
import application.statemachine.port.StateMachine;
import application.statemachine.port.State.S;

/**
 * 
 * MAIN class for Use Case 1. Here the magic happens.
 *
 */
public class SpielzugPortImpl implements SpielzugPort, SpieleSpielzug {
	
	private Random wuerfel;
	private SpielBrett spielBrett;
	
	private StateMachine stateMachine;
	
	private int augenzahl = 0;
	private int spielerImSpiel;
	private int spieler;
	private int try_; // try is keyword
	
	private final int MAX_TRIES = 3;
	
	public SpielzugPortImpl() {
		this.wuerfel = new Random();
		this.spielBrett = new SpielBrett();
		this.stateMachine = StateMachineFactoryImpl.FACTORY.stateMachinePort().stateMachine();
		
		//this.stateMachine.attach(this);

		this.spieler = 0;
		this.spielerImSpiel = 3; // DEBUG / TEST
		this.try_ = 1;
	}
	
	public int getAugenzahl() {
		return this.augenzahl;
	}
	
	public int getAktiverSpieler() {
		return this.spieler;
	}
	
	public ArrayList<Integer> getStreiterZurWahl() {
		return this.spielBrett.streiterIndizes(this.spieler);
	}
	
	public int getSpielerImSpiel() {
		return this.spielerImSpiel;
	}
	
	/**
	 * Wraps "this.spieler++; setState(WuerfelWarten);"
	 * 
	 * @param gezogen
	 */
	private void next(boolean gezogen) {
		if (gezogen || this.try_ >= this.MAX_TRIES) {
			this.try_ = 1;
			this.spieler = (this.spieler + 1) % this.spielerImSpiel;
		} else {
			this.try_++;
		}
		this.stateMachine.setState(S.WuerfelWarten);
	}
	
	public void wuerfeln() {
		this.augenzahl = this.wuerfel.nextInt(6) + 1;
		// "check state"
		boolean gezogen = false;
		if (augenzahl == 6 && this.spielBrett.istHeimatfeldBesetzt(this.spieler)) {
			this.spielBrett.zieheRaus(this.spieler);
			gezogen = true;
		} else {
			ArrayList<Integer> zurWahl = this.spielBrett.streiterIndizes(this.spieler);
			if (zurWahl.size() > 1) {
				//ArrayList<Integer> zurWahl = this.spielBrett.streiterIndizes(this.spieler);
				this.stateMachine.setState(S.StreiterwahlWarten);
				return;
			} else {
				// Shortcut: play only move
				if (zurWahl.size() == 1) {
					this.spielBrett.ziehe(zurWahl.get(0), this.augenzahl);
					gezogen = true;
				}
			}
		}
		next(gezogen);
	}

	public void streiterWaehlen(int feld) {
		if (this.spielBrett.streiterIndizes(this.spieler).contains(feld)) {
			this.spielBrett.ziehe(feld, this.augenzahl);
			this.next(true);			
		} // else retry
	}

	@Override
	public SpieleSpielzug spieleSpielzug() {
		return this;
	}

	@Override
	public SpielBrett getSpielbrett() {
		return this.spielBrett;
	}

}
