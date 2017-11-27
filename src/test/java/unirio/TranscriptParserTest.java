package unirio;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class TranscriptParserTest {

    @Test
    public void failsSoftly() throws Exception {
        Transcript t = TranscriptParser.parse("");

        Assert.assertEquals("Empty name", "", t.getStudentName());
        Assert.assertEquals("Empty code", "", t.getStudentCode());
        Assert.assertEquals("Empty list", 0, t.getItems().size());
    }

    @Test
    public void parsesHeader() throws Exception {
        String body = "Universidade Federal do Estado do Rio de Janeiro (UNIRIO)\n" +
                "Data: 27/11/2017\n" +
                "Hora: 03:20\n" +
                "11.02.05.99.33 - Historico escolar com  Calculo CR - Aprovados\n" +
                "Curso: 210 - Sistemas de Informação - Bacharelado - Turno Integral (V/N) - código e-MEC 20065 Versão: 2008/1\n" +
                "Período Atual: 3º Semestre\n" +
                "Matrícula: 20162210010\n" +
                "Nome Aluno: DANIEL OLIVEIRA ANDRADE\n" +
                "Período: 2°. semestre de 2016\n" +
                "Total Geral Créditos/Carga Horária cursados no Período 22 360 Coeficiente de Rendimento Geral: 8,8681";


        Transcript t = TranscriptParser.parse(body);

        Assert.assertEquals("Name read", "DANIEL OLIVEIRA ANDRADE", t.getStudentName());
        Assert.assertEquals("Code read", "20162210010", t.getStudentCode());
        Assert.assertEquals("Semester read", 3, t.getSemester());
        Assert.assertEquals( "GPA read", 8.8681, t.getGpa(), 0.0001);
    }

    @Test
    public void parsesBody() throws Exception {
        String body = "HTD0058 TEORIAS E PRÁTICAS DISCURSIVAS NA ESFERA 3 60 7,20 100,00 APV- Aprovado\n" +
                "ACADÊMICA\n" +
                "TIN0011 TÉCNICAS DE PROGRAMAÇÃO II 5 90 100,00 DIS - Dispensa sem nota\n" +
                "TIN0105 INTRODUÇÃO À LÓGICA COMPUTACIONAL 4 60 9,80 100,00 APV- Aprovado\n" +
                "TIN0106 DESENVOLVIMENTO DE PÁGINAS WEB 4 60 8,90 100,00 APV- Aprovado\n" +
                "TIN0107 TÉCNICAS DE PROGRAMAÇÃO I 5 90 100,00 DIS - Dispensa sem nota";


        Transcript t = TranscriptParser.parse(body);

        Assert.assertEquals("List with 5 items", 5, t.getItems().size());
    }
}
