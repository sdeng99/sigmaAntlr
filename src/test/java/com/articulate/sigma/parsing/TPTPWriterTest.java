package com.articulate.sigma.parsing;

import com.articulate.sigma.KBmanager;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.Normalizer;
import java.util.HashSet;

public class TPTPWriterTest {

    /** ***************************************************************
     */
    @Before
    public void init() {

        long start = System.currentTimeMillis();
        KBmanager.getMgr().initializeOnce();
        long end = (System.currentTimeMillis()-start)/1000;
        System.out.println("PreprocessorTest.init(): total init time: " + end + " seconds");
    }

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

        HashSet<FormulaAST> rules = pre.preprocess(sv.hasPredVar,sv.hasRowVar,sv.rules);
        HashSet<FormulaAST> result = pre.reparse(rules);
        if (result.size() < 100)
            System.out.println("PreprocessorTest.test1(): " + result);
        else
            System.out.println("PreprocessorTest.test1() results too large to show");
        long end = System.currentTimeMillis();
        System.out.println("PreprocessorTest.test1(): total preprocess time: " + ((end-start)/1000) + " seconds");
        TPTPWriter tptpW = new TPTPWriter();
        for (FormulaAST f : result) {
            System.out.println("fof(axiom,kb_" + f.sourceFile + "_" + f.startLine + "," + tptpW.visitSentence(f.parsedFormula) + ").");
        }
        long end2 = System.currentTimeMillis();
        System.out.println("PreprocessorTest.test1(): total write time: " + ((end2-end)/1000)  + " seconds");
    }
}
