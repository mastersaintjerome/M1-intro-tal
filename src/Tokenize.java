
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
	
	public void construitArbrePrefix() {
		
		while(rd.hasNextLine()) {
			rd.readLine();
			code = rd.getCodeMot();
			String word = rd.getWord();
			if( ! word.isEmpty() ){
				if( root == null )
					root = new Noeud(word.charAt(0), -1);
				
				root.addWord(code, word, 0);
			}
		}	
	}
	
	public int searchWordCode(String word) {
        current = root;
        char c = 0;
        boolean needNewChar = true;
        boolean isFind = false;
        int code = 0;
        while(! isFind) {
            if(needNewChar) {
                c = word.charAt(0);
                needNewChar = false;
            }
            if(current != null) {
                code = current.getCode();
                System.out.println("Char = " + Character.toString(c) + " Temp = " + code + " Real char = " + current.getChar());
                if(current.getChar() == c){
                    current = this.current.getFilsDroit();
                    needNewChar = true;
                    if(word.length() == 0){
                    	isFind = true;
                    	needNewChar = false;
                    }else{
                    	word = word.substring(1);
                    	System.out.println(word);
                    }
                }else{
                    current = this.current.getFilsGauche();
                    System.out.println("word = " + word + "char = " + current.getChar());
                }    
            }else{
                System.out.println("Current = null");
                isFind = true;
            }
        }
        current = root;
        return code;
    }
	
	public void tokenizeInput() {
		String line = rd.readEntry();
		System.out.println(tokenizeStr(line));
	}
	
	public String tokenizeStr(String str) {        
        StringBuilder strTokenized = new StringBuilder(50);
        String[] parts = str.split(" ");
        int partsLength = parts.length;
        int temp;
        int i = 0;
        while (i < partsLength) { 
            temp = searchWordCode(parts[i]);
            System.out.println("temp = "+ temp);
            if(temp > 0){
                int j = i;
                String tempStr = parts[j];
                int tempLast = temp;
                while((temp != 0) && (j+1 < partsLength)){
                    temp = tempLast;
                    j++;
                    tempStr += "_" + parts[j];
                    tempLast = searchWordCode(tempStr);
                }
                strTokenized.append(temp);
                strTokenized.append(" ");
                i++;
            }else{
                do{
                    i++;
                    temp = searchWordCode(parts[i]);
                }while((temp == 0) && (i < partsLength));
                strTokenized.append(0);
                strTokenized.append(" ");
            }
        }
        return strTokenized.toString();
    }
	
	public void afficheArbre() {
		System.out.println(root.toString());
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
		//tokenizer.afficheArbre();
        
		System.out.println("cher = " + tokenizer.searchWordCode("cher"));
        //tokenizer.tokenizeInput();
		
		
	}

}
