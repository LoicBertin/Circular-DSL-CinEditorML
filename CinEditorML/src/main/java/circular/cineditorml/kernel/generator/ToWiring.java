package main.java.circular.cineditorml.kernel.generator;

import main.java.circular.cineditorml.kernel.App;
import main.java.circular.cineditorml.kernel.behavioral.*;
import main.java.circular.cineditorml.kernel.structural.*;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {
	enum PASS {ONE, TWO}


	public ToWiring() {
		this.result = new StringBuffer();
	}

	private void w(String s) {
		result.append(String.format("%s",s));
	}

	@Override
	public void visit(App app) {
		//first pass, create global vars
		context.put("pass", PASS.ONE);
		w("// Wiring code generated from an ArduinoML model\n");
		w(String.format("// Application name: %s\n", app.getName())+"\n");

		w("long debounce = 200;\n");
		w("\nenum STATE {");
		String sep ="";
		for(State state: app.getStates()){
			w(sep);
			state.accept(this);
			sep=", ";
		}
		w("};\n");
		if (app.getInitial() != null) {
			w("STATE currentState = " + app.getInitial().getName()+";\n");
		}

		for(Brick brick: app.getBricks()){
			brick.accept(this);
		}

		//second pass, setup and loop
		context.put("pass",PASS.TWO);
		w("\nvoid setup(){\n");
		for(Brick brick: app.getBricks()){
			brick.accept(this);
		}
		w("}\n");

		w("\nvoid loop() {\n" +
			"\tswitch(currentState){\n");
		for(State state: app.getStates()){
			state.accept(this);
		}
		w("\t}\n" +
			"}");
	}

	@Override
	public void visit(Actuator actuator) {
		if(context.get("pass") == PASS.ONE) {
			return;
		}
		if(context.get("pass") == PASS.TWO) {
			w(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]\n", actuator.getPin(), actuator.getName()));
			return;
		}
	}

	@Override
	public void visit(Buzzer buzzer) {
		if(context.get("pass") == PASS.ONE) {
			return;
		}
		if(context.get("pass") == PASS.TWO) {
			w(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]\n", buzzer.getPin(), buzzer.getName()));
			return;
		}
	}


	@Override
	public void visit(Sensor sensor) {
		if(context.get("pass") == PASS.ONE) {
			w(String.format("\nboolean %sBounceGuard = false;\n", sensor.getName()));
			w(String.format("long %sLastDebounceTime = 0;\n", sensor.getName()));
			return;
		}
		if(context.get("pass") == PASS.TWO) {
			w(String.format("  pinMode(%d, INPUT);  // %s [Sensor]\n", sensor.getPin(), sensor.getName()));
			return;
		}
	}

	@Override
	public void visit(State state) {
		if(context.get("pass") == PASS.ONE){
			w(state.getName());
			return;
		}
		if(context.get("pass") == PASS.TWO) {
			w("\t\tcase " + state.getName() + ":\n");
			for (Action action : state.getActions()) {
				action.accept(this);
			}
			if (state.getTransition() != null) {
				state.getTransition().accept(this);
				w("\t\tbreak;\n");
			}
			return;
		}

	}

	@Override
	public void visit(Transition transition) {
		if(context.get("pass") == PASS.ONE) {
			return;
		}
		if(context.get("pass") == PASS.TWO) {
			for (Sensor sensor : transition.getSensor()){
				String sensorName = sensor.getName();
				w(String.format("\t\t\t%sBounceGuard = millis() - %sLastDebounceTime > debounce;\n",
						sensorName, sensorName));
			}
			w(String.format("\t\t\tif( "));
			for (Sensor sensor : transition.getSensor()) {
				String sensorName = sensor.getName();
				if (transition.getSensor().indexOf(sensor) == transition.getSensor().size()-1) {
					if(transition.getLogical().equals(LOGICAL.XOR)){
						w(String.format("(digitalRead(%d) == %s && %sBounceGuard", sensor.getPin(), transition.getValue(), sensorName));
						for(Sensor sensorBis : transition.getSensor()){
							if(!sensorBis.equals(sensor)){
								w(String.format(" && digitalRead(%d) == %s && %sBounceGuard", sensorBis.getPin(), invertSignal(transition.getValue()), sensorBis.getName()));
							}
						}
						w(String.format(")"));
					}else{
						w(String.format("digitalRead(%d) == %s && %sBounceGuard ",
								sensor.getPin(), transition.getValue(), sensorName));
					}
				} else {
					if (transition.getLogical().equals(LOGICAL.OR)) {
						w(String.format("digitalRead(%d) == %s && %sBounceGuard || ",
								sensor.getPin(), transition.getValue(), sensorName));
					} else if (transition.getLogical().equals(LOGICAL.AND)) {
						w(String.format("digitalRead(%d) == %s && %sBounceGuard && ",
								sensor.getPin(), transition.getValue(), sensorName));
					} else if (transition.getLogical().equals(LOGICAL.XOR)) {
						w(String.format("(digitalRead(%d) == %s && %sBounceGuard", sensor.getPin(), transition.getValue(), sensorName));
						for(Sensor sensorBis : transition.getSensor()){
							if(!sensorBis.equals(sensor)){
								w(String.format(" && digitalRead(%d) == %s && %sBounceGuard", sensorBis.getPin(), invertSignal(transition.getValue()), sensorBis.getName()));
							}
						}
						w(String.format(") ||"));
					}
				}
			}
			w(String.format("){\n"));
			for (Sensor sensor : transition.getSensor()){
				String sensorName = sensor.getName();
				w(String.format("\t\t\t\t%sLastDebounceTime = millis();\n", sensorName));
			}

			w("\t\t\t\tcurrentState = " + transition.getNext().getName() + ";\n");
			w("\t\t\t}\n");
			return;
		}
	}

	@Override
	public void visit(DigitalAction action) {
		if(context.get("pass") == PASS.ONE) {
			return;
		}
		if(context.get("pass") == PASS.TWO) {
			if(action.getNumberOfIteration() > 1){
				w(String.format("\t\t\tfor (int i = 0; i<%d; i++) {\n", action.getNumberOfIteration()));
				w(String.format("\t\t\t\tdigitalWrite(%d,%s);\n",action.getActuator().getPin(), action.getSignal()));
				w(String.format("\t\t\t}\n"));
			}else{
				w(String.format("\t\t\tdigitalWrite(%d,%s);\n",action.getActuator().getPin(), action.getSignal()));
			}
			return;
		}
	}

	@Override
	public void visit(ToneAction action) {
		if(context.get("pass") == PASS.ONE) {
			return;
		}
		if(context.get("pass") == PASS.TWO) {
			if(action.getNumberOfIteration() == 1){
				if(!action.getNote().equals(NOTE.STOP)){
					if(action.getDuration() == null){
						w(String.format("\t\t\ttone(%s, %d, 100);\n", action.getActuator().getPin(), noteConverter(action.getNote())));
					}else{
						w(String.format("\t\t\ttone(%s, %d, %d);\n", action.getActuator().getPin(), noteConverter(action.getNote()), timeConverter(action.getDuration())));
						w(String.format("\t\t\t\tdelay(%d);\n",timeConverter(action.getDuration()) + 300));
					}
				}else{
					w(String.format("\t\t\tnoTone(%s);\n", action.getActuator().getPin()));
				}
			}else{
				w(String.format("\t\t\tfor (int i = 0; i<%d; i++) {\n", action.getNumberOfIteration()));
				if(action.getDuration() == null){
					w(String.format("\t\t\t\ttone(%s, %d, 100);\n", action.getActuator().getPin(), noteConverter(action.getNote())));
					w(String.format("\t\t\t\tdelay(100);\n"));
				}else{
					w(String.format("\t\t\t\ttone(%s, %d, %d);\n", action.getActuator().getPin(), noteConverter(action.getNote()), timeConverter(action.getDuration())));
					w(String.format("\t\t\t\tdelay(%d);\n",timeConverter(action.getDuration()) + 300));
				}
				w(String.format("\t\t\t}\n"));
			}
		}
	}

	public int timeConverter(DURATION duration){
		switch (duration) {
			case SHORT:
				return 500;
			case LONG:
				return 2000;
			default: return 500;
		}
	}

	public int noteConverter(NOTE note) {
		switch (note) {
			case C4:
				return 262;
			case G3:
				return 196;
			case A3:
				return 220;
			case B3:
				return 247;
			default:
				return 0;
		}
	}

	public SIGNAL invertSignal(SIGNAL signal) {
		if (signal == SIGNAL.HIGH) {
			return SIGNAL.LOW;
		} else {
			return SIGNAL.HIGH;
		}
	}
}


