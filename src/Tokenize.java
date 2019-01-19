
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
        while ( ! isFind || word.length() < 1) {
            if ( needNewChar ) {
                //System.out.println("word = " + word);
                c = word.charAt(0);
                needNewChar = false;
            }
            
            if ( current != null ) {
                code = current.getCode();
                //System.out.println("Char = " + Character.toString(c) + " Temp = " + code + " Real char = " + current.getChar());
                if ( current.getChar() == c ) {
                    current = this.current.getFilsDroit();
                    needNewChar = true;                    
                    //System.out.println("Taille : " + word.length());
                    if ( word.length()  <= 1 ) {
                        isFind = true;
                        needNewChar = false;
                    } else {
                        word = word.substring(1);
                        System.out.println(word);
                    }
                } else {
                    current = this.current.getFilsGauche();
                    //System.out.println("word = " + word + "char = " + current.getChar());
                }
            } else {
                //System.out.println("Current = null");
                isFind = true;
                code = 0;
            }
        }
        current = root;
        if( ! isFind )
            code = 0;
        return code;
    }
	
	public void tokenizeInput() {
        String line = rd.readEntry();
        System.out.println(tokenizeStr(line));
        //System.out.println("47007 5904 84392 79990 11 15492 34623 13298 52385 17934 1 1");
    }

	 public String prepareTokenizeStr(String str){
	        str = str.replaceAll("[,:;.!?-]", " $0").replace("'","' ").trim();
	        return str;
	 }
    
    public String tokenizeStr(String str) {
    	
        StringBuilder strTokenized = new StringBuilder(50);
        str = prepareTokenizeStr(str);
        String[] parts = str.split(" ");
        
        for(int z = 0; z < parts.length; z++){
            System.out.println( parts[z] );
        }
        int partsLength = parts.length;
        int temp;
        int i = 0;
        while (i < partsLength) {
            System.out.println(" Start : i = " + i + ", length = " + partsLength);
            //System.out.println("str = " + strTokenized.toString());
            temp = searchWordCode(parts[i]);
            System.out.println("temp = " + temp);
            if (temp > 0) {
                int j = i;
                String tempStr = parts[j];
                int tempLast = temp;
                while ((tempLast != 0) && (j + 1 < partsLength)) {
                    System.out.println(temp);
                    j++;
                    tempStr += "_" + parts[j];
                    tempLast = searchWordCode(tempStr);
                    System.out.println("tempStr " + tempStr + tempLast);
                    if( tempLast != 0 ){
                        temp = tempLast;
                        i = j;
                    }
                }
                System.out.println("temp ok = " + temp);
                strTokenized.append(temp);
                strTokenized.append(" ");
                i++;
            } else {
                while ( (temp <= 0) && (i < partsLength) ){
                    temp = searchWordCode( parts[i] );
                    i++;
                }
                strTokenized.append(0);
                strTokenized.append(" ");
            }
        }
        return strTokenized.toString();
    }
	
	public void afficheArbre() {
		System.out.println( root.toString() );
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
        
		System.out.println("Cher = " + tokenizer.searchWordCode("Cher") + "\n");
        tokenizer.tokenizeInput();
		
		
	}

}
