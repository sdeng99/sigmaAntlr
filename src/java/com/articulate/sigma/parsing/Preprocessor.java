package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;

import java.util.HashSet;

// call the functions needed to make SUMO syntactically first order
// and ready for conversion to TPTP
public class Preprocessor {

    public static KB kb = null;

    /** ***************************************************************
     */
    public Preprocessor(KB kbin) {kb = kbin;}

    /** ***************************************************************
     */
    public static void preprocess(HashSet<FormulaAST> rowvar,
                                  HashSet<FormulaAST> predvar,
                                  HashSet<FormulaAST> rules) { // includes rowvar and predvar

        PredVarInst pvi = new PredVarInst(kb);
        Sortals sortals = new Sortals(kb);
        RowVar rv = new RowVar(kb);
        for (FormulaAST f : rules)
            sortals.winnowAllTypes(f);
        pvi.processAll(predvar);
        rv.expandRowVar(rowvar);
    }
}
