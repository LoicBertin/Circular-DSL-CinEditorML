package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.NamedElement;
import fr.circular.cineditorml.kernel.behavioral.Instruction;
import fr.circular.cineditorml.kernel.generator.Visitable;

import java.util.ArrayList;

public abstract class Clip implements NamedElement, Visitable {

    private String name;
    private ArrayList<Instruction> instructions = new ArrayList<Instruction>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public void addInstruction(Instruction instruction){
        this.instructions.add(instruction);
    }

}
