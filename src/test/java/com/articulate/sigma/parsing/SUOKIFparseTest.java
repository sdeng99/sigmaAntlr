package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.HashMap;

public class SUOKIFparseTest {

    public static HashMap<Integer,FormulaAST> process(String input) {

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

        System.out.println("test1(): ");
        String input = "(likes John Mary)\n; and here's a comment\n(part Wheel1 Car2)\n";

        HashMap<Integer,FormulaAST> hm = process(input);
        StringBuilder sb = new StringBuilder();
        for (Formula f : hm.values()) {
            f.printCaches();
            sb.append(f.getFormula() + "\n");
        }
        System.out.println("result: " + sb);
        assertEquals(input,sb.toString());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void test2() {

        System.out.println("test2(): ");
        String input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        HashMap<Integer,FormulaAST> hm = process(input);
        StringBuilder sb = new StringBuilder();
        for (Formula f : hm.values()) {
            f.printCaches();
            sb.append(f.getFormula() + " ");
        }
        System.out.println("result: " + sb);
        assertEquals(input,sb.toString().trim());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void test3() {

        System.out.println("test3(): ");
        String input = "(=> (and (exhaustiveAttribute ?CLASS @ROW) (inList NonFullyFormed (ListFn @ROW))) (instance NonFullyFormed Attribute))";
        HashMap<Integer,FormulaAST> hm = process(input);
        StringBuilder sb = new StringBuilder();
        for (Formula f : hm.values()) {
            f.printCaches();
            sb.append(f.getFormula() + " ");
        }
        System.out.println("result: " + sb);
        assertEquals(input,sb.toString().trim());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void predRowUnder() {

        System.out.println("predRowUnder(): ");
        String input = "(=> (and (exhaustiveAttribute ?CLASS ?ROW1) (inList ?ATTR (ListFn_1 ?ROW1))) (instance ?ATTR Attribute))";
        HashMap<Integer,FormulaAST> hm = process(input);
        FormulaAST f = hm.values().iterator().next();

        f.printCaches();
        assertEquals(0,f.predVarCache.size());
        assertEquals(1,f.rowVarCache.size());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void predRow() {

        System.out.println("predRow(): ");
        String input = "(=> (and (exhaustiveAttribute ?CLASS @ROW) (inList ?ATTR (ListFn @ROW))) (instance ?ATTR Attribute))";
        HashMap<Integer,FormulaAST> hm = process(input);
        FormulaAST f = hm.values().iterator().next();

        f.printCaches();
        assertEquals(0,f.predVarCache.size());
        assertEquals(1,f.rowVarCache.size());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void withSortals() {

        System.out.println("withSortals(): ");
        String input = "(=> (and (instance ?REL Predicate) (instance ?ARG PositiveInteger) (instance ?N Quantity) (instance ?VAL Quantity)) " +
                "(=> (and (maxValue subclass ?ARG ?N) (subclass @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?N ?VAL)))";
        HashMap<Integer,FormulaAST> hm = process(input);
        FormulaAST f = hm.values().iterator().next();

        f.printCaches();
        assertEquals(0,f.predVarCache.size());
        assertEquals(1,f.rowVarCache.size());
        System.out.println();
    }
}