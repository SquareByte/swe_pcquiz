package application.statemachine.port;

public interface Observer {
	void update(State newState);
}
