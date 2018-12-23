package visitor;

import utils.*;
import type.*;
import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Visiteur non terminé pour générer du ASML
 *
 */
public class ASMLVisitor implements Visitor {
    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Exp Expression
    *
     */

	private HashMap<String,String> h;
	public ArrayList<String> code;
	public ArrayList<String> float_code;
	
	public ASMLVisitor() {
		code = new ArrayList<String>();
		code.add("\nlet _ = \n");
		//System.out.println("let _ =");
		//expression.accept(this);
	}
	
	
    @Override
    public void visit(Unit e) {
    	
        //System.out.print("()");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Bool expression
    *
     */
    @Override
    public void visit(Bool e) {
    	if(e.b) {
    		code.add("true");
    	}
    	else {
    		code.add("false");
    	}
    	
        //System.out.print(e.b);
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Int expression
    *
     */
    @Override
    public void visit(Int e) {
    	code.add(String.valueOf(e.i));
        //System.out.print(e.i);
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Float expression
    *
     */
    @Override
    public void visit(Float e) {
        String s = String.format("%.2f", e.f);
        //System.out.print(s);
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Not expression
    *
     */
    @Override
    public void visit(Not e) {
    	code.add("not ");
        //System.out.print("not ");
        e.e.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Neg expression
    *
     */
    @Override
    public void visit(Neg e) {
    	code.add("neg ");
        //System.out.print("neg ");
        e.e.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Add expression
    *
     */
    @Override
    public void visit(Add e) {
    	code.add("add ");
    	//System.out.print("  ");
        //System.out.print("add ");
        e.e1.accept(this);
        code.add(" ");
        //System.out.print(" ");
        e.e2.accept(this);
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Sub expression
    *
     */
    @Override
    public void visit(Sub e) {
    	code.add("sub ");
        //System.out.print("sub ");
        e.e1.accept(this);
        code.add(" ");
        //System.out.print(" ");
        e.e2.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FNeg expression
    *
     */
    @Override
    public void visit(FNeg e) {
    	code.add("fneg ");
        //System.out.print("fneg ");
        e.e.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FAdd expression
    *
     */
    @Override
    public void visit(FAdd e) {
    	code.add("fadd ");
        //System.out.print("fadd ");
        e.e1.accept(this);
        code.add(" ");
        //System.out.print(" ");
        e.e2.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FSub expression
    *
     */
    @Override
    public void visit(FSub e) {
    	code.add("fsub ");
        //System.out.print("fsub ");
        e.e1.accept(this);
        code.add(" ");
        //System.out.print(" ");
        e.e2.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FMul expression
    *
     */
    @Override
    public void visit(FMul e) {
    	code.add("fmul ");
        //System.out.print("fmul ");
        e.e1.accept(this);
        //System.out.print(" ");
        e.e2.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FDiv expression
    *
     */
    @Override
    public void visit(FDiv e) {
    	code.add("fdiv ");
        //System.out.print("fdiv ");
        e.e1.accept(this);
        code.add(" ");
        //System.out.print(" ");
        e.e2.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Eq expression
    *
     */
    @Override
    public void visit(Eq e) {
        e.e1.accept(this);
        code.add(" = ");
        //System.out.print(" = ");
        e.e2.accept(this);
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input LE expression
    *
     */
    @Override
    public void visit(LE e) {
        //System.out.print("(");
        e.e1.accept(this);
        //System.out.print(" <= ");
        e.e2.accept(this);
        //System.out.print(")");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input If expression
    *
     */
    @Override
    public void visit(If e) {
        //System.out.print("if ");
        e.e1.accept(this);
        //System.out.print(" then (");
        //System.out.println("");
        e.e2.accept(this);
        //System.out.print("\n) else (");
        //System.out.println("");
        e.e3.accept(this);
        //System.out.println("\n)");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Let expression
    *
     */
    @Override
    public void visit(Let e) {
    	code.add("let "+e.id+" = ");
        //System.out.print("  let ");
        //System.out.print(e.id);
        //System.out.print(" = ");
        e.e1.accept(this);
        code.add(" in \n  ");
        //System.out.print(" in ");
        //System.out.println("");
        e.e2.accept(this);
        //System.out.print("");
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Var expression
    *
     */
    @Override
    public void visit(Var e) {
    	String f = e.id.id;
    	switch(f) {
    	case "print_int":
    		//System.out.print("_min_caml_print_int");
    		code.add("_min_caml_print_int");
    		break;
    		
    	case "print_newline":
    		//System.out.print("_min_caml_print_newline");
    		code.add("_min_caml_print_newline");
    		break;
    		
    	case "cos":
    		//System.out.print("_min_caml_cos");
    		code.add("_min_caml_cos");
    		
    		break;
    	
    	case "sin":
    		//System.out.print("_min_caml_sin");
    		code.add("_min_caml_sin");
    		break;
    	
    	case "sqrt":
    		//System.out.print("_min_caml_sqrt");
    		code.add("_min_caml_sqrt");
    		break;
    	default:
    		//System.out.print(e.id);
    		code.add(String.valueOf(e.id));
    		break;
    	}
    }



    // print sequence of identifiers 
    static <E> void printInfix(List<E> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<E> it = l.iterator();
        //System.out.print(it.next());
        while (it.hasNext()) {
            //System.out.print(op + it.next());
        }
    }

    // print sequence of Exp
    void printInfix2(List<Exp> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<Exp> it = l.iterator();
        it.next().accept(this);
        while (it.hasNext()) {
            //System.out.print(op);
            it.next().accept(this);
        }
    }

    public void visit(LetRec e){
        //System.out.print("(let rec " + e.fd.id + " ");
        printInfix(e.fd.args, " ");
        //System.out.print(" = ");
        e.fd.e.accept(this);
        //System.out.print(" in ");
        e.e.accept(this);
        //System.out.print(")");
    }

    public void visit(App e){
    	code.add("call ");
    	//System.out.print("  call ");
        e.e.accept(this);
        code.add(" ");
        //System.out.print(" ");
        printInfix2(e.es, " ");
    }

    public void visit(Tuple e){
        //System.out.print("(");
        printInfix2(e.es, ", ");
        //System.out.print(")");
    }

    public void visit(LetTuple e){
        //System.out.print("(let (");
        printInfix(e.ids, ", ");
        //System.out.print(") = ");
        e.e1.accept(this);
        //System.out.print(" in ");
        e.e2.accept(this);
        //System.out.print(")");
    }

    public void visit(Array e){
        //System.out.print("(Array.create ");
        e.e1.accept(this);
        //System.out.print(" ");
        e.e2.accept(this);
        //System.out.print(")");
    }

    public void visit(Get e){
        e.e1.accept(this);
        //System.out.print(".(");
        e.e2.accept(this);
        //System.out.print(")");
    }

    public void visit(Put e){
        //System.out.print("(");
        e.e1.accept(this);
        //System.out.print(".(");
        e.e2.accept(this);
        //System.out.print(") <- ");
        e.e3.accept(this);
        //System.out.print(")");
    }
}


