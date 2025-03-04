package com.articulate.sigma.parsing;

import org.antlr.v4.runtime.*;

public class SuokifApp {

    public static void process(CodePointCharStream inputStream) {

        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        SuokifParser.FileContext fileContext = suokifParser.file();
        SuokifVisitor visitor = new SuokifVisitor();
        visitor.visitFile(fileContext);
    }

    public static void main( String[] args) {

        String input = "(likes John Mary) ; and here's a comment\n";
        System.out.println(input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        process(inputStream);
        System.out.println();

        input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        System.out.println(input);
        inputStream = CharStreams.fromString(input);
        process(inputStream);
        System.out.println();
    }
}
