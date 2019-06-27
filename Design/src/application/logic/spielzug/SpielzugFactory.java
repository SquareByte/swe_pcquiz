package application.logic.spielzug;

import application.logic.spielzug.port.SimpleSpielzugPort;

public interface SpielzugFactory {
	
	SimpleSpielzugPort simpleSpielzugPort();
	
	SpielzugFactory FACTORY = new SpielzugFactoryImpl();
	
}
