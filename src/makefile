comp ::
    javac *.java

all :: comp

tokenize :: comp
    java Tokenize "../data_ratp/lexique_ratp_fr.txt" < "../data_ratp/corpus_ratp_fr.txt" > tokenized.corpus_ratp.fr.txt

counter :: comp
    java Counter tokenized.corpus_ratp.fr.txt

viterbi :: comp
    Viterbi -vtt ../data_ratp/table_ratp_en2fr.txt tokenized.corpus_ratp.en.txt
