
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static java.util.Collections.swap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Gaëtans
 */
final public class Perplexity {

    final static public double alpha = 0.01;
    private Map<Integer, Float> probOneGram = new HashMap<Integer, Float>();
    private Map<BiGram, Float> probBiGram = new HashMap<BiGram, Float>();
    private List<List<String>> sentencePermutations = new ArrayList<>();

    final private OneGram oneGram = new OneGram();
    final private BiGramList biGramList = new BiGramList();

    public Perplexity() {
        oneGram.initFromFile();
        biGramList.initFromFile();
    }

    public OneGram getOneGram() {
        return oneGram;
    }

    /*
     * Return : Renvois le nombre des occurences des mots du 1-gram lu.
     */
    public int getOneGramN() {
        return oneGram.getN();
    }

    /*
     * Return : Renvois le nombre de mots différents du 1-gram lu. 
     */
    public int getOneGramV() {
        return oneGram.getV();
    }

    /*
     * Return : Renvois la liste des bi-grams lus.
     */
    public BiGramList getBiGramList() {
        return biGramList;
    }

     /*
     * Return : Renvois le nombre de bi-grams lus.
     */ 
    public int getBiGramN(){
        return biGramList.getN();
    }
    
    /*
     * Return : Renvois le nombres de bi-grams différents lus.
     */ 
    public int getBiGramV() {
        return biGramList.getV();
    }
    
    /*
     * Return : Renvois le nombre d'occurences du mot key.
     */ 
    public int C(int key) {
        return oneGram.getNumberOf(key);
    }
    
    /*
     * Return : Renvois le nombre d'occurences du bugram composé des 2 clés.
     */ 
    public int C(int key1, int key2) {
        return biGramList.getNumberOf(key1, key2);
    }
    
    /*
     * Return : Calcul et renvois la probabilitée d'un 1-gram donné.
     */ 
    public float P(int Wi){
        return (float) ((C(Wi) + alpha) / (oneGram.getN() + (oneGram.getV() * alpha)));
    }

    /*
     * Return : Calcul et renvois la probabilitée d'un bigram donné.
     */
    public float P(int Wi, int Wj) {
        return (float) ((C(Wi, Wj) + alpha) / (oneGram.getNumberOf(Wi) + (oneGram.getV() * alpha)));
    }

    public float logP(int Wi) {
    	return (float) -Math.log(P(Wi));
    }
    
    public float logP(int Wi, int Wj) {
    	return (float) -Math.log(P(Wi, Wj));
    }
    
    /*
     * Return : Calcul et renvoie la somme des logarithme de toutes les probabilitées des mots en entrée.
     */
    public float logP(int[] T) {
        int k = T.length;

        float result = (float) Math.log(P(0, T[0]));

        for (int i = 1; i < k; i++) {
            result += Math.log(P(T[i], T[i - 1]));
        }

        return result;
    }

    /*
     * Return : Calcul et renvoie le logprob moyen de la chaîne de mots en entrée.
     */
    public float LP(String T) {
        String[] parsedT = T.split(" ");
        int k = parsedT.length;
        int[] convertedT = new int[parsedT.length];
        for (int i = 0; i < k; i++) {
            convertedT[i] = Integer.parseInt(parsedT[i]);
        }
        return ((float) -(1 / k) * logP(convertedT));
    }

    /*
     * Return : Calcul et renvoie la perplexitée de la liste de mots en entrée.
     */
    public float PP(String T) {
        return (float) Math.pow(2, LP(T));
    }
    
    public void sentencePermutation(String separator, String sentence){
        List<String> parts =new ArrayList<> (Arrays.asList(sentence.trim().split(separator)));
        permute(parts,0);
    }
    
    private void permute(List<String> arr, int k){
        for(int i = k; i < arr.size(); i++){
            swap(arr, i, k);
            permute(arr, k+1);
            swap(arr, k, i);
        }
        if (k == arr.size() -1){
            List<String> array = new ArrayList<>(arr);
            sentencePermutations.add(array);
        }
    }
    
    public void showSentencePermutations(){
        for(List<String> sentence : sentencePermutations){
            System.out.println(sentence.toString());
        }
    }
    
    /*
    * Calcul de la meilleur phrase
    */
    public String bestSentence(String sentence){
        sentencePermutation(" ",sentence);
        List<Pair<String,Float>> perplexities = new ArrayList<>();
        for(List<String> sentencePermute : sentencePermutations){
            String line = String.join(" ",sentencePermute);
            float perplexity = PP(line);
            Pair<String,Float> pair = new Pair<>(line,perplexity);
            perplexities.add(pair);
        }
        Collections.sort(perplexities, Comparator.comparing(p -> +p.getSecond()));
        return perplexities.get(0).getFirst();
    }

    public static void main(String[] args) {

        Perplexity perplexity = new Perplexity();
        
        perplexity.getOneGram().printMap();
        perplexity.getBiGramList().printBiGram();
        
        //perplexity.sentencePermutation(" ", "Je vol un avion");
        //perplexity.showSentencePermutations();
        //String sentence = perplexity.bestSentence("Je vol un avion");
        //System.out.println(sentence);
    }
}
