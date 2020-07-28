package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashSet;

public class RowVar {

    public KB kb = null;

    /** ***************************************************************
     */
    public RowVar(KB kbin) {kb = kbin;}

    /** ***************************************************************
     */
    public void expandRowVar(FormulaAST f) {

        for (ParserRuleContext prc : f.rowvarLiterals) {

        }
    }

    /** ***************************************************************
     */
    public void expandRowVar(HashSet<FormulaAST> rowvars) {
        for (FormulaAST f : rowvars)
            expandRowVar(f);
    }
}
