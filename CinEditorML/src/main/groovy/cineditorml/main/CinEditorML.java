package main.groovy.cineditorml.main;

import main.groovy.cineditorml.dsl.CinEditorMLDSL;

import java.io.File;


/**
 * This main takes one argument: the path to the Groovy script file to execute.
 * This Groovy script file must follow CinEditorML DSL's rules.
 * 
 * "We've Got A Groovy Thing Goin'"!
 * 
 * @author Thomas Moreau
 */
public class CinEditorML {
	public static void main(String[] args) {
		CinEditorMLDSL dsl = new CinEditorMLDSL();
		if(args.length > 0) {
			dsl.eval(new File(args[0]));
		} else {
			System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
		}
	}
}
