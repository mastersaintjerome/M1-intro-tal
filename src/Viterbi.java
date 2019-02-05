
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javafx.util.Pair;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gaëtan
 */
public class Viterbi {
    private final String treillisFileName;//"exemple_treillis.txt"
    private List<List<Pair<Integer,Double>>> treillis; 
    
    public Viterbi(String treillisFileName){
        this.treillisFileName = treillisFileName;
        treillis = new ArrayList<>();
    }
    
    /*
     * Lis un fichier et crée une liste de bi-gram.
     */
    public void initFromFile(){
            File text = new File(treillisFileName);
            Scanner scnr = null;
            try{
                    scnr = new Scanner(text);

            }catch(FileNotFoundException fi){
                    fi.printStackTrace();
            }
        int currentCol = 0;
        List<Pair<Integer,Double>> words = new ArrayList<>();
        
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            String[] parts = line.split(" ",2);
            if("%col".equals(parts[0])){
                int col = Integer.parseInt(parts[1]);
                if(col != currentCol){
                    Collections.sort(words, Comparator.comparing(p -> -p.getValue()));
                    treillis.add(words);
                    words = new ArrayList<>();
                    currentCol = col;
                }
            }else{
                Pair<Integer,Double> word = new Pair<>(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]));
                words.add(word);
            }   
        }
        if(words.size() != 0){
            treillis.add(words);
        }
    }
    
    public void showTreillis(){
        int i =0;
        for(List<Pair<Integer,Double>> items : treillis){
            System.out.println("%col " + i);
            for(Pair<Integer,Double> pair : items) {
                Integer key = pair.getKey();
                Double value = pair.getValue();
                System.out.println(key + " " + value);
            }
            i++;
        }
    }
    
    public static void main(String[] args) {
        Viterbi viterbi = new Viterbi("exemple_treillis.txt");
        viterbi.initFromFile();
        viterbi.showTreillis();
    }
}