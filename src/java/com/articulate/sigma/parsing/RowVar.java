package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashSet;

public class RowVar {

    public KB kb = null;

    /** ***************************************************************
     */
    public RowVar(KB kbin) {

        if (!PredVarInst.predVarInstDone == true)
            System.out.println("Error! in RowVar() Predicate variable instantiation is required and has not been completed");
        kb = kbin;
    }

    /** ***************************************************************
     * argument : (sentence | term)
     * sentence : (relsent | logsent | quantsent | variable)
     * variable : (REGVAR | ROWVAR) ;
     */
    public String getRowVarArgument(SuokifParser.ArgumentContext ac) {

        if (ac.children.iterator().next().getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
            SuokifParser.SentenceContext sc = (SuokifParser.SentenceContext) ac.children.iterator().next();
            if (sc.children.iterator().next().getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                SuokifParser.VariableContext vc = (SuokifParser.VariableContext) sc.children.iterator().next();
                if (vc.ROWVAR() != null)
                    return vc.ROWVAR().getText();
            }
        }
        System.out.println("Error in getRowVarArgument(): no row var found in " + ac.getText());
        return null;
    }

    /** ***************************************************************
     * @return an ArrayList with the predicate as the first element and
     * the row variable as the second
     * funterm : '(' FUNWORD argument+ ')'
     * argument : (sentence | term)
     * sentence : (relsent | logsent | quantsent | variable)
     * relsent : ('(' IDENTIFIER argument+ ')') | ('(' variable argument+ ')')  ;
     */
    public ArrayList<String> predAndRowForLiteral(ParserRuleContext prc) {

        System.out.println("predAndRowForLiteral(): " + prc.getText());
        ArrayList<String> result = new ArrayList<>();
        if (prc.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$RelsentContext")) {
            SuokifParser.RelsentContext rel = (SuokifParser.RelsentContext) prc;
            System.out.println("predAndRowForLiteral(): relsent: " + rel.getText());
            if (rel.IDENTIFIER() != null)
                result.add(rel.IDENTIFIER().toString());
            for (ParseTree c : prc.children) {
                if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ArgumentContext")) {
                    SuokifParser.ArgumentContext ac = (SuokifParser.ArgumentContext) c;
                    System.out.println("predAndRowForLiteral(): argument: " + ac.getText());
                    result.add(getRowVarArgument(ac));
                }
            }
        }
        if (prc.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$FuntermContext")) {
            SuokifParser.FuntermContext rel = (SuokifParser.FuntermContext) prc;
            System.out.println("predAndRowForLiteral(): funterm: " + rel.getText());
            if (rel.FUNWORD() != null)
                result.add(rel.FUNWORD().toString());
            for (ParseTree c : prc.children) {
                if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ArgumentContext")) {
                    SuokifParser.ArgumentContext ac = (SuokifParser.ArgumentContext) c;
                    System.out.println("predAndRowForLiteral(): argument: " + ac.getText());
                    result.add(getRowVarArgument(ac));
                }
            }
        }
        return result;
    }

    /** ***************************************************************
     */
    public void expandRowVar(FormulaAST f) {

        for (ParserRuleContext prc : f.rowvarLiterals) {
            ArrayList<String> ar = predAndRowForLiteral(prc);
            String pred = ar.get(0);
            String var = ar.get(1);
            System.out.println("formula " + f);
            System.out.println("literal " + prc.getText());
            System.out.println("# children: "  + prc.children.size());
            ArrayList<String> sig = kb.kbCache.getSignature(pred);
            int arity = kb.kbCache.getArity(pred);
            if (arity > 0) {
                int resultArity = arity - (prc.children.size() - 4);
                String varName = var.substring(1);
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= resultArity+1; i++)
                    sb.append("?" + varName + i + " ");
                sb.deleteCharAt(sb.length() - 1);
                f.setFormula(f.getFormula().replace(var,sb.toString()));
            }
            else {

            }
        }
    }

    /** ***************************************************************
     */
    public void expandRowVar(HashSet<FormulaAST> rowvars) {
        for (FormulaAST f : rowvars)
            expandRowVar(f);
    }
}
