package application.logic;

import application.logic.port.MVCPort;
import application.logic.port.SpielzugPort;

public interface LogicFactory {
	
	LogicFactory FACTORY =
			new LogicFactoryImpl();
	
	MVCPort mvcPort();
	
	SpielzugPort spielzugPort();
	
	
}
