
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Gaëtan
 */
public class Viterbi {
    private List<List<Pair<Integer, Double>>> treillis;
    private Perplexity perplexity = new Perplexity();
    private Map<Integer,List<Pair<Integer, Double>>> translateTable;
    private double[][] alpha;
    private int[][] beta;

    public Viterbi() {
        this.translateTable = new LinkedHashMap<>();
        treillis = new ArrayList<>();
    }

    public void translateTableInitFromFile(String translateTableFileName){	
        File text = new File(translateTableFileName);
        Scanner scnr = null;
        try {
            scnr = new Scanner(text);

        } catch (FileNotFoundException fi) {
            fi.printStackTrace();
        }

        List<Pair<Integer, Double>> frenchProba = new ArrayList<>();
        
        //init 0
        int zero = 0;
        double probaNull = 0.00000001;
        Pair<Integer, Double> wordInit = new Pair<>(zero, probaNull);
        frenchProba.add(wordInit);
        translateTable.put(0, frenchProba);
        
        frenchProba = new ArrayList<>();
        int englishWord = -1;
        int currentWord = -1;
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            if(! line.isEmpty()) {
	           String[] parts = line.split(" ", 3);
	           currentWord = Integer.parseInt(parts[0]);
	           if(!translateTable.containsKey(Integer.parseInt(parts[0]))){
	                if(currentWord != englishWord){
	                    translateTable.put(englishWord, frenchProba);
	                }
	                frenchProba = new ArrayList<>();
	                englishWord = Integer.parseInt(parts[0]);
	            }
	            Pair<Integer, Double> word = new Pair<>(Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
	            frenchProba.add(word);
            }
        }
        if(frenchProba.size() > 0){
            translateTable.put(englishWord, frenchProba);
        }
    }
    
        /*
     * Affiche le treillis.
     */
    public void showTranslateTable() {
        for (Map.Entry<Integer,List<Pair<Integer, Double>>> entry : translateTable.entrySet()) {
            for (Pair<Integer,Double> pair : entry.getValue()) {
                Integer key = pair.getFirst();
                Double value = pair.getSecond();
                System.out.println(entry.getKey() + " " +key + " " + value);
            }
        }
    }
    
