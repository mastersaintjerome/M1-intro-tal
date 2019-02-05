
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class Viterbi {
    private final String treillisFileName;//"exemple_treillis.txt"
    private List<Map<Integer,Double>> treillis; 
    
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
        Map<Integer,Double> words = new HashMap<>();
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            String[] parts = line.split(" ",2);
            if("%col".equals(parts[0])){
                int col = Integer.parseInt(parts[1]);
                if(col != currentCol){
                    treillis.add(words);
                    words = new HashMap<>();
                    currentCol = col;
                }
            }else{
                words.put(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]));
            }   
        }
        if(words.size() != 0){
            treillis.add(words);
        }
    }
    
    public void showTreillis(){
        int i =0;
        for(Map<Integer,Double> items : treillis){
            System.out.println("%col " + i);
            for(Map.Entry<Integer, Double> entry : items.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
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
