package main.groovy.cineditorml.dsl

import fr.circular.cineditorml.kernel.behavioral.COLOR
import fr.circular.cineditorml.kernel.behavioral.DurationInstruction
import fr.circular.cineditorml.kernel.behavioral.TextPositionInstruction
import fr.circular.cineditorml.kernel.structural.Clip
import fr.circular.cineditorml.kernel.structural.TextClip

abstract class CinEditorMLBasescript extends Script {
	// sensor "name" pin n
	def videoClip(String path) {
		[named: { name -> ((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().createVideoFileClip(name, path) }]
	}

	def text(String content) {
		[named: { name ->
			[during: { time ->
				((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().createTextClip(name, content, time)
				[at: { position ->
					TextClip text = (name instanceof String ? (TextClip)((CinEditorMLBinding)this.getBinding()).getVariable(name) : (TextClip)name)
					text.addInstruction(new TextPositionInstruction(position))
				}]
			}]
		} ]
	}

	def backgroundClip(COLOR color) {
		[named: { name ->
			[during: { time ->
				((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createColorClip(name, color, time)
			}]
		}]
	}

	def addText(String textName) {
		[on: { clipName ->
			TextClip text = (textName instanceof String ? (TextClip)((CinEditorMLBinding)this.getBinding()).getVariable(textName) : (TextClip)textName)
			Clip clip = (clipName instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(clipName) : (Clip)clipName)
			((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createMergeClip(clip, text)
		}]
	}

	def makeVideoClip(String name) {
		[with: { cName ->
			ArrayList<Clip> clips = new ArrayList<Clip>();
			Clip clip = (cName instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(cName) : (Clip)cName)
			clips.add(clip)
			def closure
			closure = [{ clipName ->
				clip = (clipName instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(clipName) : (Clip)clipName)
				clips.add(clip)
			}]
			[then: closure]
			((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().concatenateClips(clips, name)
		}]
	}

/*
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
				[then: closureand]
			}
			[when: closureand]
		}]
	}

 */

	// export name
	def export(String name, String path) {
		println(((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().generateCode(name, path).toString())
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
