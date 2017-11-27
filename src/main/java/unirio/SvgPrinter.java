package unirio;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SvgPrinter {
    private SvgPrinter(){}
    private static SvgPrinter instance;
    public static SvgPrinter get(){
        if (instance==null) instance = new SvgPrinter();

        return instance;
    }

    private static final String path = "./data/grade.svg";

    private String getData(String file) throws IOException{
        if (file.equals("")) file = path;

        byte[] encoded = Files.readAllBytes(Paths.get(file));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private void saveData(String data, String file) throws IOException{
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        try{
            writer.write(data);
        }finally {
            writer.close();
        }
    }

    public void print (Transcript t) throws IOException{
        print(t, "");
    }

    public void print (Transcript t, String svgFile) throws IOException{
        String data = getData(svgFile);
        data = data.replaceAll("\\{\\{code}}", getAllCode(t));

        saveData(data, t.getStudentCode()+".svg");
    }



    private static String colorOk = "#48e793";
    private static String colorNay = "#e77148";
    private static String colorCurrent = "#48b0e7";

    private String getItemCode(TranscriptItem i){
        String color = "#fff";
        if(i.hasStatus(TranscriptItemStatus.Approved, TranscriptItemStatus.Dismissed)) color = colorOk;
        if(i.hasStatus(TranscriptItemStatus.Failed, TranscriptItemStatus.FailedToAtt)) color = colorNay;
        if(i.hasStatus(TranscriptItemStatus.Enrolled)) color = colorCurrent;

        return getPaintCode(i.getCode(), color);
    }

    private String getPaintCode(String id, String color){
        return "x = document.getElementById('"+id+"'); if (x) x.style.fill='"+color+"';";
    }

    private String getAllCode(Transcript t){
        StringBuilder sb = new StringBuilder();

        for (TranscriptItem item : t.getItems()){
            sb.append(getItemCode(item));
        }

        int i;

        for (i = 1; i <= t.electiveClassesTaken(); i++){
            sb.append(getPaintCode("ELETIVA_0"+i, colorOk));
        }

        for (i = 1; i <= t.optionalClassesTaken(); i++){
            sb.append(getPaintCode("OPTATIVA_0"+i, colorOk));
        }

        return sb.toString();
    }
}
