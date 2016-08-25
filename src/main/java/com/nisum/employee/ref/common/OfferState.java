/**
 * 
 */
package com.nisum.employee.ref.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NISUM CONSULTING
 *
 */
public enum OfferState {
	NOTINITIATED {
		@Override
		public OfferState prev() {
			return NOTINITIATED;
		}

		@Override
		public List<OfferState> next() {
			List<OfferState> states = new ArrayList<>();
			states.add(INITIATED);
			states.add(CLOSED);
			return states;
		}
	},
	INITIATED {
		@Override
		public OfferState prev() {
			return NOTINITIATED;
		}

		@Override
		public List<OfferState> next() {
			List<OfferState> states = new ArrayList<>();
			states.add(APPROVED);
			states.add(REJECTED);		
			return states;
		}
	},
	APPROVED {
		@Override
		public OfferState prev() {
			return INITIATED;
		}

		@Override
		public List<OfferState> next() {
			List<OfferState> states = new ArrayList<>();
			states.add(RELEASED);
			states.add(HOLD);
			return states;
		}
	},
	HOLD {
		@Override
		public OfferState prev() {
			return INITIATED;
		}

		@Override
		public List<OfferState> next() {
			List<OfferState> states = new ArrayList<>();
			states.add(APPROVED);
			states.add(RELEASED);
			states.add(CLOSED);
			return states;
		}
	},
	RELEASED {
		@Override
		public OfferState prev() {
			return APPROVED;
		}

		@Override
		public List<OfferState> next() {
			List<OfferState> states = new ArrayList<>();
			states.add(ACCEPTED);
			states.add(REJECTED);
			return states;
		}
	},
	ACCEPTED {
		@Override
		public OfferState prev() {
			return RELEASED;
		}

		@Override
		public List<OfferState> next() {
			List<OfferState> states = new ArrayList<>();
			states.add(JOINED);
			states.add(CLOSED);
			return states;
		}
	},
	REJECTED {
		@Override
		public OfferState prev() {
			return RELEASED;
		}

		@Override
		public List<OfferState> next() {
			List<OfferState> states = new ArrayList<>();
			states.add(INITIATED);
			states.add(CLOSED);
			return states;
		}
	},
	JOINED {
		@Override
		public OfferState prev() {
			return RELEASED;
		}

		@Override
		public List<OfferState> next() {
			List<OfferState> states = new ArrayList<>();
			states.add(CLOSED);
			return states;
		}
	},
	CLOSED {
		@Override
		public OfferState prev() {
			return RELEASED;
		}

		@Override
		public List<OfferState> next() {
			return new ArrayList<>();
		}
	};

	public abstract List<OfferState> next();

	public abstract OfferState prev();

	public static final OfferState getState(String offerState) {
		for (OfferState state : OfferState.values()) {
			if (state.name().equalsIgnoreCase(offerState)) {
				return state;
			}
		}
		return NOTINITIATED;
	}
}
