package com.worksap.nlp.sudachi;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.worksap.nlp.sudachi.dictionary.DoubleArrayLexicon;
import com.worksap.nlp.sudachi.dictionary.Grammar;
import com.worksap.nlp.sudachi.dictionary.GrammarImpl;
import com.worksap.nlp.sudachi.dictionary.Lexicon;

public class JapaneseDictionary implements Dictionary {

    Grammar grammar;
    Lexicon lexicon;

    JapaneseDictionary() throws IOException {
        FileInputStream istream = new FileInputStream("system.dic");
        FileChannel inputFile = istream.getChannel();
        ByteBuffer bytes
            = inputFile.map(FileChannel.MapMode.READ_ONLY, 0, inputFile.size());
        inputFile.close();

        GrammarImpl grammar = new GrammarImpl(bytes, 0);
        this.grammar = grammar;
        lexicon = new DoubleArrayLexicon(bytes, grammar.storageSize());
    }


    @Override
    public void close() {
        grammar = null;
        lexicon = null;
    }

    @Override
    public Tokenizer create() {
        return new JapaneseTokenizer(grammar, lexicon);
    }
}
