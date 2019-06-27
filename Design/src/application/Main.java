package application;

import application.gui.impl.View;
import application.logic.Logic;
import application.statemachine.port.State;

public class Main {

	public static void main(String args[]) {
		State debug = State.S.WuerfelWarten;
		(new View(new Logic(debug))).startEventLoop();
		return;
	}
}
