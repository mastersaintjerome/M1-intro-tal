
/**
 *
 * @author Gaëtans
 */
final public class Tokenize {

    private Node root = null;
    private Node current = root;
    private int code = 0;
    private Reader rd = null;

    public Tokenize() {
        this.rd = new Reader();
    }

    public Tokenize(String file) {
        this.rd = new Reader(file);
    }

    /*
	 * Construit l'arbre préfix depuis le Reader.
     */
    public void buildTree() {
    	
        while (rd.hasNextLine()) {
        	
            rd.readLine();
            code = rd.getWordCode();
            String word = rd.getWord();

            if ( ! word.isEmpty() ) {
                if (root == null) {
                    root = new Node( word.charAt(0), 0);
                }
                root.addWord(code, word, 0);
            }
        }
    }

    /*
     * return : le code du mot passé en paramettre.
     */
    public int searchWordCode(String word) {
        char c = 0;
        boolean needNewChar = true;
        boolean isFind = false;
        int code = 0;
        //System.out.println(word);
        current = root;

        while ( ! isFind || word.length() < 1) {

            if (needNewChar && word.length() != 0) {
                c = word.charAt(0);
                needNewChar = false;
            }

            if (current != null) {

                code = current.getCode();

                if (current.getChar() == c) {

                    current = this.current.getRightChild();
                    needNewChar = true;

                    if (word.length() <= 1) {
                        isFind = true;
                        needNewChar = false;
                    } else {

                        word = word.substring(1);
                    }
                } else {
                    current = this.current.getLeftChild();
                }

            } else {
                isFind = true;
                code = 0;
            }
        }

        current = root;

        if ( ! isFind ) {
            code = 0;
        }

        return code;
    }

    /*
     * Tokenise une ligne depuis l'entrée standard.
     */
    public void tokenizeInput() {
    	String line;
        while(rd.hasEntryNextLine()) {
        	line = rd.readEntry();
        	if(! line.isEmpty())
        		System.out.println(tokenizeString(line));
        }  
    }

    /*
    * Tokenise une ligne passé en argument et la retourne
     */
    public String tokenizeLine(String line) {
        return tokenizeString(line);
    }

    /*
     * return : renvoie la ligne à tokeniser traitée, afin de la rendre traitable plus facilement.
     */
    private String prepareTokenizeString(String str) {
        str = str.replaceAll("[,:;.!?]", " $0").replaceAll("'", "' ").replaceAll(" -", " ").replaceAll("[\\s][\\s]+", " ").trim();//.replace(" -", " ") dernière modif
        return str;
    }

    /*
     * Tokenise une chaine de mot en une chaine d'entiers correspondants.
     */
    public String tokenizeString(String str) {   	
        StringBuilder strTokenized = new StringBuilder(50);
        str = prepareTokenizeString(str);
        String[] parts = str.split(" ");
        int partsLength = parts.length;
        int temp;
        int i = 0;

        while (i < partsLength) {
            temp = searchWordCode(parts[i]);

            if (temp > 0) {
                int j = i;
                String tempStr = parts[j];
                int tempLast = temp;

                while ((tempLast != 0) && (j + 1 < partsLength)) {
                    j++;
                    tempStr += "_" + parts[j];
                    tempLast = searchWordCode(tempStr);

                    if (tempLast != 0) {
                        temp = tempLast;
                        i = j;
                    }
                }

                strTokenized.append(temp);
                strTokenized.append(" ");
                i++;

            } else {

                while ((temp <= 0) && (i < partsLength)) {
                    temp = searchWordCode(parts[i]);
                    i++;
                }

                strTokenized.append(0);
                strTokenized.append(" ");
            }
        }

        return strTokenized.toString();
    }

    /*
     * Affiche l'arbre préfix.
     */
    public void printTree() {
        System.out.println(root.toString());
    }

    public static void main(String[] args) {
        Tokenize tokenizer;

        if (args.length == 1) {
            //System.out.println(args[0]);
            tokenizer = new Tokenize(args[0]);
        } else {
            tokenizer = new Tokenize();
        }

        tokenizer.buildTree();
        //tokenizer.printTree();
        tokenizer.tokenizeInput();
    }

}
