
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
	
}
