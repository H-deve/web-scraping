package fr.cda.campingcar.util.Fichier;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FichierImp implements Fichier {

    private String fileName1 = "";

    public FichierImp(String fileName1) {
        this.fileName1 = fileName1;
    }



    public String getFileName1() {
        return fileName1;
    }

    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }



    @Override
    public void readFile() throws IOException {

    }

    @Override
    public void writeFile() throws IOException {

    }


//    public void lineWrite(List<String> lines) throws IOException {
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
//            for (String line : lines) {
//                bw.write(line);
//                bw.newLine();
//            }
//        } catch (IOException e) {
//            throw new IOException("Error writing to the file: " + fileName, e);
//        }
//    }
}
