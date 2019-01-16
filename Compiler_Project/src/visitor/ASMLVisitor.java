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

	private HashMap<String,Type> h; // les variables 
	private HashMap<String,String> varfloat; // les variables flottantes
	public HashMap<String,String> new_varfloat; // les flottants non déclarés
	public ArrayList<String> code; // le code asml principal
	public ArrayList<String> float_code; // le code asml contenant les déclarations de flottants en début de fichier
	public ArrayList<String> fun_code; // le code asml pour les fonctions
	public HashMap<String,ArrayList<String>> ff; // nom_fonction : (liste des arguments)
	private boolean isFun = false;
	
	
	public ASMLVisitor(HashMap<String,Type> env) {
		code = new ArrayList<String>();
		float_code = new ArrayList<String>();
		varfloat = new HashMap<String,String>();
		new_varfloat = new HashMap<String,String>();
		h = new HashMap<String,Type>(env);
		fun_code = new ArrayList<String>();
		ff = new HashMap<String,ArrayList<String>>();
		
		code.add("\nlet _ = \n");
		
		//System.out.println("let _ =");
		//expression.accept(this);
	}
	
	
	
	public void float_op(String operateur) {
		
	}
	
	
    @Override
    public void visit(Unit e) {
    	if(isFun) {
    		return;
    	}
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
    	if(!isFun) {

    		if(e.b) {
    			code.add("1");
    		}
    		else {
    			code.add("0");
    		}
    	}
    	else {
    		
    	
    	if(e.b) {
    		fun_code.add("1");
    	}
    	else {
    		fun_code.add("0");
    		}
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
    	if(!isFun) {
	    	code.add(String.valueOf(e.i));
	        //System.out.print(e.i);
    	}
    	else
    		fun_code.add(String.valueOf(e.i));
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Float expression
    *
     */
    @Override
    public void visit(Float e) {
    	if(!isFun) {


    		String s = String.format("%.2f", e.f);
    		if(float_code.get(float_code.size()-1).contains("tmp")) {
    			if(new_varfloat.containsKey(s)) {
    				code.add("r"+new_varfloat.get(s));
    			}
    			else {
    				code.add("rtmp"+new_varfloat.size());
    				new_varfloat.put(s,"tmp"+new_varfloat.size());
    			}



    		}
    		s = s.replaceAll(",","."); 
    		float_code.add(s);
    		return;
    	}
    	
    	
    }
    

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Not expression
    *
     */
    @Override
    public void visit(Not e) {
    	if(!isFun) {
    		//System.out.print("not ");
    		e.e.accept(this);
    		if(code.get(code.size()-1).equals("1")) {
    			code.set(code.size()-1, "0");
    		}
    		else {
    			code.set(fun_code.size()-1, "1");
    		}
    		//System.out.print("");
    	}
    	else{
    		e.e.accept(this);
    		if(fun_code.get(fun_code.size()-1).equals("1")) {
    			fun_code.set(fun_code.size()-1, "0");
    		}
    		else {
    			fun_code.set(fun_code.size()-1, "1");
    		}
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Neg expression
    *
     */
    @Override
    public void visit(Neg e) {
    	if(!isFun) {
	    	code.add("neg ");
	        //System.out.print("neg ");
	        e.e.accept(this);
	        //System.out.print("");
    	}
    	else {
    		fun_code.add("neg ");
    		e.e.accept(this);
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Add expression
    *
     */
    @Override
    public void visit(Add e) {
    	if(!isFun) {

	    	code.add("add ");
	    	//System.out.print("  ");
	        //System.out.print("add ");
	        e.e1.accept(this);
	        code.add(" ");
	        //System.out.print(" ");
	        e.e2.accept(this);
	        return;
    	}

    	fun_code.add("add ");
    	//System.out.print("  ");
        //System.out.print("add ");
        e.e1.accept(this);
        fun_code.add(" ");
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
    	if(!isFun) {
    		code.add("sub ");
    		//System.out.print("sub ");
    		e.e1.accept(this);
    		code.add(" ");
    		//System.out.print(" ");
    		e.e2.accept(this);
    		//System.out.print("");
    	}
    	else {
    		fun_code.add("sub ");
    		//System.out.print("sub ");
    		e.e1.accept(this);
    		fun_code.add(" ");
    		//System.out.print(" ");
    		e.e2.accept(this);
    		//System.out.print("");
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FNeg expression
    *
     */
    @Override
    public void visit(FNeg e) {
    	if(!isFun) {
    		code.add("fneg ");
    		//System.out.print("fneg ");
    		e.e.accept(this);
    		//System.out.print("");
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FAdd expression
    *
     */
    @Override
    public void visit(FAdd e) {
    	if(!isFun) {



    		//System.out.print("fadd ");

    		if(e.e1.getClass()==ast.Float.class && e.e2.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    			e.e1.accept(this);
    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");

    			code.add(code.get(code.size()-4));
    			code.set(code.size()-5,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    			code.add("fadd ");
    			code.add(code.get(code.size()-5));
    			code.set(code.size()-6,"");
    			code.add(" ");
    			//System.out.print(" ");
    			e.e2.accept(this);
    			code.add("\n");
    			return;
    		}
    		else if(e.e2.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    		}

    		else if(e.e1.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    		}


    		code.add("fadd ");
    		e.e1.accept(this);
    		code.add(" ");
    		//System.out.print(" ");
    		e.e2.accept(this);
    		code.add("\n");
    		//System.out.print("");
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FSub expression
    *
     */
    @Override
    public void visit(FSub e) {
    	if(!isFun) {

    		//System.out.print("fsub ");

    		if(e.e1.getClass()==ast.Float.class && e.e2.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    			e.e1.accept(this);
    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");

    			code.add(code.get(code.size()-4));
    			code.set(code.size()-5,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    			code.add("fsub ");
    			code.add(code.get(code.size()-5));
    			code.set(code.size()-6,"");
    			code.add(" ");
    			//System.out.print(" ");
    			e.e2.accept(this);
    			code.add("\n");
    			return;
    		}
    		else if(e.e2.getClass()==ast.Float.class) {


    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    		}

    		else if(e.e1.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    		}


    		code.add("fsub ");
    		e.e1.accept(this);
    		code.add(" ");
    		//System.out.print(" ");
    		e.e2.accept(this);
    		code.add("\n");
    		//System.out.print("");
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FMul expression
    *
     */
    @Override
    public void visit(FMul e) {
    	//System.out.print("fmul ");
    	if(!isFun) {


    		if(e.e1.getClass()==ast.Float.class && e.e2.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    			e.e1.accept(this);
    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");

    			code.add(code.get(code.size()-4));
    			code.set(code.size()-5,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    			code.add("fmul ");
    			code.add(code.get(code.size()-5));
    			code.set(code.size()-6,"");
    			code.add(" ");
    			//System.out.print(" ");
    			e.e2.accept(this);
    			code.add("\n");
    			return;
    		}
    		else if(e.e2.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    		}

    		else if(e.e1.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    		}


    		code.add("fmul ");
    		e.e1.accept(this);
    		code.add(" ");
    		//System.out.print(" ");
    		e.e2.accept(this);
    		code.add("\n");
    		//System.out.print("")
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input FDiv expression
    *
     */
    @Override
    public void visit(FDiv e) {
    	if(!isFun) {


    		if(e.e1.getClass()==ast.Float.class && e.e2.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    			e.e1.accept(this);
    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");

    			code.add(code.get(code.size()-4));
    			code.set(code.size()-5,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    			code.add("fdiv ");
    			code.add(code.get(code.size()-5));
    			code.set(code.size()-6,"");
    			code.add(" ");
    			//System.out.print(" ");
    			e.e2.accept(this);
    			code.add("\n");
    			return;
    		}
    		else if(e.e2.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    		}

    		else if(e.e1.getClass()==ast.Float.class) {

    			code.add("let addrtmp"+new_varfloat.size()+" = _tmp"+new_varfloat.size()+" in \n");
    			code.add("let rtmp"+new_varfloat.size()+" = mem(addrtmp"+new_varfloat.size()+" + 0) in \n");
    			code.add(code.get(code.size()-3));
    			code.set(code.size()-4,"");
    			float_code.add("\nlet _tmp"+new_varfloat.size()+" = ");
    		}


    		code.add("fdiv ");
    		e.e1.accept(this);
    		code.add(" ");
    		//System.out.print(" ");
    		e.e2.accept(this);
    		code.add("\n");
    		//System.out.print("")
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Eq expression
    *
     */
    @Override
    public void visit(Eq e) {
    	if(!isFun) {
    		
	        e.e1.accept(this);
	        code.add(" = ");
	        e.e2.accept(this);
    	}
    	else {
	        e.e1.accept(this);
	        fun_code.add(" = ");
	        e.e2.accept(this);
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input LE expression
    *
     */
    @Override
    public void visit(LE e) {
    	if(!isFun) {


    		//System.out.print("(");
    		e.e1.accept(this);
    		code.add(" <= ");
    		e.e2.accept(this);
    		//System.out.print(")");
    	}
    	else {
    		e.e1.accept(this);
    		fun_code.add(" <= ");
    		e.e2.accept(this);
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input If expression
    *
     */
    @Override
    public void visit(If e) {
    	if(!isFun) {

    		code.add("\nif ");
    		e.e1.accept(this);
    		code.add(" then (");
    		code.add("");
    		e.e2.accept(this);
    		code.add("\n) else (");
    		//System.out.println("");
    		e.e3.accept(this);
    		code.add("\n)");
    	}
    	else {
    		fun_code.add("\nif ");
    		e.e1.accept(this);
    		fun_code.add(" then (");
    		fun_code.add("");
    		e.e2.accept(this);
    		fun_code.add("\n) else (");
    		//System.out.println("");
    		e.e3.accept(this);
    		fun_code.add("\n)");
    	}
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Let expression
    *
     */
    @Override
    public void visit(Let e) {
    	if(!isFun) {
    		if(!h.containsKey(e.id.id)) { // si c'est un appel de fonction
    			code.add("let "+e.id+" = ");
    			e.e1.accept(this);
    			code.add(" in \n  ");
    		}
    		
    		else if(h.get(e.id.id).getClass()==TFloat.class && e.e1.getClass()!=FAdd.class && e.e1.getClass()!=FSub.class && e.e1.getClass()!=FMul.class && e.e1.getClass()!=FDiv.class) { // si c'est une déclaration de float directe
    			varfloat.put(e.id.id,""+(varfloat.size()+1));
    			float_code.add("\nlet _x"+varfloat.size()+" = ");
    			e.e1.accept(this);
    			code.add("let addr"+varfloat.size()+" = _x"+varfloat.size()+" in \n");
    			code.add("let r"+varfloat.size()+" = mem(addr"+varfloat.size()+" + 0) in \n");
    		}


    		else { // si c'est une déclaration d'entiers ou de float avec une opération (ex : 1.2+2.5)
    			code.add("let "+e.id+" = ");
    			//System.out.print("  let ");
    			//System.out.print(e.id);
    			//System.out.print(" = ");
    			e.e1.accept(this);
    			code.add(" in \n  ");
    			//System.out.print(" in ");
    			//System.out.println("");
    		}
    		e.e2.accept(this);
    		//System.out.print("");
    	}
    	else {
    		if(!h.containsKey(e.id.id)) { // si c'est un appel de fonction
    			fun_code.add("let "+e.id+" = ");
    			e.e1.accept(this);
    			fun_code.add(" in \n  ");
    		}
    		
    		else if(h.get(e.id.id).getClass()==TFloat.class && e.e1.getClass()!=FAdd.class && e.e1.getClass()!=FSub.class && e.e1.getClass()!=FMul.class && e.e1.getClass()!=FDiv.class) { // si c'est une déclaration de float directe
    			varfloat.put(e.id.id,""+(varfloat.size()+1));
    			float_code.add("\nlet _x"+varfloat.size()+" = ");
    			e.e1.accept(this);
    			fun_code.add("let addr"+varfloat.size()+" = _x"+varfloat.size()+" in \n");
    			fun_code.add("let r"+varfloat.size()+" = mem(addr"+varfloat.size()+" + 0) in \n");
    		}


    		else { // si c'est une déclaration d'entiers ou de float avec une opération (ex : 1.2+2.5)
    			fun_code.add("let "+e.id+" = ");
    			//System.out.print("  let ");
    			//System.out.print(e.id);
    			//System.out.print(" = ");
    			e.e1.accept(this);
    			fun_code.add(" in \n  ");
    			//System.out.print(" in ");
    			//System.out.println("");
    		}
    		e.e2.accept(this);
    		//System.out.print("");
    	}
        
    }

    /**
     * Visitor qui affiche le code ASML généré
     *
     * @param e input Var expression
    *
     */
    @Override
    public void visit(Var e) {
    	if(!isFun) {


    		String f = e.id.id;
    		switch(f) {
    		case "print_int":
    			//System.out.print("_min_caml_print_int");
    			code.add("min_caml_print_int");
    			break;

    		case "print_newline":
    			//System.out.print("_min_caml_print_newline");
    			code.add("min_caml_print_newline");
    			break;

    		case "cos":
    			//System.out.print("_min_caml_cos");
    			code.add("min_caml_cos");

    			break;

    		case "sin":
    			//System.out.print("_min_caml_sin");
    			code.add("min_caml_sin");
    			break;

    		case "sqrt":
    			//System.out.print("_min_caml_sqrt");
    			code.add("min_caml_sqrt");
    			break;
    		default:
    			//System.out.print(e.id);
    			if(varfloat.containsKey(e.id.id)) {
    				code.add("r"+varfloat.get(e.id.id));
    			}
    			else
    				code.add(String.valueOf(e.id));
    			break;
    		}
    	}
    	else{


    		String f = e.id.id;
    		switch(f) {
    		case "print_int":
    			//System.out.print("_min_caml_print_int");
    			fun_code.add("min_caml_print_int");
    			break;

    		case "print_newline":
    			//System.out.print("_min_caml_print_newline");
    			fun_code.add("min_caml_print_newline");
    			break;

    		case "cos":
    			//System.out.print("_min_caml_cos");
    			fun_code.add("min_caml_cos");

    			break;

    		case "sin":
    			//System.out.print("_min_caml_sin");
    			fun_code.add("min_caml_sin");
    			break;

    		case "sqrt":
    			//System.out.print("_min_caml_sqrt");
    			fun_code.add("min_caml_sqrt");
    			break;
    		default:
    			//System.out.print(e.id);
    			if(varfloat.containsKey(e.id.id)) {
    				fun_code.add("r"+varfloat.get(e.id.id));
    			}
    			else
    				fun_code.add(String.valueOf(e.id));
    			break;
    		}
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
        	code.add(" ");
            it.next().accept(this);
            
        }
    }

    public void visit(LetRec e){
    	isFun = true;
        System.out.print(e.fd.id);
        ArrayList<String> tmp_arraylist = new ArrayList<String>();
        for(Id id:e.fd.args) {
        	tmp_arraylist.add(id.id);
        }
        fun_code.add("");
        ff.put("_"+e.fd.id.id,tmp_arraylist);
        fun_code.add("let _"+e.fd.id.id+" ");
        for(String s:tmp_arraylist) {
        	fun_code.add(s+ " ");
        }
        fun_code.add("=\n");
        System.out.println(ff);
        //System.out.print(" = ");
        e.fd.e.accept(this);
        //System.out.print(" in ");
        isFun = false;
        e.e.accept(this);
        //System.out.print(")");     
    }

    public void visit(App e){
    	if(!isFun) {


    		code.add("call _");
    		//System.out.print("  call ");
    		e.e.accept(this);
    		code.add(" ");
    		//System.out.print(" ");
    		printInfix2(e.es, " ");
    	}
    	else {
    		fun_code.add("call _");
    		//System.out.print("  call ");
    		e.e.accept(this);
    		fun_code.add(" ");
    		//System.out.print(" ");
    		printInfix2(e.es, " ");
    	}
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