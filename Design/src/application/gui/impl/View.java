package application.gui.impl;

import application.logic.Logic;
import application.statemachine.port.Observer;
import application.statemachine.port.State;

public class View implements Observer {
	private State currentState;
	private Controller controller;
	private Logic model;
	private boolean running = true;
	
	public View(Logic model) {
		this.model = model;
		this.model.attach(this);
		this.controller = new Controller(this, model);
	}
	
	public void update(State newState) {
		this.currentState = newState;
	}
	
	void display() {
		// display the state 
		/*if (this.currentState.equals(State.S)) {
			
		}*/
		System.out.println("Würf> " + model.getAugenzahl());
		System.out.println("Spie> " + model.getAktiverSpieler());
		System.out.println("imSp> " + model.getStreiterZurWahl());
		System.out.println(">>>>> " + this.currentState.stateName());
	}
	
	void show(String text) {
		System.out.println(text);
	}
	
	void stop() {
		this.running = false;
	}
	
	public void startEventLoop() {
		while (running) {
			this.display();
			this.controller.doit();
		}
	}
}
