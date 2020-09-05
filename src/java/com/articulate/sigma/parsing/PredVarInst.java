package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;

import java.util.Collection;
import java.util.HashSet;

// Instantiate predicate variables
public class PredVarInst {

    private KB kb;
    private VarTypes vt = null;
    public static boolean predVarInstDone = false;
    public static boolean debug = false;

    /** ***************************************************************
     */
    public PredVarInst(KB kbin) {
        kb = kbin;
    }

    /** ***************************************************************
     * Note that if there is more than one predicate variable we have to
     * cycle through all the formulas generated for the first variable
     */
    public HashSet<FormulaAST> processOne(FormulaAST f) {

        vt = new VarTypes(null,kb); // no list of formulas since we'll just pass in one when calling constrainVars() below
        if (debug) System.out.println("PredVarInst.processOne()" + f);
        if (debug) System.out.println("PredVarInst.processOne(): varTypes" + f.varTypes);
        if (debug) System.out.println("PredVarInst.processOne(): f.predVarCache" + f.predVarCache);
        HashSet<FormulaAST> result = new HashSet<>();
        result.add(new FormulaAST(f));
        for (String var : f.predVarCache) {
            if (debug) System.out.println("PredVarInst.processOne(): substituting for var: " + var);
            HashSet<String> types = f.varTypes.get(var);
            HashSet<String> relations = new HashSet<>();
            if (types != null) {
                for (String type : types) {
                    if (debug) System.out.println("PredVarInst.processOne(): var,type: " + var + ":" + type);
                    if (relations.size() == 0)
                        relations.addAll(kb.kbCache.getInstancesForType(type));
                    else
                        relations.retainAll(kb.kbCache.getInstancesForType(type));
                }
            }
            HashSet<FormulaAST> newresult = new HashSet<>();
            for (FormulaAST f2 : result) {
                if (debug) System.out.println("PredVarInst.processOne(): relations: " + relations);
                for (String rel : relations) {
                    FormulaAST fnew = new FormulaAST(f2);
                    fnew = vt.constrainVars(rel, var, fnew);
                    if (debug) System.out.println("PredVarInst.processOne(): substituting: " + rel + " for " + var);
                    if (debug) System.out.println("PredVarInst.processOne(): in formula: " + fnew);
                    fnew.setFormula(fnew.getFormula().replace(var, rel)); // TODO: vulnerable to a match of variable name substrings
                    if (debug) System.out.println("PredVarInst.processOne(): with result: " + fnew);
                    for (HashSet<FormulaAST.RowStruct> frhs : fnew.rowVarStructs.values()) {
                        for (FormulaAST.RowStruct fr : frhs) {
                            if (fr.pred.equals(var)) {  // have to update the row var record to reflect the pred var substitution
                                fr.pred = rel;
                                fr.literal = fr.literal.replace(var, rel);
                            }
                        }
                    }
                    if (debug) System.out.println("PredVarInst.processOne(): rowVarStructs: " + fnew.rowVarStructs);
                    newresult.add(fnew);
                }
            }
            result = newresult;
        }
        return result;
    }

    /** ***************************************************************
     */
    public HashSet<FormulaAST> processAll(Collection<FormulaAST> fs) {

        if (debug) System.out.println("PredVarInst.processAll()");
        HashSet<FormulaAST> result = new HashSet<>();
        for (FormulaAST fast : fs) {
            if (fast.higherOrder || fast.containsNumber) continue;
            result.addAll(processOne(fast));
        }
        predVarInstDone = true;
        return result;
    }
}
