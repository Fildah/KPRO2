package utils;

import model.Dokument;
import model.Spis;

import java.io.*;

public class CSVFileHandler {
    private String fileName;

    public CSVFileHandler(String fileName){
        this.fileName = fileName;
    }

    public void uloz (Spis spis) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);
        for(int i = 0; i < spis.velikostSpisu(); i++){
            Dokument dokument = spis.najdiDokument(i);
            out.printf("%s;%s;%s\n", dokument.getCj(), dokument.getVec(), dokument.getDetail());
        }
        out.close();
    }

    public void nacti(Spis spis) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(fileName));
        String radek;
        while ((radek = input.readLine())!=null) {
            String[] str = radek.split(";");
            Dokument d = new Dokument(str[0], str[1], str[2]);
            spis.zarad(d);
        }
        input.close();
    }
}
