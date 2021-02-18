package main.groovy.cineditorml.dsl

import fr.circular.cineditorml.kernel.behavioral.COLOR
import fr.circular.cineditorml.kernel.behavioral.DurationInstruction
import fr.circular.cineditorml.kernel.behavioral.PositionInstruction
import fr.circular.cineditorml.kernel.structural.Clip
import fr.circular.cineditorml.kernel.structural.TextClip
import fr.circular.cineditorml.kernel.structural.VideoClip

abstract class CinEditorMLBasescript extends Script {

	def importVideoClip(String path) {
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
			((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createTextClip(name, content)
			[during: { time ->
				TextClip text = (name instanceof String ? (TextClip) ((CinEditorMLBinding) this.getBinding()).getVariable(name) : (TextClip) name)
				text.addInstruction(new DurationInstruction(time));
				[at: { position ->
					text.addInstruction(new PositionInstruction(position))
				}]
			}]
		}]
	}

	def createClip(String name) {
		[during: { time ->
			[with_background: {background ->
				String c = ("background".concat(name))
				((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createColorClip(c, (COLOR)background, time)
				Clip clip = (c instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(c) : (Clip)c)
				[with_text: { content ->
					String t = ("text".concat(name))
					((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createTextClip(t, content)
					Clip text = (t instanceof String ? (Clip) ((CinEditorMLBinding) this.getBinding()).getVariable(t) : (Clip) t)
					[at: { position ->
						text.addInstruction(new PositionInstruction(position))
						[from: { from ->
							[to: { to ->
								String textClipName = "transparent".concat(name)
								time = to - from
								((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createTemporalTextClipWithTransparentBackground(text, from, to, position, name)
								text.addInstruction(new DurationInstruction(time))
								text = (textClipName instanceof String ? (Clip) ((CinEditorMLBinding) this.getBinding()).getVariable(textClipName) : (Clip) textClipName)
								((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createMergeClip(clip, text, name)
							}]
						},
						with_same_duration_as_background: { bool ->
							text.addInstruction(new DurationInstruction(time))
							((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createMergeClip(clip, text, name)
						}]
					}]
				}]
			}]
		},
		with_background: {background ->
			Clip clip = (background instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(background) : (Clip)background)
			[with_text: { content ->
				String t = ("text".concat(name))
				((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createTextClip(t, content)
				Clip text = (t instanceof String ? (Clip) ((CinEditorMLBinding) this.getBinding()).getVariable(t) : (Clip) t)
				[at: { position ->
					text.addInstruction(new PositionInstruction(position))
					[from: { from ->
						[to: { to ->
							String textClipName = "concatenated".concat(name)
							int time = to - from
							((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createTemporalTextClipWithTransparentBackground(text, from, to, position, name)
							text.addInstruction(new DurationInstruction(time));
							text = (textClipName instanceof String ? (Clip) ((CinEditorMLBinding) this.getBinding()).getVariable(textClipName) : (Clip) textClipName)
							((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createMergeClip(clip, text, name)
						}]
					}]
				}]
			}]
		}]

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
			((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createMergeClip(clip, text, clip.getName())
		}]
	}

	def makeVideoClip(String name) {
		((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().changeName(name)
		ArrayList<Clip> clips = new ArrayList<Clip>();
		def closure
		closure = { clipNamebis ->
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
