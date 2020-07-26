package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import com.articulate.sigma.KB;

import java.util.Collection;
import java.util.HashSet;

public class PredVarInst {

    private KB kb;

    /** ***************************************************************
     */
    public PredVarInst(KB kbin) {
        kb = kbin;
    }

    /** ***************************************************************
     */
    public HashSet<FormulaAST> processOne(FormulaAST f) {

        System.out.println("PredVarInst.processOne()" + f);
        System.out.println("PredVarInst.processOne(): varmap" + f.varmap);
        System.out.println("PredVarInst.processOne(): specvarmap" + f.specvarmap);
        System.out.println("PredVarInst.processOne(): specvarmap" + f.specvarmap);
        HashSet<FormulaAST> result = new HashSet<>();
        String newFormulaStr = f.getFormula();
        for (String var : f.predVarCache) {
            String type = f.specvarmap.get(var);
            System.out.println("PredVarInst.processOne(): var,type: " + var + ":" + type);
            HashSet<String> relations = kb.kbCache.getInstancesForType(type);
            System.out.println("PredVarInst.processOne(): relations: " + relations);
            for (String rel : relations) {
                FormulaAST fnew = new FormulaAST(f);
                fnew.setFormula(f.getFormula().replace(var+" ",rel + " "));
                result.add(fnew);
            }
        }
        return result;
    }

    /** ***************************************************************
     */
    public HashSet<FormulaAST> processAll(Collection<FormulaAST> fs) {

        System.out.println("PredVarInst.processAll()");
        HashSet<FormulaAST> result = new HashSet<>();
        for (FormulaAST fast : fs)
            result.addAll(processOne(fast));
        return result;
    }
}
