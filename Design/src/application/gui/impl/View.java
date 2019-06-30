package application.gui.impl;

import application.logic.Logic;
import application.logic.spielzug.SpielBrett;
import application.statemachine.port.Observer;
import application.statemachine.port.State;

public class View implements Observer {
	private State currentState;
	private Controller controller;
	private Logic model;
	private boolean running = true;
	private boolean showResult;
	
	public View(Logic model) {
		this.model = model;
		this.model.attach(this);
		this.controller = new Controller(this, model);
		this.showResult = false;
		
		this.displaySplash();
	}
	
	public void update(State newState) {
		if (this.currentState != null && this.currentState.isEqualStateOf(State.S.WuerfelWarten)) {
			this.showResult = true;
		}
		this.currentState = newState;
	}
	
	public void display() {
		if (this.showResult) {
			displayResult();
			this.showResult = false;
		}
		
		if (this.currentState.isEqualStateOf(State.S.WuerfelWarten)) {
			displayHeader();
			displayStatus();
		} else if (this.currentState.isEqualStateOf(State.S.StreiterwahlWarten)) {
			displayHeader();
			displayOptions();
		} else {
			displaySplash();
		}
		
		System.out.println();
		
		/*System.out.println(model.getSpielbrett().getSpielbrettAsString());
		System.out.println("Würf> " + model.getAugenzahl());
		System.out.println("Nächst Spieler:> " + model.getAktiverSpieler());
		System.out.println("imSp> " + model.getStreiterZurWahl());
		System.out.println(">>>>> " + this.currentState.stateName());*/
	}
	
	private void displaySplash() {
		System.out.println("Hallo und Willkommen bei\n" + 
	               "+-----------------------------------------+\n" + 
				   "|   ____   ___       __   _  _  __  ____  |\n" +
		   		   "|  (  _ \\ / __)___  /  \\ / )( \\(  )(__  ) |\n" +
		   		   "|   ) __/( (__(___)(  O )) \\/ ( )(  / _/  |\n" +
		   		   "|  (__)   \\___)     \\__\\)\\____/(__)(____) |\n" +
	               "+-----------------------------------------+\n");
	}
	
	private void displayHeader() {
		System.out.println("_______________________________________________________________________");
		System.out.println(this.model.getSpielbrett().getSpielerFarbe(this.model.getAktiverSpieler()) + " ist am Zug");
		System.out.println("_______________________________");
	}
	
	private void displayStatus () {
		SpielBrett sb = this.model.getSpielbrett();
		for (int i = 0; i < this.model.getSpielerImSpiel(); i++) {
			System.out.println(String.format("%s:\t%d von 3 im Spiel: %s",
					sb.getSpielerFarbe(i), sb.streiterImSpiel(i), sb.streiterIndizes(i).toString()));
		}
	}

	private void displayResult() {
		System.out.println("\nDu hast eine *" + model.getAugenzahl() + "* gewürfelt!");
	}
	
	private void displayOptions() {
		System.out.println("Positionen: " + this.model.getStreiterZurWahl().toString()); 
	}
	
	public void show(String text) {
		System.out.println(text);
	}
	
	public void stop() {
		this.running = false;
	}
	
	public void startEventLoop() {
		while (running) {
			this.display();
			this.controller.doit();
		}
	}
}