    /*
     * Créer un treillis et l'initialise à partir d'un fichier.
     */
    public void initFromFile(String treillisFileName) {
    	
        File text = new File(treillisFileName);
        Scanner scnr = null;
        try {
            scnr = new Scanner(text);

        } catch (FileNotFoundException fi) {
            fi.printStackTrace();
        }

        int currentCol = 0;
        List<Pair<Integer, Double>> words = new ArrayList<>();

        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            String[] parts = line.split(" ", 2);
            if ("%col".equals(parts[0])) {
                int col = Integer.parseInt(parts[1]);
                if (col != currentCol) {
                    Collections.sort(words, Comparator.comparing(p -> +p.getSecond()));
                    treillis.add(words);
                    words = new ArrayList<>();
                    currentCol = col;
                }
            } else {
                Pair<Integer, Double> word = new Pair<>(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]));
                words.add(word);
            }
        }
        if (words.size() != 0) {
            treillis.add(words);
        }
    }

    /*
     * Affiche le treillis.
     */
    public void showTreillis() {
        int i = 0;
        for (List<Pair<Integer, Double>> items : treillis) {
            System.out.println("%col " + i);
            for (Pair<Integer, Double> pair : items) {
                Integer key = pair.getFirst();
                Double value = pair.getSecond();
                System.out.println(key + " " + value);
            }
            i++;
        }
    }

    /*
     * Affiche la meilleure phrase.
     */
    public void showBestSentence() {
        String str = "";
        for (List<Pair<Integer, Double>> items : treillis) {
            Integer key = items.get(0).getFirst();
            str += key + " ";
        }
        System.out.println(str);
    }

    /*
     *  Return : Calcul et renvoie la probabilitée qu'une phrase commence par un mot W1.
     */
    public double LP0(int W) {
        return perplexity.logP(0, W);
    }

    /*
     *  Return : Calcul et renvoie la probabilité d’émission d’avoir le mot W à la position i dans le treillis.
     */
    public double LPE(int W, int index) {
        List<Pair<Integer, Double>> words = treillis.get(index);
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getFirst() == W) {
                return words.get(i).getSecond();
            }
        }
        return 0.0;
    }
    
        /*
     *  Return : Calcul et renvoie la probabilité d’émission d’avoir le mot W à la position i dans le treillis.
     */
    public double LPETranslateTable(int W, int index) {
        List<Pair<Integer, Double>> words = translateTable.get(index);
        for(Pair<Integer, Double> items : words){   
            if (items.getFirst() == W) {
                return items.getSecond();
            }
        }
        return 0.0;
    }

    /*
     * calcul argmin
     */
    public int argmin(int i, int j, int N) {
        double min = 100.0, tempMin = 0.0;
        int indexMin = -1;
        System.out.println("N = " + N);
        
        for (int k = 0; k < N; k++) {
            tempMin = alpha[i - 1][k] + perplexity.logP(w(i - 1, k), w(i, j)) + LPE(w(i, j), i);
            System.out.println("Beta [" + i + "]["+ j +"] " + "= " + tempMin);
            //System.out.println("K = " + k + "; i = " + i + "; j = " + j + "; tempMin = " + tempMin);
            
            if (min > tempMin) {
                System.out.println(" i = " + i + "; j = " + j + "; K = " + k);
                indexMin = k;
                min = tempMin;
            }
        }
        System.out.println("indexMin = " + indexMin);
        return indexMin;
    }
    
        /*
     * calcul argmin
     */
    public int argminTranslateTable(int i, int j, int N, String [] parts) {
        double min = 9999.0, tempMin = 0.0;
        int indexMin = -1;
        //System.out.println("N = " + N);
        
        for (int k = 0; k < N; k++) {
            tempMin = alpha[i - 1][k] 
                    + perplexity.logP(wTranslateTable(Integer.parseInt(parts[i-1]), k), wTranslateTable(Integer.parseInt(parts[i]), j)) 
                    + LPETranslateTable(wTranslateTable(Integer.parseInt(parts[i]), j), Integer.parseInt(parts[i]));
            //System.out.println("Beta [" + i + "]["+ j +"] " + "= " + tempMin);
            //System.out.println("K = " + k + "; i = " + i + "; j = " + j + "; tempMin = " + tempMin);
            //System.out.println(" viol :" + tempMin);
            if (min > tempMin) {
                //System.out.println(" i = " + i + "; j = " + j + "; K = " + k);
                indexMin = k;
                min = tempMin;
            }
        }
        //System.out.println("indexMin = " + indexMin);
        return indexMin;
    }

    /*
     * Return : renvoie le mot situé à la position i, alternative j dans la position.
     */
    public int w(int i, int j) {
        return treillis.get(i).get(j).getFirst();
    }
    
    /*
     * Return : renvoie le mot situé à la position i, alternative j dans la position.
     */
    public int wTranslateTable(int i, int j) {
    	//System.out.println("int i :" +i + "int j : " + j + " w : "+ translateTable.get(i).get(j).getFirst());
        return translateTable.get(i).get(j).getFirst();
    }
    
    /*
    * ToDo finir viterbi Translate table
    */
    public void viterbiTranslateTable(String tokenizeEnglishStr) {
        int min = 0;
        // Taille du treillis.
        String[] parts = tokenizeEnglishStr.split(" ");
        int T = parts.length;
        //System.out.println(Integer.parseInt(parts[0]));
        int word = Integer.parseInt(parts[0]);
        int N = translateTable.get(word).size();
        //System.out.println(N + " popo");
        alpha = new double[T][N];
        beta = new int[T][N];
        
        for(int i = 0; i < T; i++){
            Arrays.fill(beta[i], -1);
        }
        
        for (int j = 0; j < N; j++) {
            alpha[0][j] = LP0(wTranslateTable(Integer.parseInt(parts[0]), j)) 
                    + LPETranslateTable(wTranslateTable(Integer.parseInt(parts[0]), j), Integer.parseInt(parts[0]));
            beta[0][j] = -1;
        }

        for (int i = 1; i < T; i++) {
        	//System.out.println(Integer.parseInt(parts[i]));
    		N = translateTable.get(Integer.parseInt(parts[i])).size();
            alpha[i]=new double[N];
            beta[i]=new int[N];
            for (int j = 0; j < N; j++) {
            	//System.out.println(j + " / " + N);
                min = argminTranslateTable(i, j, translateTable.get(Integer.parseInt(parts[i-1])).size(),parts);
                //System.out.println("aie ouille " + min);
                alpha[i][j] = alpha[i - 1][min] 
                        + perplexity.logP(wTranslateTable(Integer.parseInt(parts[i-1]), min), wTranslateTable(Integer.parseInt(parts[i]), j)) 
                        + LPETranslateTable(wTranslateTable(Integer.parseInt(parts[i]), j), Integer.parseInt(parts[i]));
                //System.out.println("min = " +min);
                beta[i][j] = min;
            }
            //System.out.println("aie");
        }
    }

    public void viterbi() {
        int min = 0;

        // Taille du treillis.
        int T = treillis.size();
        int N = treillis.get(0).size();

        alpha = new double[T][N];
        beta = new int[T][N];
        
        for(int i = 0; i < T; i++){
            Arrays.fill(beta[i], -1);
        }
        
        for (int j = 0; j < N; j++) {
            alpha[0][j] = LP0(w(0, j)) + LPE(w(0, j), 0);
            beta[0][j] = -1;
        }

        for (int i = 1; i < T; i++) {
            N = treillis.get(i).size();
            for (int j = 0; j < N; j++) {
                min = argmin(i, j, treillis.get(i-1).size());
                alpha[i][j] = alpha[i - 1][min] + perplexity.logP(w(i - 1, min), w(i, j)) + LPE(w(i, j), i);
                beta[i][j] = min;
            }
        }
    }

    public void showBacktrackPath() {
        // Taille du treillis.
        int T = treillis.size();
        int N = treillis.get(T-1).size();
        //StringBuilder strBuilder = new StringBuilder(50);
        ArrayList<String> strBuilder = new ArrayList<>();
        int minEntry = beta[T-1][0];
        int minProbaJ = 0;
        for(int j = 1; j < N;j++) {
    		if(alpha[T-1][j] < alpha[T-1][minProbaJ]) {
    			minEntry = beta[T-1][j];
    			minProbaJ = j;
    		}
        }
        
        //System.out.println( "Beta [" + (T-1) + "]["+ minProbaJ +"] " + beta[T-1][minProbaJ]);
        
        //strBuilder.append(w(T-1, minProbaJ)).append(" ");
        strBuilder.add(w(T-1, minProbaJ) + " ");    
        
        for (int i = T-2; i >= 0; i--) {
        	 N = treillis.get(i).size();
            for (int j = 0; j < N; j++) {
            	if(j == minEntry) {
                    //System.out.println( "Beta [" + i + "]["+ j +"] " + beta[i][j]);
                    //strBuilder.append(w(i, j)).append(" ");
                    strBuilder.add(w(i, j) + " "); 
                    minEntry = beta[i][j];
                    break;
            	}
            }
        }
        Collections.reverse(strBuilder);
        System.out.println("Best Path : \n" + strBuilder.toString());
    }
    
    public void showBacktrackPathTranslateTable(String tokenizeEnglishStr) {
        // Taille du treillis.
        String[] parts = tokenizeEnglishStr.split(" ");
        int T = parts.length;
        int N = translateTable.get(Integer.parseInt(parts[T-1])).size();
        ArrayList<String> strBuilder = new ArrayList<>();
        int minEntry = beta[T-1][0];
        int minProbaJ = 0;
        for(int j = 1; j < N;j++) {
    		if(alpha[T-1][j] < alpha[T-1][minProbaJ]) {
    			minEntry = beta[T-1][j];
    			minProbaJ = j;
    		}
        }
        
        //System.out.println( "Beta [" + (T-1) + "]["+ minProbaJ +"] " + beta[T-1][minProbaJ]);
        
        //strBuilder.append(w(T-1, minProbaJ)).append(" ");
        strBuilder.add(wTranslateTable(Integer.parseInt(parts[T-1]), minProbaJ) + " ");    
        
        for (int i = T-2; i >= 0; i--) {
        	 N = translateTable.get(Integer.parseInt(parts[i])).size();
            for (int j = 0; j < N; j++) {
            	if(j == minEntry) {
                    //System.out.println( "Beta [" + i + "]["+ j +"] " + beta[i][j]);
                    //strBuilder.append(w(i, j)).append(" ");
                    strBuilder.add(wTranslateTable(Integer.parseInt(parts[i]), j) + " "); 
                    minEntry = beta[i][j];
                    break;
            	}
            }
        }
        Collections.reverse(strBuilder);
        System.out.println(String.join(" ", strBuilder));
    }

    public static void main(String[] args) {
        Viterbi viterbi = new Viterbi();
        
        if(args.length >= 2){
            if(args[0].equals("-vb")){
                viterbi.initFromFile(args[1]);
                viterbi.viterbi();
                viterbi.showBacktrackPath();
            }else if(args[0].equals("-vtt")){
                viterbi.translateTableInitFromFile(args[1]);//"../table-traduction-30.txt"
                //viterbi.showTranslateTable();
                String tokenizeEnglishStr;
                File file = null;
                if(args.length >= 3) {
                	try {
                		file = new File(args[2]);
                		Scanner sc = new Scanner(file);
                		
                		while(sc.hasNextLine()) {
                			
                			tokenizeEnglishStr = sc.nextLine();
                			//System.out.println(tokenizeEnglishStr + " " + sc.hasNextLine());
                			viterbi.viterbiTranslateTable(tokenizeEnglishStr);
                            viterbi.showBacktrackPathTranslateTable(tokenizeEnglishStr);   
                		}
                		
                		sc.close();
                	}catch(Exception e) {
                		
                	}
                	
                }else {
                    tokenizeEnglishStr = args[2];
                    viterbi.viterbiTranslateTable(tokenizeEnglishStr);
                    viterbi.showBacktrackPathTranslateTable(tokenizeEnglishStr);   
                }
          
            }else if(args[0].equals("-vt")){
                viterbi.initFromFile(args[1]);
                viterbi.showTreillis();
                viterbi.showBestSentence();
            }
        }else{
            viterbi.initFromFile("../exemple_treillis.txt");
            viterbi.viterbi();
            viterbi.showBacktrackPath();
        }
        
        //viterbi.initFromFile("../exemple_treillis.txt");
        //viterbi.showTreillis();
        //viterbi.showBestSentence();
        //viterbi.translateTableInitFromFile("../table-traduction-30.txt");
        //viterbi.showTranslateTable();
        //viterbi.viterbi();
        //viterbi.showBacktrackPath();
        //String tokenizeEnglishStr = "2450 1525 2262 2170";
        //viterbi.viterbiTranslateTable(tokenizeEnglishStr);
        //viterbi.showBacktrackPathTranslateTable(tokenizeEnglishStr);
    }
}
