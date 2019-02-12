
import java.io.File;
import java.io.FileNotFoundException;
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
public class Code2Word {
    private int maxCode = 0;
    private String lexicon[]; 
    
    public void read_lexicon(String fileName){
        File text = new File(fileName);
        Scanner scnr = null;
        maxCode = 0;
        try{
                scnr = new Scanner(text);

        }catch(FileNotFoundException fi){
                fi.printStackTrace();
        }
        //Search max code
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            String[] parts = line.split(" ",2);
            int code = Integer.parseInt(parts[0]);
            if(maxCode < code) maxCode = code;
        }
        //Prepare tab
        lexicon = new String[maxCode+1];
        for(int i =0; i < maxCode;i++){
            lexicon[i] = null;
        }
        try{
            scnr = new Scanner(text);

        }catch(FileNotFoundException fi){
            fi.printStackTrace();
        }
        //Create lexicon
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            String[] parts = line.split(" ",2);
            int code = Integer.parseInt(parts[0]);
            String word = parts[1];
            lexicon[code] = word;
        }
    }
            
    public String getStrByCode(int code){
        return lexicon[code];
    }
    
    public static void main(String[] args) {
        Code2Word code2Word = new Code2Word();
        code2Word.read_lexicon("../data_ratp/lexique_ratp_fr.txt");
        String sentence;
        
        if(args.length == 1){
            sentence = args[0];
        }else{
            sentence = "3009 8976 2298 5926";
        }
        
        String[] parts = sentence.split(" ");
        int partsLength = parts.length;
        StringBuilder convertSentence = new StringBuilder(100);
        
        for(int i = 0;i <partsLength;i++){
            convertSentence.append(code2Word.getStrByCode(Integer.parseInt(parts[i])));
            if(i != partsLength-1){
                convertSentence.append(" ");
            }
        }
        
        System.out.println(convertSentence.toString());      
    }
}