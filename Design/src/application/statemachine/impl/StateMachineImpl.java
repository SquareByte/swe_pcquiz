package application.statemachine.impl;

import application.statemachine.port.Observer;
import application.statemachine.port.State;
import application.statemachine.port.StateMachine;
import application.statemachine.port.Subject;

import java.util.ArrayList;

public class StateMachineImpl implements StateMachine, Subject {


	private State currentState;
	private ArrayList<Observer> observers;

	public StateMachineImpl(){
		this.currentState = State.S.StreiterwahlWarten;
		this.observers = new ArrayList<Observer>();
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

	@Override
	public State getState() {
		return this.currentState;
	}

	@Override
	public void setState(State state) {
		if (state.isEqualStateOf(State.S.SpielzugSpielen)) {
			this.currentState = State.S.WuerfelWarten; // WuerfelWarten is init state in SpielzugSpielen
		} else {
			this.currentState = state;
		}
		this.observers.forEach(obs -> obs.update(this.currentState));
	}

}

