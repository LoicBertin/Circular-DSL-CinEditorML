package fr.circular.cineditorml.kernel.generator;

import fr.circular.cineditorml.kernel.App;
import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Visitor<T> {

	public abstract void visit(App app);

	public abstract void visit(State state);
	public abstract void visit(Transition transition);
	public abstract void visit(DigitalAction action);
	public abstract void visit(ToneAction action);

	public abstract void visit(Actuator actuator);
	public abstract void visit(Buzzer buzzer);
	public abstract void visit(Sensor sensor);

	public abstract void visit(Instruction instruction);
	public abstract void visit(DurationInstruction durationInstruction);
	public abstract void visit(Clip clip);
	public abstract void visit(TextClip textClip);
	public abstract void visit(VideoClip videoClip);


	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String,Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}

}

