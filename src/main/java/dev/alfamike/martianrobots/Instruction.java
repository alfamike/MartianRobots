package dev.alfamike.martianrobots;

public enum Instruction {
	L {
		@Override
		public Coordinate run(Coordinate co) {
			Orientation newOrientation = co.getOrientation().rotateLeft(co.getOrientation());
			co.setOrientation(newOrientation);
			return co;
		}
	},R {
		@Override
		public Coordinate run(Coordinate co) {
			Orientation newOrientation = co.getOrientation().rotateRight(co.getOrientation());
			co.setOrientation(newOrientation);
			return co;
		}
	},F {
		@Override
		public Coordinate run(Coordinate co) {
			
			switch (co.getOrientation()) {
			case N:
				co.setyAxis(co.getyAxis() + 1);
				break;
			case S:
				co.setyAxis(co.getyAxis() - 1);
				break;
			case E:
				co.setxAxis(co.getxAxis() + 1);
				break;
			case W:
				co.setxAxis(co.getxAxis() - 1);
				break;
				
			default:
				break;
			}
			return co;
		}
	};
	
	// abstract method
    public abstract Coordinate run(Coordinate co);
}
