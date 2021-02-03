package main.groovy.cineditorml.dsl

import main.java.circular.cineditorml.kernel.behavioral.*;
import main.java.circular.cineditorml.kernel.structural.*;

abstract class CinEditorMLBasescript extends Script {
	// sensor "name" pin n
	def sensor(String name) {
		[pin: { n -> ((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().createSensor(name, n) },
		onPin: { n -> ((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().createSensor(name, n)}]
	}
	
	// actuator "name" pin n
	def actuator(String name) {
		[pin: { n -> ((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().createActuator(name, n) }]
	}

	def buzzer(String name) {
		[pin: { n -> ((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().createBuzzer(name, n) }]
	}
	
	// state "name" means actuator becomes signal [and actuator becomes signal]*n
	def state(String name) {
		List<Action> actions = new ArrayList<Action>()
		((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createState(name, actions)
		// recursive closure to allow multiple and statements
		def closure
		closure = { actuator ->
			[becomes: { signal ->
				DigitalAction action = new DigitalAction()
				action.setActuator(actuator instanceof String ? (Actuator)((CinEditorMLBinding)this.getBinding()).getVariable(actuator) : (Actuator)actuator)
				action.setSignal(signal instanceof String ? (SIGNAL)((CinEditorMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal)
				actions.add(action)
				[and: closure]
			},
			 plays: { note ->
				 ToneAction action = new ToneAction()
				action.setActuator(actuator instanceof String ? (Actuator)((CinEditorMLBinding)this.getBinding()).getVariable(actuator) : (Actuator)actuator)
				action.setNote(note instanceof String ? (NOTE)((CinEditorMLBinding)this.getBinding()).getVariable(note) : (NOTE)note)
				actions.add(action)
				[and: closure,
				for : { duration ->
					[duration : { time ->
						actions.get(actions.size() - 1).setDuration(duration instanceof String ? (DURATION) ((CinEditorMLBinding) this.getBinding()).getVariable(duration) : (DURATION) duration)
						actions.get(actions.size() - 1).setNumberOfIteration(time)
						["time" : null]
					}]
				}]
			 }]
		}
		[means: closure]
	}
	
	// initial state
	def initial(state) {
		((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().setInitialState(state instanceof String ? (State)((CinEditorMLBinding)this.getBinding()).getVariable(state) : (State)state)
	}

	// from state1 to state2 when sensor [and/or sensor]*n becomes signal
	def from(state1) {
		List<Sensor> sensors = new ArrayList<Sensor>();
		State state2save;
		[to: { state2 ->
			def closureor
			def closurexor
			def closureand
			closureor = { sensor ->
				sensors.add(sensor instanceof String ? (Sensor)((CinEditorMLBinding)this.getBinding()).getVariable(sensor) : (Sensor)sensor)
				[and: closureand,
				 or: closureor,
				 xor: closurexor,
				 becomes: { signal ->
					 ((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createTransition(
							 state1 instanceof String ? (State)((CinEditorMLBinding)this.getBinding()).getVariable(state1) : (State)state1,
							 state2 instanceof String ? (State)((CinEditorMLBinding)this.getBinding()).getVariable(state2) : (State)state2,
							 sensors,
							 signal instanceof String ? (SIGNAL)((CinEditorMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal,
							 LOGICAL.OR)
				 }]
			}
			closurexor = { sensor ->
				sensors.add(sensor instanceof String ? (Sensor)((CinEditorMLBinding)this.getBinding()).getVariable(sensor) : (Sensor)sensor)
				[and: closureand,
				 or: closureor,
				 xor: closurexor,
				 becomes: { signal ->
					 ((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createTransition(
							 state1 instanceof String ? (State)((CinEditorMLBinding)this.getBinding()).getVariable(state1) : (State)state1,
							 state2 instanceof String ? (State)((CinEditorMLBinding)this.getBinding()).getVariable(state2) : (State)state2,
							 sensors,
							 signal instanceof String ? (SIGNAL)((CinEditorMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal,
							 LOGICAL.XOR)
				 }]
			}
			closureand = { sensor ->
				sensors.add(sensor instanceof String ? (Sensor)((CinEditorMLBinding)this.getBinding()).getVariable(sensor) : (Sensor)sensor)
				[and: closureand,
				 or: closureor,
				 xor: closurexor,
				 becomes: { signal ->
					 ((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createTransition(
							 state1 instanceof String ? (State)((CinEditorMLBinding)this.getBinding()).getVariable(state1) : (State)state1,
							 state2 instanceof String ? (State)((CinEditorMLBinding)this.getBinding()).getVariable(state2) : (State)state2,
							 sensors,
							 signal instanceof String ? (SIGNAL)((CinEditorMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal,
							 LOGICAL.AND)
				 }]
			}
			[when: closureand]
		}]
	}
	
	// export name
	def export(String name) {
		println(((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().generateCode(name).toString())
	}
	
	// disable run method while running
	int count = 0
	abstract void scriptBody()
	def run() {
		if(count == 0) {
			count++
			scriptBody()
		} else {
			println "Run method is disabled"
		}
	}
}
