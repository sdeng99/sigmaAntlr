package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import com.articulate.sigma.FormulaPreprocessor;
import com.articulate.sigma.KB;
import com.articulate.sigma.KBmanager;
import com.articulate.sigma.utils.FileUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TPTPWriter {

    public HashSet<FormulaAST> formulas = null;

    public static boolean debug = false;
    
    public void generateTPTP() {

        for (FormulaAST f : formulas) {
            
        }
    }

    /** ***************************************************************
     * file : (sentence | comment)+ EOF ;
     * @return a String of TPTP formulas
     */
    public String visitFile(SuokifParser.FileContext context) {

        StringBuilder sb = new StringBuilder();
        if (debug) System.out.println("visitFile() Visiting file: " + context.getText());
        if (debug) System.out.println("visitFile() # children: " + context.children.size());
        if (debug) System.out.println("visitFile() text: " + context.getText());
        int counter = 0;
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitFile() child: " + c.getClass().getName());
            FormulaAST f = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                sb.append(visitSentence((SuokifParser.SentenceContext) c));
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$CommentContext")) {
                sb.append("# " + visitComment((SuokifParser.CommentContext) c));
            }
        }
        return sb.toString();
    }

    /** ***************************************************************
     * sentence : (relsent | logsent | quantsent | variable) ;
     */
    public String visitSentence(SuokifParser.SentenceContext context) {

        if (context == null || context.children == null) return null;
        if (debug) System.out.println("visitSentence() Visiting sentence: " + context.getText());
        if (debug) System.out.println("visitSentence() # children: " + context.children.size());
        if (debug) System.out.println("visitSentence() text: " + context.getText());
        for (ParseTree c : context.children) {
            if (debug)  System.out.println("child of sentence: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$RelsentContext"))
                return visitRelsent((SuokifParser.RelsentContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$LogsentContext"))
                return visitLogsent((SuokifParser.LogsentContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$QuantsentContext"))
                return visitQuantsent((SuokifParser.QuantsentContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext"))
                return visitVariable((SuokifParser.VariableContext) c);
        }
        return null;
    }

    /** ***************************************************************
     */
    public String visitComment(SuokifParser.CommentContext context) {

        if (debug) System.out.println("Visiting comment: " + context.getText());
        if (debug) System.out.println(context.COMMENT().getText() + "\n");
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        return context.getText();
    }

    /** ***************************************************************
     * relsent : ('(' IDENTIFIER argument+ ')') | ('(' variable argument+ ')')  ;
     * argument : (sentence | term) ;
     * term : (funterm | variable | string | number | FUNWORD | IDENTIFIER ) ;
     * Set the types of any variables that appear in an instance or subclass
     * declaration
     */
    public String visitRelsent(SuokifParser.RelsentContext context) {

        StringBuilder sb = new StringBuilder();
        FormulaAST result = new FormulaAST();
        HashSet<FormulaAST.RowStruct> newRowVarStructs = new HashSet<>();
        result.predVarCache = new HashSet<>();
        if (debug) System.out.println("Visiting relsent: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        String pred = null;
        int argnum = 0;
        ArrayList<String> argList = new ArrayList<>();
        HashSet<FormulaAST.ArgStruct> constantTerms = new HashSet<>(); // tracks if a constant is an argument
        HashMap<Integer,HashSet<SuokifParser.ArgumentContext>> args = new HashMap<>();
        if (context.IDENTIFIER() != null) {
            pred = context.IDENTIFIER().toString();
            sb.append("s__" + pred + "(");
        }
        for (ParseTree c : context.children) {
            if (debug) System.out.println("child of relsent: " + c.getClass().getName());
            FormulaAST f = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                sb.append(visitVariable((SuokifParser.VariableContext) c) + ",");
            }
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ArgumentContext")) {
                SuokifParser.ArgumentContext ac = (SuokifParser.ArgumentContext) c;
                sb.append(visitArgument(ac) + ",");
                if (debug) System.out.println("ac: " + ac.getText());
            }
        }
        sb.delete(sb.length()-1,sb.length());
        sb.append(")");
        return sb.toString();
    }

    /** ***************************************************************
     * argument : (sentence | term) ;
     */
    public String visitArgument(SuokifParser.ArgumentContext context) {

        if (debug) System.out.println("Visiting argument: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (context.children.size() != 1)
            System.out.println("error in visitArgument() wrong # children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
            if (debug) System.out.println("child of argument: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                return visitSentence((SuokifParser.SentenceContext) c);
            }
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$TermContext"))
                return visitTerm((SuokifParser.TermContext) c);
        }
        return null;
    }

    /** ***************************************************************
     * logsent :  (notsent | andsent | orsent | implies | iff | eqsent) ;
     */
    public String visitLogsent(SuokifParser.LogsentContext context) {

        if (debug) System.out.println("Visiting logsent: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (context.children.size() != 1)
            System.out.println("error in visitLogsent() wrong # children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitLogsent() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$NotsentContext"))
                return visitNotsent((SuokifParser.NotsentContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$AndsentContext"))
                return visitAndsent((SuokifParser.AndsentContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$OrsentContext"))
                return visitOrsent((SuokifParser.OrsentContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ImpliesContext"))
                return visitImplies((SuokifParser.ImpliesContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$IffContext"))
                return visitIff((SuokifParser.IffContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$EqsentContext"))
                return visitEqsent((SuokifParser.EqsentContext) c);
        }
        return null;
    }

    /** ***************************************************************
     * notsent : '(' 'not' sentence ')' ;
     */
    public String visitNotsent(SuokifParser.NotsentContext context) {

        String f = null;
        if (debug) System.out.println("Visiting Notsent: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitNotsent() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext"))
                f = visitSentence((SuokifParser.SentenceContext) c);
        }
        return "~(" + f + ")";
    }

    /** ***************************************************************
     * andsent : '(' 'and' sentence sentence+ ')' ;
     */
    public String visitAndsent(SuokifParser.AndsentContext context) {

        StringBuilder sb = new StringBuilder();
        if (debug) System.out.println("Visiting Andsent: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        ArrayList<FormulaAST> ar = new ArrayList<>();
        sb.append("( ");
        for (ParseTree c : context.children) {
            if (debug) System.out.println("child of andsent: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                sb.append(visitSentence((SuokifParser.SentenceContext) c) + " & ");
            }
        }
        sb.delete(sb.length()-2,sb.length());
        sb.append(")");
        return sb.toString();
    }

    /** ***************************************************************
     * orsent : '(' 'or' sentence sentence+ ')' ;
     */
    public String visitOrsent(SuokifParser.OrsentContext context) {

        StringBuilder sb = new StringBuilder();
        if (debug) System.out.println("Visiting Orsent: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        sb.append("(");
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitOrsent() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                sb.append(visitSentence((SuokifParser.SentenceContext) c) + " | ");
            }
        }
        sb.delete(sb.length()-2,sb.length());
        sb.append(")");
        return sb.toString();
    }

    /** ***************************************************************
     * implies :  '(' '=>' sentence sentence ')' ;
     */
    public String visitImplies(SuokifParser.ImpliesContext context) {

        StringBuilder sb = new StringBuilder();
        if (debug) System.out.println("Visiting Implies: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        String f1 = null;
        String f2 = null;
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitImplies() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                if (f1 == null) {
                    f1 = visitSentence((SuokifParser.SentenceContext) c);
                }
                else {
                    f2 = visitSentence((SuokifParser.SentenceContext) c);
                }
            }
        }
        return "( " + f1 + " => " + f2 + ")";
    }

    /** ***************************************************************
     * iff : '(' '<=>' sentence sentence ')' ;
     */
    public String visitIff(SuokifParser.IffContext context) {

        if (debug) System.out.println("Visiting Iff: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        String f1 = null;
        String f2 = null;
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitIff() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                if (f1 == null) {  // this is a bit questionable since it makes the lexically first element the antecedent
                    f1 = visitSentence((SuokifParser.SentenceContext) c);
                }
                else {
                    f2 = visitSentence((SuokifParser.SentenceContext) c);
                }
            }
        }
        return "( " + f1 + " <=> " + f2 + ")";
    }

    /** ***************************************************************
     * eqsent : '(' 'equal' term term ')' ;
     * argument : (sentence | term) ;
     * term : (funterm | variable | string | number | FUNWORD | IDENTIFIER ) ;
     */
    public String visitEqsent(SuokifParser.EqsentContext context) {

        if (debug) System.out.println("Visiting Eqsent: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        String f1 = null;
        String f2 = null;
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitEqsent() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$TermContext")) {
                if (f1 == null) {
                    f1 = visitTerm((SuokifParser.TermContext) c);
                }
                else {
                    f2 = visitTerm((SuokifParser.TermContext) c);
                }
            }
        }
        return "(" + f1 + " = " + f2 + ")";
    }

    /** ***************************************************************
     * quantsent : (forall | exists) ;
     */
    public String visitQuantsent(SuokifParser.QuantsentContext context) {

        if (debug) System.out.println("Visiting quantsent: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        FormulaAST f = null;
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitQuantsent() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ForallContext"))
                return visitForall((SuokifParser.ForallContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ExistsContext"))
                return visitExists((SuokifParser.ExistsContext) c);
        }
        return null;
    }

    /** ***************************************************************
     * forall : '(' 'forall' '(' variable+ ')' sentence ')' ;
     */
    public String visitForall(SuokifParser.ForallContext context) {

        if (debug) System.out.println("Visiting Forall: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        StringBuilder varlist = new StringBuilder();
        FormulaAST f = null;
        String body = null;
        if (debug) System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitForall() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                String farg = visitVariable((SuokifParser.VariableContext) c);
                varlist.append(farg + ", ");
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                body = visitSentence((SuokifParser.SentenceContext) c);
            }
        }
        varlist.delete(varlist.length()-2,varlist.length());
        return "! [" + varlist + "] : (" + body + ")";
    }

    /** ***************************************************************
     * exists : '(' 'exists' '(' variable+ ')' sentence ')' ;
     */
    public String visitExists(SuokifParser.ExistsContext context) {


        if (debug) System.out.println("Visiting Exists: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        StringBuilder varlist = new StringBuilder();
        String body = null;
        if (debug) System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitExists() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                String farg = visitVariable((SuokifParser.VariableContext) c);
                varlist.append(farg + ", ");
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                body = visitSentence((SuokifParser.SentenceContext) c);
            }
        }
        varlist.delete(varlist.length()-2,varlist.length());
        return "? [" + varlist + "] : (" + body + ")";
    }

    /** ***************************************************************
     * variable : (REGVAR | ROWVAR) ;
     */
    public String visitVariable(SuokifParser.VariableContext context) {

        if (debug) System.out.println("Visiting variable: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        if (context.REGVAR() != null) {
            if (debug) System.out.println("regvar: " + context.REGVAR().toString());
            return "V__" + context.REGVAR().toString().substring(1);
        }
        if (context.ROWVAR() != null) {
            if (debug) System.out.println("Error - no row vars should exist at this point - rowv: " + context.ROWVAR().toString());
            return context.ROWVAR().toString();
        }
        return null;
    }

    /** ***************************************************************
     * term : (funterm | variable | string | number | FUNWORD | IDENTIFIER ) ;
     */
    public String visitTerm(SuokifParser.TermContext context) {

        if (debug) System.out.println("visitTerm() Visiting Term: " + context.getText());
        if (debug) System.out.println("visitTerm() # children: " + context.children.size());
        if (context.children.size() != 1)
            System.out.println("error in visitTerm() wrong # children: " + context.children.size());
        if (debug) System.out.println("visitTerm() text: " + context.getText());
        if (context.IDENTIFIER() != null) {
            String ident = context.IDENTIFIER().toString();
            if (debug) System.out.println("visitTerm() identifier: " + ident);
            return "s__" + ident;
        }
        if (context.FUNWORD() != null) {
            String funword = context.FUNWORD().toString();
            if (debug) System.out.println("visitTerm() funword: " + funword);
            return "s__" + funword;
        }
        for (ParseTree c : context.children) { // there should be only one child
            if (debug) System.out.println("visitTerm() child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$FuntermContext"))
                return visitFunterm((SuokifParser.FuntermContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                return visitVariable((SuokifParser.VariableContext) c);
            }
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$StringContext"))
                return visitString((SuokifParser.StringContext) c);
            else if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$NumberContext")) {
                if (debug) System.out.println("visitTerm() found a number: " + c.getText());
                return visitNumber((SuokifParser.NumberContext) c);
            }
        }
        return null;
    }

    /** ***************************************************************
     * funterm : '(' FUNWORD argument+ ')' ;
     */
    public String visitFunterm(SuokifParser.FuntermContext context) {

        StringBuilder sb = new StringBuilder();
        if (debug) System.out.println("Visiting funterm: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        HashSet<FormulaAST.RowStruct> newRowVarStructs = new HashSet<>();
        String funword = null;
        if (context.FUNWORD() != null) {
            funword = context.FUNWORD().toString();
            if (debug) System.out.println("funword: " + funword);
            sb.append("s__" + funword + "(");
        }
        int argnum = 1;
        HashMap<Integer,HashSet<SuokifParser.ArgumentContext>> args = new HashMap<>();
        ArrayList<FormulaAST> arf = new ArrayList<>();
        for (ParseTree c : context.children) {
            if (debug) System.out.println("visitFunterm() child: " + c.getClass().getName());
            FormulaAST farg = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ArgumentContext")) {
                SuokifParser.ArgumentContext ac = (SuokifParser.ArgumentContext) c;
                sb.append(visitArgument(ac) + ",");
            }
        }
        sb.delete(sb.length()-1,sb.length());
        sb.append(")");
        return sb.toString();
    }

    /** ***************************************************************
     */
    public String visitString(SuokifParser.StringContext context) {

        if (debug) System.out.println("Visiting string: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        FormulaAST f = new FormulaAST();
        f.setFormula(context.getText());
        return context.getText();
    }

    /** ***************************************************************
     */
    public String visitNumber(SuokifParser.NumberContext context) {

        if (debug) System.out.println("Visiting number: " + context.getText());
        if (debug) System.out.println("# children: " + context.children.size());
        if (debug) System.out.println("text: " + context.getText());
        return context.getText();
    }

    /** ***************************************************************
     */
    public String wrappedMetaFormat(FormulaAST f) {
        return "fof(kb_" + FileUtil.noExt(FileUtil.noPath(f.sourceFile)) + "_" + f.startLine + ",axiom," + this.visitSentence(f.parsedFormula) + ").";
    }

    /** ***************************************************************
     */
    public static void showHelp() {

        System.out.println("KBTPTPWriter class");
        System.out.println("  options (with a leading '-':");
        System.out.println("  h - show this help screen");
        System.out.println("  t - translate configured KB");
    }

    /** ***************************************************************
     */
    public static void main(String[] args) throws IOException {

        System.out.println("INFO in TPTPWriter.main()");
        if (args != null && args.length > 0 && args[0].equals("-h"))
            showHelp();
        else {
            KBmanager.getMgr().initializeOnce();
            String kbName = KBmanager.getMgr().getPref("sumokbname");
            KB kb = KBmanager.getMgr().getKB(kbName);
            if (args != null && args.length > 0 && args[0].contains("t")) {
                KBmanager.getMgr().initializeOnce();
                SuokifVisitor sv = new SuokifVisitor();
                sv.parseFile(System.getenv("SIGMA_HOME") + File.separator + "KBs" + File.separator + "Merge.kif");
                Preprocessor pre = new Preprocessor(KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname")));
                pre.removeMultiplePredVar(sv); // remove explosive rules with multiple predicate variables
                HashSet<FormulaAST> rules = pre.preprocess(sv.hasPredVar, sv.hasRowVar, sv.rules);
                TPTPWriter tptpW = new TPTPWriter();
                for (FormulaAST f : rules) {
                    System.out.println("fof(kb_" + FileUtil.noExt(FileUtil.noPath(f.sourceFile)) + "_" + f.startLine + ",axiom," + tptpW.visitSentence(f.parsedFormula) + ").");
                }
            }
            else
                showHelp();
        }
    }
}
