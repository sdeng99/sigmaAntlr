package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import com.articulate.sigma.UnitTestBase;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SUOKIFCacheTest extends UnitTestBase {

    public static HashMap<Integer, FormulaAST> process(String input) {

        System.out.println(input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        SuokifParser.FileContext fileContext = suokifParser.file();
        SuokifVisitor visitor = new SuokifVisitor();
        visitor.visitFile(fileContext);
        HashMap<Integer,FormulaAST> hm = visitor.result;
        return hm;
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

        String input = "(likes John Mary)";

        HashMap<Integer,FormulaAST> hm = process(input);
        Formula f = hm.values().iterator().next();
        f.printCaches();
        System.out.println("termCache: " + f.termCache);
        String expected = "[John, likes, Mary]";
        assertEquals(expected,f.termCache.toString());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void test2() {

        System.out.println("SUOKIFCacheText.test2():");
        String input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        HashMap<Integer,FormulaAST> hm = process(input);
        FormulaAST f = hm.values().iterator().next();
        f.printCaches();
        String expected = "[minValue, ListOrderFn, ListFn, greaterThan]";
        System.out.println("SUOKIFCacheText.test2(): expected term cache: " + expected);
        System.out.println("SUOKIFCacheText.test2(): actual term cache: " + f.termCache.toString());
        assertEquals(expected,f.termCache.toString());
        expected = "[@ARGS]";
        System.out.println("SUOKIFCacheText.test2(): expected row var cache: " + expected);
        System.out.println("SUOKIFCacheText.test2(): actual row var cache: " + f.rowVarCache.toString());
        assertEquals(expected,f.rowVarCache.toString());
        expected = "\tListOrderFn\t1: (ListFn@ARGS), 2: ?ARG, \n";
        StringBuffer sb = new StringBuffer();
        String pred = "ListOrderFn";
        sb.append("\t" + pred + "\t");
        for (Integer i : f.argMap.get(pred).keySet()) {
            sb.append(i + ": ");
            for (SuokifParser.ArgumentContext c : f.argMap.get(pred).get(i)) {
                sb.append(c.getText() + ", ");
            }
        }
        sb.append("\n");
        assertEquals(expected,sb.toString());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void test3() {

        String input = "(=>\n" +
                "    (and\n" +
                "        (attribute ?SYLLABLE Stressed)\n" +
                "        (instance ?WORD Word)\n" +
                "        (part ?SYLLABLE ?WORD))\n" +
                "    (not\n" +
                "        (exists (?SYLLABLE2)\n" +
                "            (and\n" +
                "                (instance ?SYLLABLE2 Syllable)\n" +
                "                (part ?SYLLABLE2 ?WORD)\n" +
                "                (attribute ?SYLLABLE2 Stressed)\n" +
                "                (not\n" +
                "                    (equal ?SYLLABLE2 ?SYLLABLE))))))";
        HashMap<Integer,FormulaAST> hm = process(input);
        StringBuilder sb = new StringBuilder();
        Formula f = hm.values().iterator().next();
        f.printCaches();
        String expected = "[?SYLLABLE2]";
        assertEquals(expected,f.existVarsCache.toString());
        assertEquals(expected,f.quantVarsCache.toString());
        expected = "[?WORD, ?SYLLABLE]";
        assertEquals(expected,f.unquantVarsCache.toString());
        System.out.println();
    }
}
