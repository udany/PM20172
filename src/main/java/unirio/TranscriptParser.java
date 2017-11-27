package unirio;

import sun.rmi.runtime.Log;
import util.Logger;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranscriptParser {
    /**
     * Groups:
     * #1 - Code
     * #2 - Name
     * #3 - CR
     * #4 - CH
     * #5 - Grade
     * #6 - Attendance
     * #7 - Status
     */
    private static Pattern itemRegex = Pattern.compile("([A-Z]{3}[0-9]{4})\\s(.*?)\\s([0-9])\\s([0-9]{2})\\s([0-9]{1,2}\\,[0-9]{2})?\\s?([0-9]{1,3}\\,[0-9]{2})\\s([A-Z]{3})");

    private static Pattern studentNameRegex = Pattern.compile("Nome Aluno: ([a-zA-Z\\s]*)\n");
    private static Pattern studentCodeRegex = Pattern.compile("Matrícula: ([0-9]{11})");
    private static Pattern gpaRegex = Pattern.compile("Coeficiente de Rendimento Geral: ([0-9],[0-9]{1,6})");
    private static Pattern semesterRegex = Pattern.compile("Período Atual: ([0-9]{1,2})");


    private String header;
    private String body;

    public Transcript transcript;

    public TranscriptParser(String src){
        try {
            TranscriptReader reader = new TranscriptReader(src);

            /// Returns all text in the first page
            header = reader.readPage(1, false);

            body = reader.read(true);

            Logger.out(header);

            reader.close();

            transcript = parse(header, body);
        }catch (Exception e){
            Logger.out("Error in the parser: "+e.getMessage());
        }
    }

    public static Transcript parse(String header, String body){
        Matcher matcher;
        String name = "", code = "";
        double gpa = 0;
        int semester = 0;

        // Student Name
        matcher = studentNameRegex.matcher(header);
        if (!matcher.find()){
            //Logger.out("No name found, bad luck follows...");
        }else{
            name = matcher.group(1);
        }

        // Student Code
        matcher = studentCodeRegex.matcher(header);
        if (!matcher.find()){
            //Logger.out("No code found, a storm is coming...");
        }else{
            code = matcher.group(1);
        }

        // GPA
        matcher = gpaRegex.matcher(header);
        if (!matcher.find()){
            //Logger.out("No code found, a storm is coming...");
        }else{
            gpa = Double.parseDouble(matcher.group(1).replace(",", "."));
        }

        // GPA
        matcher = semesterRegex.matcher(header);
        if (!matcher.find()){
            //Logger.out("No code found, a storm is coming...");
        }else{
            semester = Integer.parseInt(matcher.group(1));
        }

        // Items
        ArrayList<TranscriptItem> items = new ArrayList<TranscriptItem>();
        matcher = itemRegex.matcher(body);

        while (matcher.find()) {
            TranscriptItem item = TranscriptItem.builder()
                    .code(matcher.group(1))
                    .name(matcher.group(2))
                    .cr(matcher.group(3))
                    .ch(matcher.group(4))
                    .grade(matcher.group(5))
                    .attendance(matcher.group(6))
                    .statusCode(matcher.group(7))
                    .build();

            items.add(item);
        }

        return Transcript.builder()
                .studentName(name)
                .studentCode(code)
                .semester(semester)
                .gpa(gpa)
                .items(items)
                .build();
    }
}
