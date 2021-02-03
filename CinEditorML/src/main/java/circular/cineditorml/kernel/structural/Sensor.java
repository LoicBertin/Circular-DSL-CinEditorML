package main.java.circular.cineditorml.kernel.structural;

import main.java.circular.cineditorml.kernel.generator.Visitor;

public class Sensor extends Brick {
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
