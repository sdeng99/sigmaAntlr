package com.articulate.sigma.parsing;

import com.articulate.sigma.IntegrationTestBase;
import com.articulate.sigma.KBmanager;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

public class PreprocessorTest extends IntegrationTestBase {

    /** ***************************************************************
     */
    @Test
    public void test1() {

        long start = System.currentTimeMillis();
        SuokifVisitor sv = new SuokifVisitor();
        sv.parseFile(System.getenv("SIGMA_HOME") + File.separator + "KBs" + File.separator + "Merge.kif");
        Preprocessor pre = new Preprocessor(KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname")));

        sv.hasPredVar.removeAll(sv.multiplePredVar); // remove explosive rules with multiple predicate variables
        sv.rules.removeAll(sv.multiplePredVar);
        sv.hasRowVar.removeAll(sv.multiplePredVar);

        Collection<FormulaAST> rules = pre.preprocess(sv.hasPredVar,sv.hasRowVar,sv.rules);
        HashSet<FormulaAST> result = pre.reparse(rules);
        if (result.size() < 100)
            System.out.println("PreprocessorTest.test1(): " + result);
        else
            System.out.println("PreprocessorTest.test1() results too large to show");
        long end = (System.currentTimeMillis()-start)/1000;
        System.out.println("PreprocessorTest.init(): total preprocess time: " + end + " seconds");
    }

    /** ***************************************************************
     */
    @Test
    public void test2() {

        String input = "(=>\n" +
                "  (and\n" +
                "    (maxValue ?REL ?ARG ?N)\n" +
                "    (?REL @ARGS)\n" +
                "    (equal ?VAL\n" +
                "      (ListOrderFn\n" +
                "        (ListFn @ARGS) ?ARG)))\n" +
                "  (greaterThan ?N ?VAL))";
        SuokifVisitor sv = SuokifVisitor.parseString(input);
        System.out.println("PreprocessorTest.test2(): # rules: " + sv.rules.size());
        Preprocessor pre = new Preprocessor(KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname")));
        System.out.println("PreprocessorTest.test2(): # before preprocess: " + sv.rules.size());
        Collection<FormulaAST> rules = pre.preprocess(sv.hasPredVar,sv.hasRowVar,sv.rules);
        System.out.println("PreprocessorTest.test2(): # after preprocess: " + rules.size());
        HashSet<FormulaAST> result = pre.reparse(rules);
        if (result.size() < 100)
            System.out.println("PreprocessorTest.test2(): " + result);
        else
            System.out.println("PreprocessorTest.test2() results too large to show");
    }
}
