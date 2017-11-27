package unirio;

import util.Logger;

public class Main {
    public static void main(String[] args){
        try {
            String file = args.length > 0 ? args[0] : "./data/daniel.pdf";

            TranscriptParser parser = new TranscriptParser(file);
            Transcript t = parser.transcript;

            Logger.out(t.getStudentName()+" - "+t.getStudentCode() + " => " + t.getGpa());

            if (t.shouldBeExpelled()){
                Logger.out("=DEVE SER JUBILADO=");
            }

            if (t.mustPresentIntegralizationPlan()){
                Logger.out("=DEVE APRESENTAR PLANO DE INTEGRALIZAÇÂO E MANTER CR 5=");
            }

            if (t.currentlyEnrolledClasses() < 3 && t.classesLeft() > 2){
                Logger.out("=ALUNO DEVE CURSAR AO MENOS 3 DISCIPLINAS=");
            }

            if (!t.canFinishInTime()){
                Logger.out("=ALUNO NÃO TEM COMO CONCLUIR EM TEMPO REGULAR=");
            }

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
