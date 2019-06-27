package application.gui.impl;

import java.io.DataInputStream;

import application.logic.Logic;
import application.logic.spielzug.impl.SpielzugPortImpl;
import application.logic.spielzug.port.SpieleSpielzug;
import application.statemachine.port.Observer;
import application.statemachine.port.State;

/**
 * Der SpielzugController!
 */
public class Controller implements Observer {
	private View myView;
	private Logic myModel;
	
	
	private DataInputStream in;
	private String targetInput;
	private String command;
	
	Controller(View view, Logic model) {
		this.in = new DataInputStream(System.in);
		
		this.myView = view;
		this.myModel = model;
		this.myModel.attach(this);
		
		this.targetInput = "x";
		this.command = "Hallo und Willkommen bei\n" + 
	               "+-----------------------------------------+\n" + 
				   "|   ____   ___       __   _  _  __  ____  |\n" +
		   		   "|  (  _ \\ / __)___  /  \\ / )( \\(  )(__  ) |\n" +
		   		   "|   ) __/( (__(___)(  O )) \\/ ( )(  / _/  |\n" +
		   		   "|  (__)   \\___)     \\__\\)\\____/(__)(____) |\n" +
	               "+-----------------------------------------+\n";
	}
	
	/**
	 * SpielZug Controller
	 */
	public void update(State newState) {
		if (newState.isSubStateOf(State.S.SpielzugSpielen)) {
			if (newState.isEqualStateOf(State.S.WuerfelWarten)) {
				this.targetInput = "x";
				this.command = "Bitte drücke <x> und bestätige mit Enter, um zu Würfeln!";
			} else if (newState.isEqualStateOf(State.S.StreiterwahlWarten)) {
				this.targetInput = null;
				this.command = "Wähle den zu ziehenden Wissensstreiter, indem du\n" +
				               "seine Position eingibst und mit Enter bestätigst!";
			}
			System.err.println("Unknown state " + newState.stateName());
		}
	}
	
	void doit() {
		int i = -1;
		String s;
		
		this.myView.show(this.command);
		
		try {
			s = this.in.readLine();
		} catch (Exception ioException) {
			this.myView.show("[[ Es ist ein Fehler aufgetreten ]]");
			s = "err";
		}
		if (this.targetInput != null) {
			if (s.compareToIgnoreCase(this.targetInput) == 0) {
				this.myModel.wuerfeln();
			} else if (s.toLowerCase().startsWith("q")) {
				this.myView.stop();
			}
		} else {
			try {
				i = Integer.parseInt(s);
				// TODO validate
			} catch (NumberFormatException e) {
				// retry
				this.myView.show("Bitte gib eine Zahl ein!");
				this.doit();
			}
			this.myModel.streiterWaehlen(i);
		}
	}
}
