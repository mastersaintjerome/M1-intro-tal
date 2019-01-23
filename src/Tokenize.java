
final public class Tokenize {
		
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
                c = word.charAt(0);
                needNewChar = false;
            }
            
            if ( current != null ) {
                code = current.getCode();
                if ( current.getChar() == c ) {
                    current = this.current.getFilsDroit();
                    needNewChar = true;                    
                    if ( word.length()  <= 1 ) {
                        isFind = true;
                        needNewChar = false;
                    } else {
                        word = word.substring(1);
                    }
                } else {
                    current = this.current.getFilsGauche();
                }
            } else {
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
    }

	 public String prepareTokenizeStr(String str){
	        str = str.replaceAll("[,:;.!?-]", " $0").replace("'","' ").trim();
	        return str;
	 }
    
    public String tokenizeStr(String str) {
    	
        StringBuilder strTokenized = new StringBuilder(50);
        str = prepareTokenizeStr(str);
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
                    if( tempLast != 0 ){
                        temp = tempLast;
                        i = j;
                    }
                }
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
			//System.out.println(args[0]);
			tokenizer = new Tokenize(args[0]);
		}else {
			tokenizer = new Tokenize();
		}
		
		tokenizer.construitArbrePrefix();
		//tokenizer.afficheArbre();
        tokenizer.tokenizeInput();
		
		
	}

}
