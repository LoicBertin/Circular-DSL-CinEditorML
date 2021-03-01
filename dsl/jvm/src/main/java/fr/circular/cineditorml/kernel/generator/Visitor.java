package fr.circular.cineditorml.kernel.generator;

import fr.circular.cineditorml.kernel.App;
import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Visitor<T> {

	public abstract void visit(App app);

	public abstract void visit(Instruction instruction);
	public abstract void visit(DurationInstruction durationInstruction);
	public abstract void visit(PositionInstruction positionInstruction);
	public abstract void visit(AnimationInstruction animationInstruction, TextClip textClip);
	public abstract void visit(OpacityInstruction opacityInstruction);
	public abstract void visit(Clip clip);
	public abstract void visit(TextClip textClip);
	public abstract void visit(VideoClip videoClip);
	public abstract void visit(SubClip subClip);
	public abstract void visit(ColorClip colorClip);
	public abstract void visit(MergeClip mergeClip);
	public abstract void visit(ConcatenateClip concatenateClip);


	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String,Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}

}

