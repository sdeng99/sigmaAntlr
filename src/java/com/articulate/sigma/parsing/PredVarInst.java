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
     */
    public HashSet<FormulaAST> processOne(FormulaAST f) {

        vt = new VarTypes(null,kb); // no list of formulas since we'll just pass in one when calling constrainVars() below
        if (debug) System.out.println("PredVarInst.processOne()" + f);
        if (debug) System.out.println("PredVarInst.processOne(): varTypes" + f.varTypes);
        HashSet<FormulaAST> result = new HashSet<>();
        for (String var : f.predVarCache) {
            HashSet<String> types = f.varTypes.get(var);
            HashSet<String> relations = new HashSet<>();
            for (String type : types) {
                System.out.println("PredVarInst.processOne(): var,type: " + var + ":" + type);
                if (relations.size() == 0)
                    relations.addAll(kb.kbCache.getInstancesForType(type));
                else
                    relations.retainAll(kb.kbCache.getInstancesForType(type));
            }
            if (debug) System.out.println("PredVarInst.processOne(): relations: " + relations);
            for (String rel : relations) {
                FormulaAST fnew = new FormulaAST(f);
                fnew = vt.constrainVars(rel,var,fnew);
                fnew.setFormula(f.getFormula().replace(var+" ",rel + " "));
                if (debug) System.out.println("PredVarInst.processOne(): substituting: " + rel + " for " + var);
                for (HashSet<FormulaAST.RowStruct> frhs : fnew.rowVarStructs.values()) {
                    for (FormulaAST.RowStruct fr : frhs) {
                        if (fr.pred.equals(var)) {  // have to update the row var record to reflect the pred var substitution
                            fr.pred = rel;
                            fr.literal = fr.literal.replace(var+" ",rel + " ");
                        }
                    }
                }
                if (debug) System.out.println("PredVarInst.processOne(): rowVarStructs: " + fnew.rowVarStructs);
                result.add(fnew);
            }
        }
        return result;
    }

    /** ***************************************************************
     */
    public HashSet<FormulaAST> processAll(Collection<FormulaAST> fs) {

        if (debug) System.out.println("PredVarInst.processAll()");
        HashSet<FormulaAST> result = new HashSet<>();
        for (FormulaAST fast : fs) {
            result.addAll(processOne(fast));
        }
        predVarInstDone = true;
        return result;
    }
}
