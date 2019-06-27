package application.logic;

import application.logic.port.SpielzugPort;
import application.logic.spielzug.SpielzugFactory;
import application.logic.spielzug.port.SimpleSpielzugPort;
import application.logic.spielzug.port.SpieleSpielzug;
import application.statemachine.StateMachineFactory;
import application.statemachine.port.Subject;
import application.statemachine.port.SubjectPort;
import application.logic.port.MVCPort;

public class LogicFactoryImpl implements LogicFactory, MVCPort, SpielzugPort{
	
	private SimpleSpielzugPort simpleSpielzugPort =
			SpielzugFactory.FACTORY.simpleSpielzugPort();
	private SubjectPort subjectPort = StateMachineFactory.FACTORY.subjectPort();
	
	public MVCPort mvcPort() {return this;}
	
	public SpielzugPort spielzugPort() {return this;}
	
	public SpieleSpielzug spieleSpielzug() {
		return this.simpleSpielzugPort.spieleSpielzug();
	}

	@Override
	public Subject subject() {
		return this.subjectPort.subject();
	}
	
}
