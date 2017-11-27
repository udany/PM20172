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
    @Getter private double gpa=0;
    @Getter private int semester=0;
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
}