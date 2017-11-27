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
    @Getter private String gpaString;
    @Getter private List<TranscriptItem> items;

    private double gpa = 0;

    public List<TranscriptItem> getItems(){
        return Collections.unmodifiableList(items);
    }

    public int getEnrollmentYear(){
        return studentCode.equals("") ? 0 : Integer.parseInt(studentCode.substring(0,4));
    }

    public double getGpa(){
        if (gpa == 0 && !gpaString.equals("")){
            gpa = Double.parseDouble(gpaString.replace(",", "."));
        }
        return gpa;
    }

    public List<TranscriptItem> filterItems(Predicate<TranscriptItem> filter){
        return items.stream().filter(filter).collect(Collectors.toList());
    }
}