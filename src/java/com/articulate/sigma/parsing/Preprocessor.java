package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;

import java.util.HashMap;
import java.util.HashSet;

// call the functions needed to make SUMO syntactically first order
// and ready for conversion to TPTP
public class Preprocessor {

    public static KB kb = null;

    public static boolean debug = false;

    /** ***************************************************************
     */
    public Preprocessor(KB kbin) {kb = kbin;}

    /** ***************************************************************
     */
    public HashSet<FormulaAST> preprocess(HashSet<FormulaAST> rowvar,
                                  HashSet<FormulaAST> predvar,
                                  HashSet<FormulaAST> rules) { // includes rowvar and predvar

        System.out.println("Preprocessor.preprocess()");
        if (debug) System.out.println("Preprocessor.preprocess() # rules: " + rules.size());
        HashSet<FormulaAST> mismatch = new HashSet<>();
        mismatch.addAll(predvar);
        mismatch.removeAll(rowvar);
        if (mismatch.size() > 0)
            System.out.println("Error - Preprocessor.preprocess() predvar statements without rowvar: " + mismatch);
        VarTypes vt = new VarTypes(rules,kb);
        vt.findTypes();
        PredVarInst pvi = new PredVarInst(kb);
        Sortals sortals = new Sortals(kb);
        for (FormulaAST f : rules)
            sortals.elimSubsumedTypes(f);
        HashSet<FormulaAST> pviResults = pvi.processAll(predvar);
        RowVar rv = new RowVar(kb);
        HashSet<FormulaAST> rvResults = rv.expandRowVar(pviResults);
        HashSet<FormulaAST> newRules = new HashSet<>();
        for (FormulaAST r : rules) {
            if (!rowvar.contains(r) && !predvar.contains(r)) // only add rules without pred and row vars
                newRules.add(r);
        }
        // newRules.addAll(pviResults); // now add the new rules expanded from pred vars <- should not be needed
        newRules.addAll(rvResults); // now add the new rules expanded from row vars
        HashSet<FormulaAST> finalRuleSet = new HashSet<>();
        newRules = reparse(newRules);
        for (FormulaAST r : newRules) {
            System.out.println("Preprocessor.preprocess(): add sortals to r: " + r);
            sortals.addSortals(r);
            System.out.println("Preprocessor.preprocess(): result adding sortals to r: " + r);
            SuokifVisitor visitor = SuokifVisitor.parseString(r.getFormula());
            System.out.println("Preprocessor.preprocess(): parsed r: " + visitor.result);
            finalRuleSet.addAll(visitor.result.values());
        }
        return finalRuleSet;
    }

    /** ***************************************************************
     * After preprocessing, parse the new formula string in order to
     * set the caches correctly
     */
    public HashSet<FormulaAST> reparse(HashSet<FormulaAST> rules) {

        System.out.println("Preprocessor.reparse()");
        HashSet<FormulaAST> result = new HashSet<>();
        for (FormulaAST f : rules) {
            System.out.println("Preprocessor.reparse(): " + f);
            SuokifVisitor visitor = SuokifVisitor.parseString(f.getFormula());
            System.out.println("Preprocessor.reparse(): result" + visitor.result);
            if (visitor.result != null && visitor.result.values().size() > 0)
                result.add(visitor.result.values().iterator().next());
        }
        return result;
    }
}
