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
import static org.junit.Assert.assertTrue;

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
    public static int process(String input) {

        System.out.println("PredVarInstTest Input: " + input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        SuokifParser.FileContext fileContext = suokifParser.file();
        SuokifVisitor visitor = new SuokifVisitor();
        visitor.visitFile(fileContext);
        HashMap<Integer,FormulaAST> hm = visitor.result;
        VarTypes vt = new VarTypes(hm.values(),kb);
        vt.findTypes();
        StringBuilder sb = new StringBuilder();
        FormulaAST f = hm.values().iterator().next();
        f.printCaches();
        Sortals s = new Sortals(kb);
        System.out.println("PredVarInstTest.process(): varTypes: " + f.varTypes);
        s.winnowAllTypes(f);
        System.out.println("PredVarInstTest.process(): varTypes after winnow: " + f.varTypes);
        String form = s.addSortals(f);
        f.setFormula(form);
        PredVarInst pvi = new PredVarInst(kb);
        HashSet<FormulaAST> result = pvi.processOne(f);

        //Formula resultf = new Formula(result);
        if (result.size() < 10)
            System.out.println("PredVarInstTest  Result: " + result);
        else {
            System.out.println("PredVarInstTest  Result too big to show ");
        }
        System.out.println("PredVarInstTest  # formulas : " + result.size());
        /*Formula expectedf = new Formula(expected);
        System.out.println("expected: " +expectedf);
        if (resultf.equals(expectedf))
            System.out.println("Success");
        else
            System.out.println("FAIL");*/
        return result.size();
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

        System.out.println("PredVarInstTest.test1()");
        String input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        int result = process(input);
        assertEquals(262,result);
    }

    /** ***************************************************************
     */
    @Test
    public void test2(){

        System.out.println("PredVarInstTest.test2()");
        String input = "(<=>\n" +
                "    (and\n" +
                "        (instance ?REL TotalValuedRelation)\n" +
                "        (instance ?REL Predicate))\n" +
                "    (exists (?VALENCE)\n" +
                "        (and\n" +
                "            (instance ?REL Relation)\n" +
                "            (valence ?REL ?VALENCE)\n" +
                "            (=>\n" +
                "                (forall (?NUMBER ?ELEMENT ?CLASS)\n" +
                "                    (=>\n" +
                "                        (and\n" +
                "                            (lessThan ?NUMBER ?VALENCE)\n" +
                "                            (domain ?REL ?NUMBER ?CLASS)\n" +
                "                            (equal ?ELEMENT\n" +
                "                                (ListOrderFn\n" +
                "                                    (ListFn @ROW) ?NUMBER)))\n" +
                "                        (instance ?ELEMENT ?CLASS)))\n" +
                "                (exists (?ITEM)\n" +
                "                    (?REL @ROW ?ITEM))))))";
        int result = process(input);
        assertEquals(67,result);
    }

    /** ***************************************************************
     */
    @Test
    public void test3() {

        System.out.println("PredVarInstTest.test3()");
        String input = "\n" +
                "(=>\n" +
                "    (and\n" +
                "        (exhaustiveAttribute ?CLASS @ROW)\n" +
                "        (inList ?ATTR\n" +
                "            (ListFn @ROW)))\n" +
                "    (instance ?ATTR ?CLASS))";
        int result = process(input);
        assertEquals(1,result); // there should be no substitutions
    }

    /** ***************************************************************
     */
    @Test
    public void test4() {

        System.out.println("PredVarInstTest.test4()");
        String input = "(=>\n" +
                "  (and\n" +
                "    (maxValue ?REL ?ARG ?N)\n" +
                "    (?REL @ARGS)\n" +
                "    (equal ?VAL\n" +
                "      (ListOrderFn\n" +
                "        (ListFn @ARGS) ?ARG)))\n" +
                "  (greaterThan ?N ?VAL))";
        int result = process(input);
        assertEquals(262,result);
    }


    /** ***************************************************************
     */
    @Test
    public void test5() {

        System.out.println("PredVarInstTest.test5()");
        String input = "\n" +
                "(=>\n" +
                "    (and\n" +
                "        (instance ?REL1 Predicate)\n" +
                "        (instance ?REL2 Predicate)\n" +
                "        (disjointRelation ?REL1 ?REL2)\n" +
                "        (?REL1 @ROW2))\n" +
                "    (not\n" +
                "        (?REL2 @ROW2)))";
        int result = process(input);
        assertTrue(result > 10000);
    }
}
