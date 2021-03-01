package main.groovy.cineditorml.dsl

import fr.circular.cineditorml.kernel.behavioral.COLOR
import fr.circular.cineditorml.kernel.behavioral.POSITION
import fr.circular.cineditorml.kernel.behavioral.ANIMATION
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

class CinEditorMLDSL {
	private GroovyShell shell
	private CompilerConfiguration configuration
	private CinEditorMLBinding binding
	private CinEditorMLBasescript basescript

	CinEditorMLDSL() {
		binding = new CinEditorMLBinding()
		binding.setCinEditorMLModel(new CinEditorMLModel(binding));
		configuration = getDSLConfiguration()
		configuration.setScriptBaseClass("main.groovy.cineditorml.dsl.CinEditorMLBasescript")
		shell = new GroovyShell(configuration)
		
		binding.setVariable("CENTER", POSITION.CENTER)
		binding.setVariable("TOP", POSITION.TOP)
		binding.setVariable("BOTTOM", POSITION.BOTTOM)
		binding.setVariable("LEFT", POSITION.LEFT)
		binding.setVariable("RIGHT", POSITION.RIGHT)

		binding.setVariable("RED",   COLOR.RED)
		binding.setVariable("GREEN", COLOR.GREEN)
		binding.setVariable("BLUE",  COLOR.BLUE)
		binding.setVariable("BLACK", COLOR.BLACK)
		binding.setVariable("WHITE", COLOR.WHITE)

		binding.setVariable("VORTEX", ANIMATION.VORTEX)
		binding.setVariable("VORTEXOUT", ANIMATION.VORTEXOUT)
		binding.setVariable("CASCADE", ANIMATION.CASCADE)
		binding.setVariable("ARRIVE", ANIMATION.ARRIVE)

	}
	
	private static CompilerConfiguration getDSLConfiguration() {
		def secure = new SecureASTCustomizer()
		secure.with {
			//disallow closure creation
			closuresAllowed = false
			//disallow method definitions
			methodDefinitionAllowed = true
			//empty white list => forbid imports
			importsWhitelist = [
				'java.lang.*'
			]
			staticImportsWhitelist = []
			staticStarImportsWhitelist= []
			//language tokens disallowed
//			tokensBlacklist= []
			//language tokens allowed
			tokensWhitelist= []
			//types allowed to be used  (including primitive types)
			constantTypesClassesWhiteList= [
				int, Integer, Number, Integer.TYPE, String, Object
			]
			//classes who are allowed to be receivers of method calls
			receiversClassesWhiteList= [
				int, Number, Integer, String, Object
			]
		}
		
		def configuration = new CompilerConfiguration()
		configuration.addCompilationCustomizers(secure)
		
		return configuration
	}
	
	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)
		
		binding.setScript(script)
		script.setBinding(binding)
		
		script.run()
	}
}
