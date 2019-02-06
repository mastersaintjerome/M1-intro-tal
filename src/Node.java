
final public class Node {

	private char currentChar;
	private int code;
	private Node leftChild = null;
	private Node rightChild = null;
	
	public Node(char cour, int code) {
		this.currentChar = cour;
		this.code = code;
		this.leftChild = null;
		this.rightChild = null;
	}
	
	public void setLeftChild(Node fg) {
		this.leftChild = fg;
	}
	
	public void setRightChild(Node fd) {
		this.rightChild = fd;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public char getChar() {
		return this.currentChar;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	
	public Node getLeftChild() {
		return leftChild;
	}
	
	public boolean hadRightChild() {
		return getRightChild() != null;
	}
	
	public boolean hadLeftChild() {
		return getLeftChild() != null;
	}
	
	public String toString(int tab) 
	/*
	 * Affiche proprement la branche courrante de l'arbre.
	 */
	{
		
        StringBuilder string = new StringBuilder();
        StringBuilder tabs = new StringBuilder();
        
        for (int i = 0; i < tab; i++)
            tabs.append("\t");

        string.append(tabs.toString() + "- (" + getChar() + " " + getCode() + ": ");
        
        if ( hadRightChild() ) {
            string.append("\n" + getRightChild().toString(tab + 1));
        } else {
            string.append("\n" + tabs.toString() + "\t- NULL");
        }
        
        if ( hadLeftChild( )) {
            string.append("\n" + getLeftChild().toString(tab + 1));
        } else {
            string.append("\n" + tabs.toString() + "\t- NULL");
        }

        string.append("\n" + tabs.toString() + ")");
        return string.toString();
    }

    @Override
    public String toString() {
        return toString(0);
    }
    
    public void addWord(int code, String word, int index) 
    /*
     * Ajoute un mot dans l'arbre.
     */
    {
    	//Si le caractere du noeud est celui chercher
    	if( getChar() == word.charAt(index) ) {
    		
    		//Si le caractere se trouve a la fin du mot
    		if(index == word.length() - 1) {
    			
    			//On modifie le code
    			setCode( code );
    		}else {
    			
    			//Sinon on vas a droite
    			if( ! hadRightChild() ) {
    				
    				//Si ne possede pas de fils droit, on le creer
    				setRightChild(new Node( word.charAt(index + 1) , -1));
    			}
    			
    			getRightChild().addWord(code, word, index + 1);

    		}
    	}else {
    		
    		//On doit chercher à gauche
    		if( ! hadLeftChild() ) {
    			
        		setLeftChild( new Node( word.charAt(index), code) );
    		}
    		
    		getLeftChild().addWord( code, word, index );
    	}
    }
	
}
