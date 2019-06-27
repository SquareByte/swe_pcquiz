package application.logic.spielzug;

import application.logic.spielzug.impl.SpielzugPortImpl;
import application.logic.spielzug.port.SimpleSpielzugPort;
import application.logic.spielzug.port.SpieleSpielzug;
import application.statemachine.StateMachineFactory;
import application.statemachine.port.StateMachine;
import application.statemachine.port.StateMachinePort;

public class SpielzugFactoryImpl implements SpielzugFactory, SimpleSpielzugPort {
	
	private SpielzugPortImpl spielzugPortImpl;
	
	//private StateMachinePort stateMachinePort = StateMachineFactory.FACTORY.stateMachinePort();
	//private StateMachine stateMachine;
	
	public SimpleSpielzugPort simpleSpielzugPort() {return this;}
	
	public SpieleSpielzug spieleSpielzug() {
		if (this.spielzugPortImpl == null) {
			this.spielzugPortImpl = new SpielzugPortImpl();
		}
		return this.spielzugPortImpl;
	}
	
	public void wuerfeln() {
		this.spielzugPortImpl.wuerfeln();
	}
	
	public void streiterWaehlen(int feld) {
		this.spielzugPortImpl.streiterWaehlen(feld);
	}
	
}
