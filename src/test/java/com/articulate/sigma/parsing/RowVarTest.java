package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import com.articulate.sigma.KB;
import com.articulate.sigma.KBmanager;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RowVarTest {

    public static KB kb = null;

    /***************************************************************
     * */
    @BeforeClass
    public static void setup()  {

        KBmanager.getMgr().initializeOnce();
        kb = KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname"));
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
    }

    /***************************************************************
     * */
    public static HashMap<Integer,FormulaAST> process(String input) {

        System.out.println(input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        SuokifParser.FileContext fileContext = suokifParser.file();
        SuokifVisitor visitor = new SuokifVisitor();
        HashMap<Integer,FormulaAST> hm = visitor.visitFile(fileContext);
        PredVarInst pvi = new PredVarInst(kb);
        Sortals sortals = new Sortals(kb);
        VarTypes vt = new VarTypes(hm.values(),kb);
        vt.findTypes();
        RowVar rv = new RowVar(kb);
        for (FormulaAST f : visitor.rules) {
            System.out.println("RowVarTest.process(): before winnow");
            f.printCaches();
            sortals.winnowAllTypes(f);
            System.out.println("RowVarTest.process(): after winnow");
            f.printCaches();
        }
        pvi.processAll(visitor.hasPredVar);
        rv.expandRowVar(visitor.hasRowVar);
        return hm;
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

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

        String input = "(=> (and (minValue part ?ARG ?N) (part @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        String expected = "(=> (and (minValue part ?ARG ?N) (part ?ARGS1 ?ARGS2) (equal ?VAL (ListOrderFn (ListFn ?ARGS1 ?ARGS2) ?ARG))) (greaterThan ?VAL ?N))";
        HashMap<Integer,FormulaAST> hm = process(input);
        StringBuilder sb = new StringBuilder();
        for (Formula f : hm.values()) {
            f.printCaches();
            sb.append(f.getFormula() + " ");
        }
        System.out.println("result: " + sb);
        assertEquals(expected,sb.toString().trim());
        System.out.println();
    }
}
