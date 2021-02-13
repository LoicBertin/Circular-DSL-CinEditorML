package main.groovy.cineditorml.dsl

import fr.circular.cineditorml.kernel.behavioral.COLOR
import fr.circular.cineditorml.kernel.behavioral.DurationInstruction
import fr.circular.cineditorml.kernel.behavioral.TextPositionInstruction
import fr.circular.cineditorml.kernel.structural.Clip
import fr.circular.cineditorml.kernel.structural.TextClip
import fr.circular.cineditorml.kernel.structural.VideoClip

abstract class CinEditorMLBasescript extends Script {
	// sensor "name" pin n
	def videoClip(String path) {
		[named: { name -> ((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().createVideoFileClip(name, path) }]
	}

	def subClipOf(String clipName) {
		VideoClip video = (clipName instanceof String ? (VideoClip)((CinEditorMLBinding)this.getBinding()).getVariable(clipName) : (VideoClip)clipName)
		[from: {from ->
			[to: { to ->
				[named: { name ->
					((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().createSubClip(video, from, to, name)
				}]
			}]
		}]
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
		((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().changeName(name)
		ArrayList<Clip> clips = new ArrayList<Clip>();
		def closure
		closure = { clipNamebis ->
			println(clipNamebis)
			Clip clip2 = (clipNamebis instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(clipNamebis) : (Clip)clipNamebis)
			((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().addClip(clip2)
			[then: closure]
		}
		[with : {cName ->
			Clip clip = (cName instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(cName) : (Clip)cName)
			((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().addClip(clip)
			[then: closure]
		}]
	}

	// export name
	def export(String name) {
		[at : { path ->
			println(((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().generateCode(name, path).toString())
		}]
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
