package visitor;

import ast.*;
import ast.Float;
import utils.Id;

import java.util.*;

public class ArmVisitor implements Visitor { //passage de param en + donc implemente pas visitor
	
	//HashMap<String,String> var; // ex : x : [fp-8]
	//private int next_fp;
	//ArrayList<String> code;

    private static ArmVisitorArgs vArgs;

	public ArmVisitor() {
		//code = new ArrayList<String>();
		prologue();
		vArgs = new ArmVisitorArgs(this);
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
        System.out.print("moveq");
        e.e.accept(this);
        System.out.println(" #1");
        System.out.print("movne");
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
        System.out.println(" ");
    }

    public void visit(Add e) {

    }



    public void visit(Sub e) {

    }



    public void visit(FNeg e){
        System.out.print("vneg ");
        e.e.accept(this);
        e.e.accept(this);
        System.out.println("");
    }

    public void visit(FAdd e){
        //jamais appelé
    }

    public void visit(FSub e){
        //jamais appelé
    }

    public void visit(FMul e) {
        //jamais appelé
    }

    public void visit(FDiv e){
        //jamais appelé
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
        System.out.println(" " + idb.toString()); //label branchement pour le else
        e.e2.accept(this); // print le then
        System.out.println("b " + idf.toString());

        System.out.println(idb + ":");
        e.e3.accept(this); //else

        System.out.println(idf+":"); //pour la suite du code
        //

    }

    public void visit(Let e) {
        System.out.print("mov "+ e.id);
        e.e1.accept(this);

        System.out.println(" ");
        e.e2.accept(vArgs, e.e1); //au cas ou la suite est un noeud qui a besoin de visitarggs
    }

    public void visit(Var e){
        System.out.print(" "+e.id);
    }


    public void visit(LetRec e){

        System.out.println(e.fd.id + ":"); //label
        this.prologue();
        e.fd.e.accept(this);
        this.epilogue();
        e.e.accept(this);
    }

    public void visit(App e){

        //mettre les arguments dans les registres
        int i = 0;
        for( Exp arg : e.es){
            System.out.print("add r" + i);
            arg.accept(this); //registre dans lequel est l'info
            System.out.println(" #0"); // on ajoute zero a cette valeur donc valeur inchangée
            i++;
        }
        //brancher vers corps fonction
        System.out.print("b");
        e.e.accept(this);
        System.out.println(" ");
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

    public void visit(Save e) {
        System.out.print("str " + e.id + " [fp, #");
        System.out.println(e.stackOffset +"]");
        e.e.accept(this);
    }

    public void visit(Load e) {
        System.out.print("ldr " + e.id + " [fp, #");
        System.out.println(e.stackOffset +"]");
        e.e.accept(this);
    }
}


