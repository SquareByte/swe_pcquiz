package application.statemachine;


import application.statemachine.impl.StateMachineImpl;
import application.statemachine.port.Observer;
import application.statemachine.port.State;
import application.statemachine.port.StateMachine;
import application.statemachine.port.StateMachinePort;
import application.statemachine.port.Subject;
import application.statemachine.port.SubjectPort;

public class StateMachineFactoryImpl implements StateMachineFactory, SubjectPort, Subject, StateMachinePort, StateMachine {

	private StateMachineImpl stateMachine;
	
	private void mkStateMachine(){
		if (this.stateMachine == null) {
			this.stateMachine = new StateMachineImpl();
		}
	}

	@Override
	public synchronized SubjectPort subjectPort() {
		return this;
	}

	@Override
	public synchronized StateMachinePort stateMachinePort() {
		return this;
	}


	@Override
	public synchronized StateMachine stateMachine() {
		this.mkStateMachine();
		return this.stateMachine;
	}

	@Override
	public synchronized Subject subject() {
		this.mkStateMachine();
		return this.stateMachine;
	}

	@Override
	public synchronized void attach(Observer obs) {
		this.stateMachine.attach(obs);
	}

	@Override
	public synchronized void detach(Observer obs) {
		this.stateMachine.detach(obs);
	}

	@Override
	public synchronized State getState() {
		return this.stateMachine.getState();
	}

	@Override
	public synchronized void setState(State state) {
		this.stateMachine.setState(state);
	}

}
