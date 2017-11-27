package unirio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transcript {
    @Getter private String studentName;
    @Getter private String studentCode;
    @Getter private double gpa;
    @Getter private int semester;
    @Getter private List<TranscriptItem> items;

    public List<TranscriptItem> getItems(){
        return Collections.unmodifiableList(items);
    }

    public int getEnrollmentYear(){
        return studentCode.equals("") ? 0 : Integer.parseInt(studentCode.substring(0,4));
    }

    public List<TranscriptItem> filterItems(Predicate<TranscriptItem> filter){
        return items.stream().filter(filter).collect(Collectors.toList());
    }

    public boolean mustPresentIntegralizationPlan(){
        // Até 2013 o prazo eram 14 semestres com plano a partir do 11o
        // Depois disso viraram 12 com plano a parir do 7o
        return getEnrollmentYear() >= 2014 ? semester >=7 : semester >= 11;
    }

    public int mandatoryClassesTaken(){
        return  filterItems(
                x ->
                        x.isMandatory() &&
                        x.hasStatus(TranscriptItemStatus.Approved, TranscriptItemStatus.Dismissed)
        ).size();
    }

    public int mandatoryClassesLeft(){
        return  TranscriptItem.mandatory.size() - mandatoryClassesTaken();
    }

    public int currentlyEnrolledClasses(){
        return  filterItems(
                x -> x.hasStatus(TranscriptItemStatus.Enrolled)
        ).size();
    }

    public int optionalClassesTaken(){
        return  filterItems(
                x ->
                        !x.isMandatory() &&
                                x.belongsToBSI() &&
                                x.hasStatus(TranscriptItemStatus.Approved, TranscriptItemStatus.Dismissed)
        ).size();
    }

    private static final int optionalClasses = 8;
    public int optionalClassesLeft(){
        return optionalClasses - optionalClassesTaken();
    }

    public int electiveClassesTaken(){
        return  filterItems(
                x ->
                        !x.isMandatory() &&
                        !x.belongsToBSI() &&
                        x.hasStatus(TranscriptItemStatus.Approved, TranscriptItemStatus.Dismissed)
        ).size();
    }
    private static final int electiveClasses = 4;
    public int electiveClassesLeft(){
        return electiveClasses - electiveClassesTaken();
    }


    public int classesLeft(){
        return mandatoryClassesLeft() + optionalClassesLeft() + electiveClassesLeft();
    }

    public boolean canFinishInTime(){
        int maxTime = getEnrollmentYear() >= 2014 ? 12 : 14;
        int minTimeLeft = (int)Math.ceil(classesLeft()/7);

        return (minTimeLeft + semester) <= maxTime;
    }


    public boolean shouldBeExpelled(){
        if (gpa >= 4) return false;

        for (TranscriptItem i : items){
            if (filterItems(x->x.getCode().equals(i.getCode()) && x.hasStatus(TranscriptItemStatus.FailedToAtt, TranscriptItemStatus.Failed)).size() >=4){
                return true;
            }
        }

        return false;
    }
}