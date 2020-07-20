package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class SUOKIFCacheTest {

    public static HashMap<Integer, FormulaAST> process(String input) {

        System.out.println(input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        SuokifParser.FileContext fileContext = suokifParser.file();
        SuokifVisitor visitor = new SuokifVisitor();
        HashMap<Integer,FormulaAST> hm = visitor.visitFile(fileContext);
        return hm;
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

        String input = "(likes John Mary)";

        HashMap<Integer,FormulaAST> hm = process(input);
        StringBuilder sb = new StringBuilder();
        Formula f = hm.values().iterator().next();
        System.out.println("result: " + f.termCache);
        assertEquals("[likes, John, Mary]",f.termCache.toString());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void test2() {

        String input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        HashMap<Integer,FormulaAST> hm = process(input);
        StringBuilder sb = new StringBuilder();
        for (Formula f : hm.values()) {
            sb.append(f.getFormula() + " ");
        }
        System.out.println("result: " + sb);
        assertEquals(input,sb.toString().trim());
        System.out.println();
    }
}
