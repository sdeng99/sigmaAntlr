package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import com.articulate.sigma.FormulaPreprocessor;
import com.articulate.sigma.KB;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

// Determine the types of variables by their appearance in relations,
// as well as whether onstants or functions are allowed given their types
public class VarTypes {

    Collection<FormulaAST> formulas = null;
    KB kb = null;

    // a map of variables and the set of types that constrain them
    HashMap<String,HashSet<String>> varTypeMap = new HashMap<>();

    public boolean debug = false;

    /** ***************************************************************
     */
    public VarTypes(Collection<FormulaAST> set, KB kbinput) {
        formulas = set;
        kb = kbinput;
        if (set != null && debug)
            System.out.println("VarTypes(): created with # inputs: " + set.size());
    }

    /** ***************************************************************
     * funterm : '(' FUNWORD argument+ ')' ;
     * argument : (sentence | term) ;
     */
    public String findTypeOfFunterm(SuokifParser.FuntermContext input) {

        if (debug) System.out.println("VarTypes.findTypeOfFunterm(): input: " + input);
        String type = "Entity";
        if (input.FUNWORD() != null) {
            String funword = input.FUNWORD().toString();
            type = kb.kbCache.getRange(funword);
        }
        return type;
    }

    /** ***************************************************************
     * term : (funterm | variable | string | number | FUNWORD | IDENTIFIER ) ;
     */
    public String findTypeOfTerm(SuokifParser.TermContext input, String sigType) {

        if (debug) System.out.println("VarTypes.findTypeOfTerm(): input: " + input);
        if (debug) System.out.println("VarTypes.findTypeOfTerm(): sigType: " + sigType);
        String type = null;
        for (ParseTree c : ((SuokifParser.TermContext) input).children) {
            if (input.IDENTIFIER() != null) {
                String ident = input.IDENTIFIER().getText();
                if (!kb.kbCache.isInstanceOf(ident,sigType))
                    System.out.println("error in findTypeOfTerm(): signature " + sigType + " doesn't allow " + ident);
                else
                    type = kb.immediateParents(ident).iterator().next();
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$FuntermContext"))
                type = findTypeOfFunterm((SuokifParser.FuntermContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                FormulaPreprocessor.addToMap(varTypeMap, c.getText(),sigType);
            }
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$StringContext")) {
                if (!sigType.equals("SymbolicString"))
                    System.out.println("error in findTypeOfTerm(): signature doesn't allow string " + c.getText());
                else
                    type = "SymbolicString";
            }
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$NumberContext")) {
                if (!kb.kbCache.subclassOf("Number",sigType))
                    System.out.println("error in findTypeOfTerm(): signature doesn't allow number " + c.getText());
                else
                    type = "Number";
            }
        }
        return type;
    }

