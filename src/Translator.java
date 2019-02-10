
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gaëtan
 */
public class Translator {

    private final List<Triplet<Integer, Integer, Double>> translatorTable;
    private static final String TRANSLATOR_TABLE_FILE_NAME = "table-traduction.txt";
    
    private Tokenize tokenizeFr;
    private Tokenize tokenizeEn;

    public Translator() {
        translatorTable = new ArrayList<>();
    }

    public void createTranslatorTableFile(String enFileName, String frFileName) throws IOException {
        File textEn = new File(enFileName);
        File textFr = new File(frFileName);

        Scanner scnr = null;
        Scanner scnr2 = null;
        BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSLATOR_TABLE_FILE_NAME));
        try {
            scnr = new Scanner(textEn);
            scnr2 = new Scanner(textFr);
        } catch (FileNotFoundException fi) {
            fi.printStackTrace();
        }
        while (scnr.hasNextLine() && scnr2.hasNextLine()) {
            String wordEn = scnr.nextLine();
            String wordFr = scnr2.nextLine();
            /*
            * ToDo Faire la proba
             */
            Double proba = 0.0;
            /*
            * ToDo utiliser le tokenize
            */
            writer.write(wordEn + " " + wordFr + " " + proba);
            writer.newLine();
            writer.flush();
        }
        scnr.close();
        scnr2.close();
        writer.close();
    }
    
    /*
    * Le traducteur anglais vers français
    */
    public void enlighToFrench(String englishSentence){
        
    }
    
    public void setTokenizeFr(String filename) {
    	tokenizeFr = new Tokenize(filename);
    	tokenizeFr.buildTree();
    	
    }
    
    public void setTokenizeEn(String filename) {
    	tokenizeEn = new Tokenize(filename);
    	tokenizeEn.buildTree();
    }
    
}
