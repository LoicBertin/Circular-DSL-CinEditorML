package main.groovy.cineditorml.dsl;

import java.util.*;

import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.*;
import groovy.lang.Binding;
import fr.circular.cineditorml.kernel.App;
import fr.circular.cineditorml.kernel.generator.ToWiring;
import fr.circular.cineditorml.kernel.generator.Visitor;

public class CinEditorMLModel {
	//private List<Brick> bricks;
	//private List<State> states;
	private List<Clip> clips;
	private State initialState;
	
	private Binding binding;
	
	public CinEditorMLModel(Binding binding) {
		//this.bricks = new ArrayList<Brick>();
		//this.states = new ArrayList<State>();
		this.clips = new ArrayList<Clip>();
		this.binding = binding;
	}

	public void createVideoFileClip(String name, String path) {
		VideoClip videoClip = new VideoClip();
		videoClip.setName(name);
		videoClip.setFile(path);
		this.clips.add(videoClip);
		this.binding.setVariable(name, videoClip);
	}

	public void createTextClip(String name, String text, String time) {
		TextClip textClip = new TextClip();
		textClip.setName(name);
		textClip.setText(text);
		textClip.addInstruction(new DurationInstruction(time));
		this.clips.add(textClip);
		this.binding.setVariable(name, textClip);
	}

	public void createMergeClip(String clip, String text) {
		MergeClip mergeClip = new MergeClip();
		mergeClip.setName(name);
		textClip.setText(text);
		textClip.addInstruction(new DurationInstruction(time));
		this.clips.add(textClip);
		this.binding.setVariable(name, textClip);
	}

	/*
	public void createSensor(String name, Integer pinNumber) {
		Sensor sensor = new Sensor();
		sensor.setName(name);
		sensor.setPin(pinNumber);
		this.bricks.add(sensor);
		this.binding.setVariable(name, sensor);
//		System.out.println("> sensor " + name + " on pin " + pinNumber);
	}
	
	public void createActuator(String name, Integer pinNumber) {
		Actuator actuator = new Actuator();
		actuator.setName(name);
		actuator.setPin(pinNumber);
		this.bricks.add(actuator);
		this.binding.setVariable(name, actuator);
	}

	public void createBuzzer(String name, Integer pinNumber) {
		Buzzer buzzer = new Buzzer();
		buzzer.setName(name);
		buzzer.setPin(pinNumber);
		this.bricks.add(buzzer);
		this.binding.setVariable(name, buzzer);
	}
	
	public void createState(String name, List<Action> actions) {
		State state = new State();
		state.setName(name);
		state.setActions(actions);
		this.states.add(state);
		this.binding.setVariable(name, state);
	}
	
	public void createTransition(State from, State to, List<Sensor> sensors, SIGNAL value, LOGICAL logical) {
		Transition transition = new Transition();
		transition.setNext(to);
		transition.setSensor(sensors);
		transition.setValue(value);
		transition.setLogical(logical);
		from.setTransition(transition);
	}
	*/
	/*
	public void setInitialState(State state) {
		this.initialState = state;
	}*/
	
	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		App app = new App();
		app.setName(appName);
		//app.setBricks(this.bricks);
		//app.setStates(this.states);
		//app.setInitial(this.initialState);
		app.setClips(this.clips);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);
		
		return codeGenerator.getResult();
	}
}
