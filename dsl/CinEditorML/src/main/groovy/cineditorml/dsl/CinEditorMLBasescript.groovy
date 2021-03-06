package main.groovy.cineditorml.dsl

import fr.circular.cineditorml.kernel.behavioral.COLOR
import fr.circular.cineditorml.kernel.behavioral.DurationInstruction
import fr.circular.cineditorml.kernel.behavioral.AnimationInstruction
import fr.circular.cineditorml.kernel.behavioral.POSITION
import fr.circular.cineditorml.kernel.behavioral.PositionInstruction
import fr.circular.cineditorml.kernel.behavioral.Subtitle
import fr.circular.cineditorml.kernel.structural.Clip
import fr.circular.cineditorml.kernel.structural.CreditsClip
import fr.circular.cineditorml.kernel.structural.SubtitleClip
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
					[animated_with: { animation ->
						text.addInstruction(new AnimationInstruction(animation))
					}]
				}]
			}]
		}]
	}

	def makeCreditsWith(String content) {
		[named: { name ->
			((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createCreditsClip(name, content)
			[at_speed: { speed ->
				CreditsClip credits = (name instanceof String ? (CreditsClip) ((CinEditorMLBinding) this.getBinding()).getVariable(name) : (CreditsClip) name)
				credits.setSpeed(speed);
			}]
		}]
	}

	def createClip(String name) {
		def closure_text
		closure_text = { content ->
				SubtitleClip subtitleClip = (name instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(name) : (Clip)name)
				[at: { position ->
					[from: { from ->
						[to: { to ->
							((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().addSubtitle(from, to, position, content, subtitleClip)
							[and_with_text: closure_text]
						}]
					}]
				}]
		}
		[during: { time ->
			[with_background: {background ->
				String c = ("background".concat(name))
				((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createColorClip(c, (COLOR)background, time)
				Clip clip = (c instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(c) : (Clip)c)
				[with_text: { content ->
					((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().initSubtitleClip(name, clip)
					SubtitleClip subtitleClip = (name instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(name) : (Clip)name)
					[at: { position ->
						[from: { from ->
							if (from > 0) {
								// create subtitle de 0 à from
								((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().addSubtitle(0, from, POSITION.BOTTOM, " ", subtitleClip)
							}
							[to: { to ->
								((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().addSubtitle(from, to, position, content, subtitleClip)
								[and_with_text: closure_text]
							}]
						}]
					}]
				}]
			}]
		},
		with_background: {background ->
			Clip clip = (background instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(background) : (Clip)background)
			((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().initSubtitleClip(name, clip)
			[with_text: { content ->
				SubtitleClip subtitleClip = (name instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(name) : (Clip)name)
				[at: { position ->
					[from: { from ->
						if (from > 0) {
							// create subtitle de 0 à from
							((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().addSubtitle(0, from, POSITION.BOTTOM, " ", subtitleClip)
						}
						[to: { to ->
							((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().addSubtitle(from, to, position, content, subtitleClip)
							[and_with_text: closure_text]
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
		def closure
		closure = { clipName ->
			TextClip text = null
			try {
				text = (textName instanceof String ? (TextClip)((CinEditorMLBinding)this.getBinding()).getVariable(textName) : (TextClip)textName)
			}catch(MissingPropertyException e){

			}
			if(text != null){
				Clip clip = (clipName instanceof String ? (Clip)((CinEditorMLBinding)this.getBinding()).getVariable(clipName) : (Clip)clipName)
				((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().createMergeClip(clip, text, clip.getName())
			}else{
				Clip clip = (clipName instanceof String ? ((CinEditorMLBinding)this.getBinding()).getVariable(clipName) : clipName)
				SubtitleClip clipCasted
				if(clip instanceof SubtitleClip){
					clipCasted = (SubtitleClip)clip
				}else{
					((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().initSubtitleClip(clipName,clip)
					clipCasted = (clipName instanceof String ? (SubtitleClip)((CinEditorMLBinding)this.getBinding()).getVariable(clipName) : (SubtitleClip)clipName)
				}
				[backward: { time ->
					((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().addSubtitle(time,9999,POSITION.BOTTOM,textName,clipCasted)
					[and_on: closure]
				},
				 during: { time ->
					 ((CinEditorMLBinding)this.getBinding()).getCinEditorMLModel().addSubtitle(0,time,POSITION.BOTTOM,textName,clipCasted)
					 [and_on: closure]
				 },
				 from: { from ->
					 [to: { to ->
						 ((CinEditorMLBinding) this.getBinding()).getCinEditorMLModel().addSubtitle(from, to, POSITION.BOTTOM, textName, clipCasted)
						 [and_on: closure]
					 }]
				 }]
			}
		}
		[on: closure]
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
