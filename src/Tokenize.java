
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
					tmp = current.getFilsDroit();
					if(tmp != null) {
						if(tmp.getChar() == calu) {
							if(code != -1)
								tmp.setCode(code);
							current = tmp;
						}
					}
					tmp = null;
				}else{
					if(current.hadFilsGauche()) {
						
						while (current.hadFilsGauche() && current.getFilsGauche().getChar() != calu) {
							current = current.getFilsGauche();	
						}
						 
						if(! current.hadFilsGauche()) {
							//Si aucun fils gauche
							current.setFilsGauche(new Noeud(calu, code));
						}else {
							//Possede un fils gauche et calu est son caractere
							if(code != -1) {
								current.getFilsGauche().setCode(code);
								current = current.getFilsGauche();
							}
						}
					}
				}
				
				if(this.rd.isEndOfLine()) {
					current = root;
					code = -1;
					System.out.println("Fin de ligne");
				}
			}
		}
		
		
		
	}

}
