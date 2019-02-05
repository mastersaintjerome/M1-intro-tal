
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
    private final String treillisFileName; // "exemple_treillis.txt"
    private List<Map<Integer,Double>> treillis; 
    
	private Perplexity perplexity = new Perplexity();
	private double[][] alpha;
	private double[][] beta;
	
    
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
        Map<Integer,Double> words = null;
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            String[] parts = line.split(" ",2);
            if("%col".equals(parts[0])){
                int col = Integer.parseInt(parts[1]);
                if(col != currentCol){
                    treillis.add(words);
                    currentCol = col;
                }
            }else{
                words = new HashMap<>();
                words.put(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]));
            }   
        }
        if(words != null){
            treillis.add(words);
        }
    }
    
    public void showTreillis(){
        int i = 0;
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
    
    public double LP0(int W) 
    /*
     *  Return : Calcul et renvoie la probabilitée qu'une phrase commence par un mot W1.
     */
    {
    	return perplexity.P(0, W);
    }
    
    public double LPE(int W, int index) 
    /*
     *  Return : Calcul et renvoie la probabilité d’émission d’avoir le mot W à la position i dans le treillis.
     */
    {
    	return treillis.get(index).get(W);
    }
    
    
    public double argmin(int i, int j) {
    	double min = 0;
    	
    	for(int k = 1; k < perplexity.getBiGramN(); k++) {
    		
    		
    	}
    	
    	return min;
    }
    
    public int w(int i, int j) 
    /*
     * Return : renvoie le mot situé à la position i, alternative j dans la position.
     */
    {
    	return -1; //treillis.get(i);
    }
    
    public void viterbi() {
    	int i = 0, j = 0;
    	int min = 0;
  
    	// Taille du treillis.
    	int T = treillis.size();
    	
    	int N = treillis.get(0).size();
    	
    	alpha = new double[ T ][ N ];
    	beta = new double[ T ][ N ];

		for( j = 1; j <= N; j++ ) {
		    alpha[1][j] = LP0(w(1,j)) + LPE(w(1,j),1);
		    beta[1][j] = 0;
		}
    	
    	for( i = 2; i < T; i++) {
    		
    		N = treillis.get(i).size();
    		
    		for(j = 1; j < N; j++) {
    			//min = argmin{de k=1 a N} alpha[i-1,k] + perplexity.LP(w[i,j]|w[i-1,k]) + LPE(w[i,j],i);
    			//alpha[i][j] = alpha[i-1][min] + perplexity.LP(w(i-1, min), w(i,j)) + LPE(w(i,j),i);
    			beta[i][j] = min;
    	   } 
    	}
    }
}