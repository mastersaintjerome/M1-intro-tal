
public class Noeud {

	private char caractereCourrant;
	private int code;
	private Noeud filsGauche = null;
	private Noeud filsDroit = null;
	
	public Noeud(char cour, int code) {
		this.caractereCourrant = cour;
		this.code = code;
		this.filsDroit = null;
		this.filsGauche = null;
	}
	
	public void setFilsGauche(Noeud fg) {
		this.filsGauche = fg;
	}
	
	public void setFilsDroit(Noeud fd) {
		this.filsDroit = fd;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public char getChar() {
		return this.caractereCourrant;
	}
	
	public Noeud getFilsDroit() {
		return filsDroit;
	}
	
	public Noeud getFilsGauche() {
		return filsGauche;
	}
	
	public boolean hadFilsDroit() {
		return filsDroit != null;
	}
	
	public boolean hadFilsGauche() {
		return filsGauche != null;
	}
	
	public String toString(int tab) {
		
        StringBuilder string = new StringBuilder();
        StringBuilder tabs = new StringBuilder();
        
        for (int i = 0; i < tab; i++)
            tabs.append("\t");

        string.append(tabs.toString() + "- (" + getChar() + " " + getCode() + ": ");
        
        if ( hadFilsDroit() ) {
            string.append("\n" + getFilsDroit().toString(tab + 1));
        } else {
            string.append("\n" + tabs.toString() + "\t- NULL");
        }
        
        if ( hadFilsGauche( )) {
            string.append("\n" + getFilsGauche().toString(tab + 1));
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
    
    public void addWord(int code, String word, int index) {
    	//Si le caractere du noeud est celui chercher
    	if( getChar() == word.charAt(index) ) {
    		//Si le caractere se trouve a la fin du mot
    		if(index == word.length() - 1) {
    			//On modifie le code
    			setCode( code );
    		}else {
    			//Sinon on vas a droite
    			if( ! hadFilsDroit() ) {
    				//Si ne possede pas de fils droit, on le creer
    				setFilsDroit(new Noeud( word.charAt(index + 1) , -1));
    			}
				getFilsDroit().addWord(code, word, index + 1);

    		}
    	}else {
    		//On doit chercher à gauche
    		if( ! hadFilsGauche() ) {
    			setFilsGauche( new Noeud( word.charAt(index), code) );
    		}
    		getFilsGauche().addWord( code, word, index );
    	}
    }
	
}
