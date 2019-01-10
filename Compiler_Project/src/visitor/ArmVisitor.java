package visitor;

import ast.*;
import ast.Float;
import utils.Id;

import java.util.*;

public class ArmVisitor implements Visitor { //passage de param en + donc implemente pas visitor
	
	//HashMap<String,String> var; // ex : x : [fp-8]
	//private int next_fp;
	//ArrayList<String> code;

    private ArmVisitorArgs vArgs = new ArmVisitorArgs();

	public ArmVisitor() {
		//code = new ArrayList<String>();
		//prologue();
		//next_fp=-8;
		//var = new HashMap<String,String>();
		
		
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
        System.out.print(" "+e.b);
    }

    public void visit(Int e) {
        System.out.print(" " +e.i);
    }

    public void visit(Float e) {

    }

    public void visit(Not e) {
        System.out.print("cmp");
        e.e.accept(this);
        System.out.println(" #0");
        System.out.println("moveq");
        e.e.accept(this);
        System.out.println(" #0");
        System.out.println("movne");
        e.e.accept(this);
        System.out.println(" #0");

       /* cmp	r3, #0
        moveq	r3, #1
        movne	r3, #0*/
    }

    public void visit(Neg e) { // pour le neg on soustrait a zero la valeur
        System.out.print("sub");
        e.e.accept(this);
        System.out.print(" #0");
        e.e.accept(this);
        System.out.println("");
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
        System.out.print("cmp");
        e.e1.accept(this);
        e.e2.accept(this);
        System.out.println("");
        System.out.print("beq");
    }

    public void visit(LE e){
        System.out.print("cmp");
        e.e1.accept(this);
        e.e2.accept(this);
        System.out.println("");
        System.out.print("ble");
    }

    public void visit(If e){
        //if composé de e1 e2 e3
        // si e1 alors e2 sinon e3
        Id idb = Id.gen();//idbranch
        Id idf = Id.gen();//idfin

        e.e1.accept(this); // print le cmp + le bon branchement
        System.out.println(" " + idb); //label branchement pour le else
        e.e2.accept(this); // print le then
        System.out.println("b idf");

        System.out.println(idb + ":");
        e.e3.accept(this); //else

        System.out.println(idf+":"); //pour la suite du code
        //

    }

    public void visit(Let e) {
        e.e2.accept(vArgs, e.e1);
    }

    public void visit(Var e){
        System.out.print(" "+e.id);
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


