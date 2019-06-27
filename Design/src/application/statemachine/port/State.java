
package application.statemachine.port;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface State {

	public boolean isSubStateOf(State state);

	public boolean isSuperStateOf(State state);
	
	public boolean isEqualStateOf(State state);

	public String stateName();
	
	public enum S implements State {
		WuerfelWarten, StreiterwahlWarten, 
		SpielzugSpielen(WuerfelWarten, StreiterwahlWarten);

		/**
		 * @clientNavigability NAVIGABLE
		 * @directed true
		 * @supplierRole subState
		 */

		private List<State> subStates;

		private S(State... subS) {
			this.subStates = new ArrayList<>(Arrays.asList(subS));
		}

		@Override
		public boolean isSuperStateOf(State state) {
			boolean result = state == null || this == state; // self contained
			for (State s : this.subStates) // or
				result |= s.isSuperStateOf(state); // contained in a substate!
			return result;
		}

		@Override
		public boolean isSubStateOf(State state) {
			return (state == null) ? false : state.isSuperStateOf(this);
		}
		
		@Override
		public boolean isEqualStateOf(State state) {
			return this.isSuperStateOf(state) && state.isSuperStateOf(this);
		}
		
		@Override
		public final String stateName() {
			return this.name();
		}
	}

}