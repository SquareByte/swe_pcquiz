package application.statemachine.port;

public interface StateMachine {

	public void setState(State state);

	public State getState();
}