    /** ***************************************************************
     * Go through the equation map of a formula.  If the argument is a variable,
     * add the type to the variable type map (varTypeMap) by finding the
     * type of the other argument
     *
     * eqsent : '(' 'equal' term term ')' ;
     * term : (funterm | variable | string | number | FUNWORD | IDENTIFIER ) ;
     */
    public void findEquationType(FormulaAST f) {

        if (debug) System.out.println("VarTypes.findEquationType(): input: " + f);
        for (ArrayList<SuokifParser.TermContext> pair : f.eqList) {
            String var = null;
            String type = null;
            SuokifParser.TermContext arg1 = pair.get(0);
            SuokifParser.TermContext arg2 = pair.get(1);
            ParseTree child1 = arg1.children.iterator().next();
            ParseTree child2 = arg2.children.iterator().next();
            if (child1.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$FuntermContext")) {
                String funterm = ((SuokifParser.FuntermContext) child1).FUNWORD().toString();
                type = kb.kbCache.getRange(funterm);
            }
            else if (child1.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                var = child1.getText();
            }
            else if (child1.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$StringContext"))
                type = "SymbolicString";
            else if (child1.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$NumberContext"))
                type = "Number";
            else
                System.out.println("Error in findEquationType(): assignment not allowed in " + f);
            if (child2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$FuntermContext")) {
                String funterm = ((SuokifParser.FuntermContext) child2).FUNWORD().toString();
                type = kb.kbCache.getRange(funterm);
            }
            else if (child2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                var = child2.getText();
            }
            else if (child2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$StringContext"))
                type = "SymbolicString";
            else if (child2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$NumberContext"))
                type = "Number";
            else
                System.out.println("Error in findEquationType(): assignment not allowed in " + f);
            if (debug) System.out.println("var&type: " + var + " : " + type);
            FormulaPreprocessor.addToMap(f.varTypes,var, type);
        }
    }

    /** ***************************************************************
     * Constrain variables found in the argument list of a predicate variable
     * where the relation 'rel' will be substituted
     */
    public FormulaAST constrainVars(String rel, String var, FormulaAST f) {

        HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> argsForIndex = f.argMap.get(var);
        ArrayList<String> sig = kb.kbCache.getSignature(rel);
        if (sig == null || argsForIndex == null || argsForIndex.keySet().size() != sig.size()-1) { // signatures have a 0 element for function return type
            StringBuilder sb = new StringBuilder();
            for (Integer i : argsForIndex.keySet()) {
                sb.append(i + " : " );
                for (SuokifParser.ArgumentContext arg : argsForIndex.get(i))
                    sb.append(arg.getText() + ", ");
            }
            System.out.println(sb.toString());
            if (sb.toString().contains("@")) {
                if (debug) System.out.println("Arg mismatch caused by row variable " + argsForIndex.keySet());
            }
            else {
                System.out.println("VarTypes.constrainVars(): mismatched argument type lists:");
                System.out.println("VarTypes.constrainVars():line and file: " + f.sourceFile + " " + f.startLine);
                System.out.println("When substituting " + rel + " for " + var);
                System.out.println("sig " + sig);
                System.out.print("argsForIndex ");
            }
        }
        else {
            for (Integer i : argsForIndex.keySet()) {
                String sigTypeAtIndex = sig.get(i);
                HashSet<SuokifParser.ArgumentContext> args = argsForIndex.get(i);
                for (SuokifParser.ArgumentContext ac : args) {
                    for (ParseTree c : ac.children) {
                        if (debug) System.out.println("child: " + c.getClass().getName());
                        if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                            for (ParseTree c2 : ((SuokifParser.SentenceContext) c).children) {
                                if (c2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$RelsentContext") ||
                                        c2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$LogsentContext") ||
                                        c2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$QuantsentContext"))
                                    f.higherOrder = true;
                                if (c2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext") &&
                                        ((SuokifParser.VariableContext) c2).REGVAR() != null) {
                                    FormulaPreprocessor.addToMap(f.varTypes,c2.getText(), sigTypeAtIndex);
                                }
                            }
                        }
                        if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$TermContext")) {
                            String t = findTypeOfTerm((SuokifParser.TermContext) c, sigTypeAtIndex);
                            if (!sigTypeAtIndex.equals(t) && !kb.isSubclass(t,sigTypeAtIndex))
                                System.out.println("Error in VarTypes.constrainVars(): arg " + c.getText() +
                                        " not allowed as argument " + i + " to relation " + rel + " in formula " + f);
                        }
                    }
                }
            }
        }
        return f;
    }

    /** ***************************************************************
     * @param pred is the relation name
     * @param argType is the type of the argument, with an appended '+' if it's a subclass of a given class
     * @param sigType is the required type of the argument,
     */
    public boolean argTypeOk(String pred, String argType, String sigType) {

        if (!argType.equals(sigType) && !kb.kbCache.subclassOf(argType,sigType))
            return false;
        return true;
    }

    /** ***************************************************************
     * if a relation is used as an argument, add a suffix to that constant
     * in the literal for the constant list.  It will be used later in
     * conversion to TPTP.
     */
    public void findRelationsAsArgs(FormulaAST f) {

        for (String c : f.constants.keySet()) {
            FormulaAST.ArgStruct as = f.constants.get(c);
            if (kb.kbCache.relations.contains(c)) {
                String newc = c + "__m";
                as.literal.replace(" " +  c," " + newc);
            }
        }
    }

    /** ***************************************************************
     * Go through the argument map of a formula, which consists of all
     * predicates and their arguments in each position, within this formula,
     * and find the type of that argument.  If the argument is a variable,
     * add the type to the variable type map (varTypeMap).  If the argument
     * is not a variable, make sure it is allowed as a argument, given the
     * signature of the predicate.  If not, report an error.
     *
     * argument : (sentence | term) ;
     * sentence : (relsent | logsent | quantsent | variable) ;
     * term : (funterm | variable | string | number | FUNWORD | IDENTIFIER ) ;
     */
    public void findType(FormulaAST f) {

        if (debug) System.out.println("VarTypes.findType(): " + f);
        for (String pred : f.argMap.keySet()) {
            if (debug) System.out.println("VarTypes.findType():relation: " + pred);
            HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> argsForIndex = f.argMap.get(pred);
            if (debug) System.out.println("VarTypes.findType(): argsForIndex: " + argsForIndex);
            if (argsForIndex == null || Formula.isVariable(pred))
                continue;
            printContexts(argsForIndex);
            ArrayList<String> sig = kb.kbCache.getSignature(pred);
            if (debug) System.out.println("VarTypes.findType():signature: " + sig);
            if (sig == null) {
                System.out.println("Error in VarTypes.findType(): null signature in formula " + f);
                continue;
            }
            if (kb.kbCache.getArity(pred) != -1 && argsForIndex.keySet().size() != sig.size()-1) { // signatures have a 0 element for function return type
                System.out.println("Error in VarTypes.findType(): mismatched argument type lists:");
                System.out.println("VarTypes.findType():relation: " + pred);
                for (Integer argnum : argsForIndex.keySet())
                    for (SuokifParser.ArgumentContext arg : argsForIndex.get(argnum))
                        System.out.println("VarTypes.findType(): argsForIndex: " + argnum + " : " + arg.getText());
                System.out.println("VarTypes.findType(): sig: " + sig);
                System.out.println("VarTypes.findType():line and file: " + f.sourceFile + " " + f.startLine);
            }
            else {
                for (Integer i : argsForIndex.keySet()) {
                    if (sig.size() <= i) {
                        System.out.println("Error in VarTypes.findType() no signature element " + i + " for " + pred + " in " + sig);
                        continue;
                    }
                    String sigTypeAtIndex = null;
                    if (kb.getValence(pred) == -1)
                        sigTypeAtIndex = kb.kbCache.variableArityType(pred);
                    else
                        sigTypeAtIndex = sig.get(i);
                    HashSet<SuokifParser.ArgumentContext> args = argsForIndex.get(i);
                    for (SuokifParser.ArgumentContext ac : args) {
                        for (ParseTree c : ac.children) {
                            if (debug) System.out.println("child: " + c.getClass().getName());
                            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                                for (ParseTree c2 : ((SuokifParser.SentenceContext) c).children) {
                                    if (c2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$RelsentContext") ||
                                            c2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$LogsentContext") ||
                                            c2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$QuantsentContext"))
                                        f.higherOrder = true;
                                    if (c2.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext") &&
                                            ((SuokifParser.VariableContext) c2).REGVAR() != null) {
                                        FormulaPreprocessor.addToMap(f.varTypes,c2.getText(), sigTypeAtIndex);
                                    }
                                }
                            }
                            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$TermContext")) {
                                String t = findTypeOfTerm((SuokifParser.TermContext) c, sigTypeAtIndex);
                                if (!sigTypeAtIndex.equals(t) && !kb.isSubclass(t,sigTypeAtIndex))
                                    System.out.println("Error in VarTypes.findType(): arg " + c.getText() + " of type " + t +
                                            " not allowed as argument " + i + " to relation " + pred + " in formula " + f +
                                            " that requires " + sigTypeAtIndex);
                            }
                        }
                    }
                }
            }
        }
    }

    /** ***************************************************************
     */
    public void printContexts(HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> args) {

        System.out.println("VarTypes.printContexts(): args: ");
        for (Integer i : args.keySet()) {
            System.out.print(i + ": {");
            HashSet<SuokifParser.ArgumentContext> argTypes = args.get(i);
            for (SuokifParser.ArgumentContext ac : argTypes) {
                System.out.print(ac.getText() + ", ");
            }
            System.out.println("}");
        }
    }

    /** ***************************************************************
     */
    public void findTypes() {

        for (FormulaAST f : formulas) {
            findType(f);
            findEquationType(f);
            findRelationsAsArgs(f);
        }
    }
}
