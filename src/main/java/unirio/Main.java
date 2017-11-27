package unirio;

import util.Logger;

public class Main {
    public static void main(String[] args){
        try {
            TranscriptParser parser = new TranscriptParser("./data/coelho.pdf");
            Transcript t = parser.transcript;

            Logger.out(t.getStudentName()+" - "+t.getStudentCode() + " => " + t.getGpa());

            Logger.out(t.mandatoryClassesTaken()+" obrigatórias feitas, " + t.mandatoryClassesLeft() + " restantes");
            Logger.out(t.optionalClassesTaken()+" optativas feitas, " + t.optionalClassesLeft() + " restantes");
            Logger.out(t.electiveClassesTaken()+" eletivas feitas, " + t.electiveClassesLeft() + " restantes");

            Logger.out(t.classesLeft()+" matérias restantes\n");

            Logger.out(t.currentlyEnrolledClasses()+" matérias em curso");

            SvgPrinter.get().print(t);

        }catch (Exception e){
            Logger.out(e.getMessage());
        }
    }
}
