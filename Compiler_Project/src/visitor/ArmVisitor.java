package visitor;

import ast.*;
import ast.Float;

import java.util.*;

public class ArmVisitor implements Visitor {
	
	HashMap<String,String> var; // ex : x : [fp-8]
	private int next_fp;
	ArrayList<String> code;
	
	public ArmVisitor() {
		code = new ArrayList<String>();
		prologue();
		next_fp=-8;
		var = new HashMap<String,String>();
		
		
	}
	
	public String prologue() {
		System.out.print(".data\r\n" + 
				".text\r\n" + 
				".global main");
		String prologue= "	@ Prologue\r\n" + 
				"	STMFD sp!, {fp, lr} @ Save Frame Pointer and Link Pointer on the stack\r\n" + 
				"	ADD fp, sp, #4 @ Position Frame Pointer on the address of old Frame Pointer\r\n" + 
				"	SUB sp, #16 @ Allocate memory to store local variables";
		System.out.println(prologue);
		return prologue;
	}
	
	public String epilogue() {
		String epilogue = "	@ Epilogue\r\n" + 
				"	SUB sp, fp, #4\r\n" + 
				"	LDMFD sp!, {fp, lr}\r\n" + 
				"	BX lr";
		System.out.println(epilogue);
		return epilogue;
	}
	
	
    public void visit(Unit e) {
        System.out.print("()");
    }

    public void visit(Bool e) {
        System.out.print(e.b);
    }

    public void visit(Int e) {
        System.out.print(e.i);
    }

    public void visit(Float e) {

    }

    public void visit(Not e) {

    }

    public void visit(Neg e) {

    }

    public void visit(Add e) {

    }

    public void visit(Sub e) {

    }

    public void visit(FNeg e){

    }

    public void visit(FAdd e){

    }

    public void visit(FSub e){

    }

    public void visit(FMul e) {

    }

    public void visit(FDiv e){

    }

    public void visit(Eq e){

    }

    public void visit(LE e){

    }

    public void visit(If e){

    }

    public void visit(Let e) {
    	System.out.print("\n	@ ASML : Assign value to "+e.id.id);
    	/*if(e.e1.getClass()==ast.Add.class) {
    		
    	}*/
    	System.out.print("\n	LDR r4, =");
    	e.e1.accept(this);
    	System.out.print("\n	STR r4, [fp, #"+next_fp+"]");
    	var.put(e.id.id,"[fp, #"+next_fp+"]");
    	next_fp=next_fp-4;
    	System.out.println();
    	e.e2.accept(this);
        
    }

    public void visit(Var e){
        System.out.print(e.id);
    }


    // print sequence of identifiers 
    static <E> void printInfix(List<E> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        Iterator<E> it = l.iterator();
        System.out.print(it.next());
        while (it.hasNext()) {
            System.out.print(op + it.next());
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
            System.out.print(op);
            it.next().accept(this);
        }
    }

    public void visit(LetRec e){

    }

    public void visit(App e){

    }

    public void visit(Tuple e){

    }

    public void visit(LetTuple e){

    }

    public void visit(Array e){

    }

    public void visit(Get e){

    }

    public void visit(Put e){

    }
}


