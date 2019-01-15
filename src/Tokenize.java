
public class Tokenize {
		
	private Noeud root = null;
	private Noeud current = root;
	private int code = -1;
	private Reader rd = null;
	
	public Tokenize() {
		this.rd = new Reader();
	}
	
	public Tokenize(String file) {
		this.rd = new Reader(file);
	}
	
	public static void main(String[] args) {

		Tokenize tokenizer;

		if(args.length == 1) {
			System.out.println(args[0]);
			tokenizer = new Tokenize(args[0]);
		}else {
			tokenizer = new Tokenize();
		}
		
		tokenizer.construitArbrePrefix();
		
	}
	
	public void construitArbrePrefix() {
		
		Noeud tmp;
		
		while(this.rd.readChar()) {
			
			if(this.rd.isEndOfLine()) {
				code = this.rd.getCodeMot();
			}
			
			char calu = this.rd.getCalu();
			System.out.println(calu);
			
			if(root == null) {
				root = new Noeud(calu, code);
				current = root;
			}
			else {
				if(current.hadFilsDroit()) {
					if(current.getFilsDroit().getChar() == calu) {
						if(code != -1)
							current.getFilsDroit().setCode(code);
						current = current.getFilsDroit();
					}else {
						if(current.hadFilsGauche()) {
							
							while (current.hadFilsGauche() && current.getFilsGauche().getChar() != calu) {
								current = current.getFilsGauche();	
							}
							 
							if(current.hadFilsGauche()) {
								//Possede un fils gauche et calu est son caractere
								if(code != -1) {
									current.getFilsGauche().setCode(code);
									current = current.getFilsGauche();
								}
							}else {
								//Si aucun fils gauche
								current.setFilsGauche(new Noeud(calu, code));
								current = current.getFilsGauche();
							}
						}else {
							current.setFilsGauche(new Noeud(calu, code));
							current = current.getFilsGauche();
						}
					}
					
				}else{
					current.setFilsDroit(new Noeud(calu, code));
					current = current.getFilsDroit();

				}
				
				if(this.rd.isEndOfLine()) {
					current = root;
					code = -1;
					System.out.println("Fin de ligne");
				}
			}
		}	
	}
	
	public int searchWordCode(String word) {
		current = root;
		char c;
		int code = -1;
		while(word.length() > 0) {
			c = word.charAt(0);
			if(current != null) {
				code = current.getCode();
				if(this.current.getChar() == c){
					current = this.current.getFilsDroit();
				}else{
					current = this.current.getFilsGauche();
				}
				word = word.substring(1);
			}
		}
		current = root;
		return code;
	}
	
	public String tokenizeFile(String FilePath) {
		rd = new Reader(FilePath);

		return null;
	}

}
