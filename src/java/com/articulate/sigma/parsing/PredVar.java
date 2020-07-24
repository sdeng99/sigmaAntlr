package com.articulate.sigma.parsing;

import java.util.HashSet;

public class PredVar {

    HashSet<FormulaAST> formulas = null;

    public PredVar(HashSet<FormulaAST> set) {
        formulas = set;
    }
}
