package unirio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Builder
public class TranscriptItem {
    @Getter private String code;
    @Getter private String name;
    @Getter private String cr;
    @Getter private String ch;
    @Getter private String grade;
    @Getter private String attendance;
    @Getter private String statusCode;

    public boolean isMandatory(){
        return mandatory.indexOf(code) >= 0;
    }

    public boolean belongsToBSI(){
        return code.contains("TIN");
    }


    public static List<String> mandatory = Arrays.asList(
            //1
            "TIN0107",//TP1
            "TIN0106",//DPW
            "TIN0112",//FSI
            "TIN0108",//OC
            "HTD0058",//TPD
            "TME0101",//Mat

            //2
            "TIN0011",//TP2
            "TIN0013",//AEA
            "TIN0010",//IHC
            "TME0015",//AL
            "TIN0105",//ILC
            "TME0112",//Calc1

            //3
            "TIN0114",//ED1
            "TIN0120",//BD1
            "TIN0116",//SO
            "TIN0109",//EDD
            "TME0113",//Calc2
            "TME0114",//Prob

            //4
            "TIN0168",//ED2
            "TIN0115",//AS
            "TIN0123",//RD1
            "TIN0119",//LFA
            "TME0115",//Stat

            //5
            "TIN0118",//AA
            "TIN0130",//EMP
            "TIN0169",//BD2
            "TIN0126",//RD2
            "TIN0171",//PCS
            "TIN0054",//ACE1

            //6
            "TIN0117",//AF
            "TIN0125",//PCSSGBD
            "TIN0121",//PM
            "TIN0055",//ACE2

            //7
            "TIN0131",//PG1
            "TIN0122",//PSW
            "TIN0056",//ACE3

            //8
            "TIN0133",//PG2
            "TIN0132",//GPI
            "TIN0057" //ACE4
    );
}