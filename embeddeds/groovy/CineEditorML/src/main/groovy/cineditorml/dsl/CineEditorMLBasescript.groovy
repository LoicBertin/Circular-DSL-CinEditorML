package main.groovy.cineditorml.dsl

import fr.circular.cineditorml.kernel.behavioral.DigitalAction
import fr.circular.cineditorml.kernel.behavioral.LOGICAL
import fr.circular.cineditorml.kernel.behavioral.NOTE
import fr.circular.cineditorml.kernel.behavioral.DURATION
import fr.circular.cineditorml.kernel.behavioral.ToneAction
import fr.circular.cineditorml.kernel.behavioral.Action
import fr.circular.cineditorml.kernel.behavioral.State
import fr.circular.cineditorml.kernel.structural.Actuator
import fr.circular.cineditorml.kernel.structural.Sensor
import fr.circular.cineditorml.kernel.structural.SIGNAL

abstract class CineEditorMLBasescript extends Script {
	// sensor "name" pin n
	def sensor(String name) {
		[pin: { n -> ((CineEditorMLBinding)this.getBinding()).getCineEditorMLModel().createSensor(name, n) },
		onPin: { n -> ((CineEditorMLBinding)this.getBinding()).getCineEditorMLModel().createSensor(name, n)}]
	}
	
	// actuator "name" pin n
	def actuator(String name) {
		[pin: { n -> ((CineEditorMLBinding)this.getBinding()).getCineEditorMLModel().createActuator(name, n) }]
	}

	def buzzer(String name) {
		[pin: { n -> ((CineEditorMLBinding)this.getBinding()).getCineEditorMLModel().createBuzzer(name, n) }]
	}
	
	// state "name" means actuator becomes signal [and actuator becomes signal]*n
	def state(String name) {
		List<Action> actions = new ArrayList<Action>()
		((CineEditorMLBinding) this.getBinding()).getCineEditorMLModel().createState(name, actions)
		// recursive closure to allow multiple and statements
		def closure
		closure = { actuator ->
			[becomes: { signal ->
				DigitalAction action = new DigitalAction()
				action.setActuator(actuator instanceof String ? (Actuator)((CineEditorMLBinding)this.getBinding()).getVariable(actuator) : (Actuator)actuator)
				action.setSignal(signal instanceof String ? (SIGNAL)((CineEditorMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal)
				actions.add(action)
				[and: closure]
			},
			 plays: { note ->
				ToneAction action = new ToneAction()
				action.setActuator(actuator instanceof String ? (Actuator)((CineEditorMLBinding)this.getBinding()).getVariable(actuator) : (Actuator)actuator)
				action.setNote(note instanceof String ? (NOTE)((CineEditorMLBinding)this.getBinding()).getVariable(note) : (NOTE)note)
				actions.add(action)
				[and: closure,
				for : { duration ->
					[duration : { time ->
						actions.get(actions.size() - 1).setDuration(duration instanceof String ? (DURATION) ((CineEditorMLBinding) this.getBinding()).getVariable(duration) : (DURATION) duration)
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
		((CineEditorMLBinding) this.getBinding()).getCineEditorMLModel().setInitialState(state instanceof String ? (State)((CineEditorMLBinding)this.getBinding()).getVariable(state) : (State)state)
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
				sensors.add(sensor instanceof String ? (Sensor)((CineEditorMLBinding)this.getBinding()).getVariable(sensor) : (Sensor)sensor)
				[and: closureand,
				 or: closureor,
				 xor: closurexor,
				 becomes: { signal ->
					 ((CineEditorMLBinding) this.getBinding()).getCineEditorMLModel().createTransition(
							 state1 instanceof String ? (State)((CineEditorMLBinding)this.getBinding()).getVariable(state1) : (State)state1,
							 state2 instanceof String ? (State)((CineEditorMLBinding)this.getBinding()).getVariable(state2) : (State)state2,
							 sensors,
							 signal instanceof String ? (SIGNAL)((CineEditorMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal,
							 LOGICAL.OR)
				 }]
			}
			closurexor = { sensor ->
				sensors.add(sensor instanceof String ? (Sensor)((CineEditorMLBinding)this.getBinding()).getVariable(sensor) : (Sensor)sensor)
				[and: closureand,
				 or: closureor,
				 xor: closurexor,
				 becomes: { signal ->
					 ((CineEditorMLBinding) this.getBinding()).getCineEditorMLModel().createTransition(
							 state1 instanceof String ? (State)((CineEditorMLBinding)this.getBinding()).getVariable(state1) : (State)state1,
							 state2 instanceof String ? (State)((CineEditorMLBinding)this.getBinding()).getVariable(state2) : (State)state2,
							 sensors,
							 signal instanceof String ? (SIGNAL)((CineEditorMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal,
							 LOGICAL.XOR)
				 }]
			}
			closureand = { sensor ->
				sensors.add(sensor instanceof String ? (Sensor)((CineEditorMLBinding)this.getBinding()).getVariable(sensor) : (Sensor)sensor)
				[and: closureand,
				 or: closureor,
				 xor: closurexor,
				 becomes: { signal ->
					 ((CineEditorMLBinding) this.getBinding()).getCineEditorMLModel().createTransition(
							 state1 instanceof String ? (State)((CineEditorMLBinding)this.getBinding()).getVariable(state1) : (State)state1,
							 state2 instanceof String ? (State)((CineEditorMLBinding)this.getBinding()).getVariable(state2) : (State)state2,
							 sensors,
							 signal instanceof String ? (SIGNAL)((CineEditorMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal,
							 LOGICAL.AND)
				 }]
			}
			[when: closureand]
		}]
	}
	
	// export name
	def export(String name) {
		println(((CineEditorMLBinding) this.getBinding()).getCineEditorMLModel().generateCode(name).toString())
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
