package application.statemachine.port;

public interface Subject {
	void attach(Observer obs);
	void detach(Observer obs);
}