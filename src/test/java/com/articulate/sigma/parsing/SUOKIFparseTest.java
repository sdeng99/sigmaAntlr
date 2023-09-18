package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import com.articulate.sigma.UnitTestBase;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

public class SUOKIFparseTest  extends UnitTestBase {

    public static HashMap<Integer,FormulaAST> process(String input) {

        System.out.println("process(): input: " + input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        //System.out.println("process(): inputStream: " + inputStream);
        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        //System.out.println("process(): lexer: " + suokifLexer);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        //System.out.println("process(): commonTokenStream: " + commonTokenStream);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        //System.out.println("process(): suokifParser: " + suokifParser);
        SuokifParser.FileContext fileContext = suokifParser.file();
        //System.out.println("process(): fileContext: " + fileContext);
        SuokifVisitor visitor = new SuokifVisitor();
        visitor.visitFile(fileContext);
        //System.out.println("process(): visitor: " + visitor);
        //System.out.println("process(): visitor.result (before processing): " + visitor.result);
        HashMap<Integer,FormulaAST> hm = visitor.result;
        System.out.println("process(): result: " + hm);
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
        if (input.equals(sb.toString().trim()))
            System.out.println("test1(): success!");
        else
            System.out.println("test1(): fail!");
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
        if (input.equals(sb.toString().trim()))
            System.out.println("test2(): success!");
        else
            System.out.println("test2(): fail!");
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
            //f.printCaches();
            sb.append(f.getFormula() + " ");
        }
        System.out.println("result: " + sb);
        if (input.equals(sb.toString().trim()))
            System.out.println("test3(): success!");
        else
            System.out.println("test3(): fail!");
        assertEquals(input,sb.toString().trim());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void predRowUnder() {

        System.out.println("predRowUnder(): ");
        String input = "(=> (and (exhaustiveAttribute ?CLASS ?ROW1) (inList ?ATTR (ListFn_1Fn ?ROW1))) (instance ?ATTR Attribute))";
        HashMap<Integer,FormulaAST> hm = process(input);
        FormulaAST f = hm.values().iterator().next();

        f.printCaches();
        if (0 == f.predVarCache.size())
            System.out.println("predRowUnder(): success!");
        else
            System.out.println("predRowUnder(): fail!");
        assertEquals(0,f.predVarCache.size());

        if (0 == f.rowVarCache.size())
            System.out.println("predRowUnder(): success!");
        else
            System.out.println("predRowUnder(): fail!");
        assertEquals(0,f.rowVarCache.size());
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

        if (0 == f.predVarCache.size())
            System.out.println("predRow(): success!");
        else
            System.out.println("predRow(): fail!");
        assertEquals(0,f.predVarCache.size());
        if (1 == f.rowVarCache.size())
            System.out.println("predRow(): success!");
        else
            System.out.println("predRow(): fail!");
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
        if (0 == f.predVarCache.size())
            System.out.println("withSortals(): success!");
        else
            System.out.println("withSortals(): fail!");
        assertEquals(0,f.predVarCache.size());
        if (1 == f.rowVarCache.size())
            System.out.println("withSortals(): success!");
        else
            System.out.println("withSortals(): fail!");
        assertEquals(1,f.rowVarCache.size());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void hasNumber() {
        System.out.println("hasNumber(): ");
        String input = "(=>\n" +
                "  (instance ?MORNING Morning)\n" +
                "  (exists (?HOUR)\n" +
                "    (and\n" +
                "      (instance ?HOUR\n" +
                "        (HourFn 12 ?DAY))\n" +
                "      (finishes ?HOUR ?MORNING))))";
        HashMap<Integer,FormulaAST> hm = process(input);
        FormulaAST f = hm.values().iterator().next();
        if (f.containsNumber)
            System.out.println("hasNumber(): success!");
        else
            System.out.println("hasNumber(): fail!");
        assertTrue(f.containsNumber);
        System.out.println();
    }
}