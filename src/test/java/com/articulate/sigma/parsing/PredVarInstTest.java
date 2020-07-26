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
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class PredVarInstTest {

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
    public static String process(String input, String expected) {

        System.out.println("Input: " + input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        SuokifParser.FileContext fileContext = suokifParser.file();
        SuokifVisitor visitor = new SuokifVisitor();
        HashMap<Integer,FormulaAST> hm = visitor.visitFile(fileContext);
        VarTypes vt = new VarTypes(hm.values(),kb);
        vt.findTypes();
        StringBuilder sb = new StringBuilder();
        FormulaAST f = hm.values().iterator().next();
        f.printCaches();
        Sortals s = new Sortals(kb);
        String form = s.addSortals(f);
        f.setFormula(form);
        PredVarInst pvi = new PredVarInst(kb);
        HashSet<FormulaAST> result = pvi.processOne(f);
        //Formula resultf = new Formula(result);
        System.out.println("Result: " + result);
        /*Formula expectedf = new Formula(expected);
        System.out.println("expected: " +expectedf);
        if (resultf.equals(expectedf))
            System.out.println("Success");
        else
            System.out.println("FAIL");*/
        return null;
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

        System.out.println("test1()");
        String input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        String expected = "(=> " +
                "(and " +
                "(instance ?R Predicate)\n" +
                "    (instance ?ARG PositiveInteger)\n" +
                "    (instance ?N Quantity)\n" +
                "    (instance ?VAL Quantity)) " +
                "(=> " +
                "(and " +
                "(minValue ?R ?ARG ?N) " +
                "(?R @ARGS) " +
                "(equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) " +
                "(greaterThan ?VAL ?N)))";
        String result = process(input,expected);
        assertEquals(new Formula(expected),new Formula(result));
    }

}
