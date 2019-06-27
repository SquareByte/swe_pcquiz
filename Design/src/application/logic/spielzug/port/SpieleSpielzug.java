package application.logic.spielzug.port;
public interface SpieleSpielzug {

	void streiterWaehlen(int feld);

	void wuerfeln();
	
	// Declare needed getters for the GUI here
	public int getAugenzahl();
	
	public int getAktiverSpieler();
	
	public java.util.ArrayList<Integer> getStreiterZurWahl();
	
}
