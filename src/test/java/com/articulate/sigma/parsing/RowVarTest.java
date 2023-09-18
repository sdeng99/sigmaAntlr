package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import com.articulate.sigma.KB;
import com.articulate.sigma.KBmanager;
import com.articulate.sigma.UnitTestBase;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RowVarTest extends UnitTestBase {

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
    public static HashSet<FormulaAST> process(String input) {

        System.out.println("RowVarTest.process(): " + input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        SuokifParser.FileContext fileContext = suokifParser.file();
        SuokifVisitor visitor = new SuokifVisitor();
        visitor.visitFile(fileContext);
        HashMap<Integer,FormulaAST> hm = visitor.result;
        if (visitor.hasPredVar.size() > 0) {
            System.out.println("Error - can't have tests with pred vars in this routine.");
            return null;
        }
        PredVarInst pvi = new PredVarInst(kb);
        Sortals sortals = new Sortals(kb);
        VarTypes vt = new VarTypes(hm.values(),kb);
        vt.findTypes();
        for (FormulaAST f : visitor.rules) {
            //System.out.println("RowVarTest.process(): before winnow");
            //f.printCaches();
            sortals.winnowAllTypes(f);
            //System.out.println("RowVarTest.process(): after winnow");
            //f.printCaches();
        }
        PredVarInst.predVarInstDone = true; // because the test formulas already did it
        RowVar rv = new RowVar(kb);
        HashSet<FormulaAST> rvs = rv.expandRowVar(visitor.hasRowVar);
        //System.out.println("RowVarTest.process(): rvs" + rvs);
        return rvs;
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

        System.out.println("===================== RowVarTest.test1() =====================");
                String input = "\n" +
                "(=>\n" +
                "    (and\n" +
                "        (contraryAttribute @ROW1)\n" +
                "        (identicalListItems\n" +
                "            (ListFn @ROW1)\n" +
                "            (ListFn @ROW2)))\n" +
                "    (contraryAttribute @ROW2))";

        HashSet<FormulaAST> hm = process(input);
        System.out.println("RowVarTest.test1(): one result: " + hm.iterator().next());
        System.out.println("RowVarTest.test1(): result size: " + hm.size());
        System.out.println("RowVarTest.test1(): expected size: " + 49);
        assertEquals(49,hm.size());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void test2() {

        System.out.println("===================== RowVarTest.test2() =====================");
        String input = "(=> (and (minValue part ?ARG ?N) (part @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        String expected = "(=> (and (minValue part ?ARG ?N) (part ?ARGS1 ?ARGS2) (equal ?VAL (ListOrderFn (ListFn_2Fn ?ARGS1 ?ARGS2) ?ARG))) (greaterThan ?VAL ?N))";
        HashSet<FormulaAST> hm = process(input);
        StringBuilder sb = new StringBuilder();
        for (FormulaAST f : hm) {
            f.printCaches();
            sb.append(f.getFormula() + "\n");
        }
        System.out.println("RowVarTest.test2(): result: " + sb);
        assertEquals(expected,sb.toString().trim());
        System.out.println();
    }

    /** ***************************************************************
     */
    @Test
    public void test3() {

        System.out.println("===================== RowVarTest.test3() =====================");
        String input = "(forall (@ROW ?ITEM)\n" +
                "    (equal\n" +
                "        (ListLengthFn\n" +
                "            (ListFn @ROW ?ITEM))\n" +
                "        (SuccessorFn\n" +
                "            (ListLengthFn\n" +
                "                (ListFn @ROW)))))";

        HashSet<FormulaAST> hm = process(input);
        System.out.println("RowVarTest.test3(): result size: " + hm.size());
        System.out.println("RowVarTest.test3(): expected size: " + 7);
        System.out.println("RowVarTest.test3(): result size: " + hm);
        HashSet<String> results = new HashSet<>();
        for (FormulaAST f : hm) {
            results.add(f.toString());
            if (f.getFormula().contains("@")) {
                System.out.println("RowVarTest.test3(): shouldn't contain row variable " + f);
                assertTrue(false);
            }
        }

        String expected = "(forall (?ROW1 ?ROW2 ?ITEM)\n" +
                "  (equal\n" +
                "    (ListLengthFn\n" +
                "      (ListFn_3Fn ?ROW1 ?ROW2 ?ITEM))\n" +
                "    (SuccessorFn\n" +
                "      (ListLengthFn\n" +
                "        (ListFn_2Fn ?ROW1 ?ROW2)))))";
        assertTrue(results.contains(expected));
        System.out.println();
    }
}
