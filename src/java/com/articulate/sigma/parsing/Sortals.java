package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;

import java.util.HashMap;
import java.util.HashSet;

public class Sortals {

    private KB kb;

    /** ***************************************************************
     */
    public Sortals(KB kbin) {
        kb = kbin;
    }

    /** ***************************************************************
     */
    public String addSortals(FormulaAST f, HashMap<String,String> types) {

        System.out.println("Sortals.addSortals(): types: " + types);
        StringBuilder result = new StringBuilder();
        if (types.keySet().size() > 0)
            result.append("(=> ");
        if (types.keySet().size() > 1)
            result.append("(and ");
        for (String k : types.keySet()) {
            String v = types.get(k);
            if (v.endsWith("+"))
                result.append("(subclass " + k + " " + v.substring(0,v.length()-1) + ") ");
            else
                result.append("(instance " + k + " " + v + ") ");
        }
        result.deleteCharAt(result.length()-1);
        if (types.keySet().size() > 1)
            result.append(") ");
        result.append(f.getFormula());
        result.append(")");
        System.out.println("Sortals.addSortals(): result: " + result);
        return result.toString();
    }

    /** ***************************************************************
     */
    public String mostSpecificType(HashSet<String> types) {

        if (kb.kbCache.checkDisjoint(kb,types)) {
            System.out.println("Error in Sortals.mostSpecificType(): disjoint type spec: " + types);
            return "";
        }
        return kb.kbCache.getCommonChild(types);
    }

    /** ***************************************************************
     */
    public HashMap<String, String> mostSpecificTypes(HashMap<String, HashSet<String>> vmap) {

        HashMap<String, String> themap = new HashMap<>();
        for (String var : vmap.keySet()) {
            themap.put(var,mostSpecificType(vmap.get(var)));
        }
        return themap;
    }
    /** ***************************************************************
     */
    public HashMap<String, String> removeExplicitTypes(HashMap<String,String> types,
                                                       HashMap<String, String> explicit) {

        HashMap<String, String> result = new HashMap<>();
        for (String var : types.keySet()) {
            String expType = explicit.get(var);
            String type = types.get(var);
            if (expType == null || kb.compareTermDepth(type,expType) > 0)
                result.put(var,type);
        }
        return result;
    }

    /** ***************************************************************
     */
    public String addSortals(FormulaAST f) {

        HashMap<String,String> types = mostSpecificTypes(f.varmap);
        f.specvarmap = types;
        types = removeExplicitTypes(types,f.explicitTypes);
        System.out.println("Sortals.mostSpecificType():types: " + types);
        String result = addSortals(f,types);
        return result;
    }
}
