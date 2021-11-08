package dev.alfamike.martianrobots;

public enum Orientation {
	N {
		@Override
		public Orientation rotateLeft(Orientation input) {
			return Orientation.W;
		}

		@Override
		public Orientation rotateRight(Orientation input) {
			return Orientation.E;
		}
	},S {
		@Override
		public Orientation rotateLeft(Orientation input) {
			return Orientation.E;
		}

		@Override
		public Orientation rotateRight(Orientation input) {
			return Orientation.W;
		}
	},E {
		@Override
		public Orientation rotateLeft(Orientation input) {
			return Orientation.N;
		}

		@Override
		public Orientation rotateRight(Orientation input) {
			return Orientation.S;
		}
	},W {
		@Override
		public Orientation rotateLeft(Orientation input) {
			return Orientation.S;
		}

		@Override
		public Orientation rotateRight(Orientation input) {
			return Orientation.N;
		}
	};
	
	public abstract Orientation rotateLeft(Orientation input);
	public abstract Orientation rotateRight(Orientation input);
}
