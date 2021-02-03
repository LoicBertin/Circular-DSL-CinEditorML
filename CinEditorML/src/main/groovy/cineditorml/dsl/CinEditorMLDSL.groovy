package main.groovy.cineditorml.dsl;

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

import main.java.circular.cineditorml.kernel.behavioral.*;
import main.java.circular.cineditorml.kernel.structural.*;


class CinEditorMLDSL {
	private GroovyShell shell
	private CompilerConfiguration configuration
	private CinEditorMLBinding binding
	private CinEditorMLBasescript basescript

	CinEditorMLDSL() {
		binding = new CinEditorMLBinding()
		binding.setCinEditorMLModel(new CinEditorMLModel(binding));
		configuration = getDSLConfiguration()
		configuration.setScriptBaseClass("main.groovy.CinEditorML.dsl.CinEditorMLBasescript")
		shell = new GroovyShell(configuration)
		
		binding.setVariable("high", SIGNAL.HIGH)
		binding.setVariable("low", SIGNAL.LOW)

		binding.setVariable("C4", NOTE.C4)
		binding.setVariable("G3", NOTE.G3)
		binding.setVariable("A3", NOTE.A3)
		binding.setVariable("B3", NOTE.B3)
		binding.setVariable("STOP", NOTE.STOP)

		binding.setVariable("short", DURATION.SHORT)
		binding.setVariable("long", DURATION.LONG)
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
