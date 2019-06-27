package application.logic;

import java.util.ArrayList;

import application.logic.spielzug.SpielzugFactoryImpl;
import application.logic.spielzug.port.SpieleSpielzug;
import application.statemachine.StateMachineFactory;
import application.statemachine.port.Observer;
import application.statemachine.port.State;
import application.statemachine.port.Subject;

public class Logic implements Observer, Subject, SpieleSpielzug {
	private ArrayList<Observer> observers = new ArrayList<>();
	private State currentState;
	
	private SpieleSpielzug spielZug;
	
	public Logic(State start) {
		
		this.spielZug = SpielzugFactoryImpl.FACTORY.simpleSpielzugPort().spieleSpielzug();
		(StateMachineFactory.FACTORY.subjectPort().subject()).attach(this);
		(StateMachineFactory.FACTORY.stateMachinePort().stateMachine()).setState(start);
	}

	public int getAugenzahl() {
		return this.spielZug.getAugenzahl();
	}
	
	public int getAktiverSpieler() {
		return this.spielZug.getAktiverSpieler();
	}
	
	public ArrayList<Integer> getStreiterZurWahl() {
		return this.spielZug.getStreiterZurWahl();
	}
	
	public void update(State newState) {
		this.currentState = newState;
		this.inform(); // delegate stateMachine changes
	};
	
	private void inform() {
		this.observers.forEach(obs -> obs.update(this.currentState));
	}
	
	@Override
	public void attach(Observer obs) {
		this.observers.add(obs);
		obs.update(this.currentState);
	}
	
	@Override
	public void detach(Observer obs) {
		this.observers.remove(obs);
	}
	
	// sysOp
	public void wuerfeln() {
		/* Wenn der Zustand passt, wird die Operation durchgeführt. */
		if (this.currentState.equals(State.S.WuerfelWarten)) {
			this.spielZug.wuerfeln();	
		}
		// no need for this.inform(), spielZug calls setState -> triggers Observers
	};
	
	public void streiterWaehlen(int feld) {
		/* Wenn der Zustand passt, wird die Operation durchgeführt. */
		this.spielZug.streiterWaehlen(feld);
		this.inform();
	};
}
