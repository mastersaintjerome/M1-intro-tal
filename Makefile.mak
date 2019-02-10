compilation : javac *.java

perplexity : cat ../exemple.fr.txt | java Tokenize ../lexique.txt.fr | java Perplexity

counter : java Counter "$(cat ../exemple.fr.txt | java Tokenize ../lexique.fr.txt)"
