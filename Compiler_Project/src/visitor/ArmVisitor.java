package visitor;

import ast.*;
import ast.Float;
import utils.Id;

import java.util.*;

public class ArmVisitor implements Visitor { //passage de param en + donc implemente pas visitor
	
	//HashMap<String,String> var; // ex : x : [fp-8]
	//private int next_fp;
	//ArrayList<String> code;

    private static ArrayList<String> fonctions = new ArrayList<>();
    private static ArmVisitorArgs vArgs;
    private static int id = 0;

	public ArmVisitor() {
		//code = new ArrayList<String>();
		vArgs = new ArmVisitorArgs(this);
		//next_fp=-8;
		//var = new HashMap<String,String>();
		
		
	}

	private int getId(){
        id++;
        return id;
    }

	public String prologue() {

		String prologue= "\n@ Prologue\r\n.text\n" +
                ".global _start\n" +
                "_start:\n" ;/*+
				"STMFD sp!, {fp, lr} @ Save Frame Pointer and Link Pointer on the stack\r\n" +
				"ADD fp, sp, #4 @ Position Frame Pointer on the address of old Frame Pointer\r\n" +
				"SUB sp, #16 @ Allocate memory to store local variables\n";*/
		System.out.println(prologue);
		return prologue;
	}
	
	public String epilogue() {
		String epilogue = "\n@ Epilogue\r\n" +
				"SUB sp, fp, #4\r\n" +
				"LDMFD sp!, {fp, lr}\r\n" +
				"BX lr\n";
		System.out.println(epilogue);
		return epilogue;
	}
	
	
    public void visit(Unit e) {
        //System.out.print("()");
    }

    public void visit(Bool e) {
        System.out.print(" "+ (e.b? "#1" : "#0"));
    }

    public void visit(Int e) {
        System.out.print(" #" +e.i);
    }

    public void visit(Float e) {

    }

    public void visit(Not e) {
        System.out.println("");
        System.out.print("cmp");
        e.e.accept(this);
        System.out.println(", #0");
        System.out.print("moveq");
        e.e.accept(this);
        System.out.println(", #1");
        System.out.print("movne");
        e.e.accept(this);
        System.out.println(", #0");

       /* cmp	r3, #0
        moveq	r3, #1
        movne	r3, #0*/
    }

    public void visit(Neg e) { // pour le neg on soustrait a zero la valeur
        System.out.print("sub");
        e.e.accept(this);
        System.out.print(", #0");
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
        System.out.print(",");
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
        System.out.print(",");
        e.e2.accept(this);
        System.out.println("");
        System.out.print("beq ");
    }

    public void visit(LE e){
        System.out.print("cmp");
        e.e1.accept(this);
        System.out.print(",");
        e.e2.accept(this);
        System.out.println("");
        System.out.print("ble ");
    }

    public void visit(If e){
        //if composé de e1 e2 e3
        // si e1 alors e2 sinon e3
        String idb = "else" + getId();//idbranch
        String idf = "endif" + getId();//idfin

        e.e1.accept(this); // print le cmp + le critère de comparaison
        System.out.println(idb); //nom du branchement si c valide
        e.e3.accept(this); // print le else
        System.out.println("b " + idf); // branchement vers la suite du code

        System.out.println(idb + ":");
        e.e2.accept(this); //then

        System.out.println(idf+":"); //pour la suite du code
        //

    }

    public void visit(Let e) {

        if(e.e1 instanceof Var || e.e1 instanceof Int || e.e1 instanceof Bool || e.e1 instanceof Unit) { // si 1 ligne mov suffit
            System.out.print("mov " + e.id + ",");
            e.e1.accept(this);
            System.out.println();
        } else { // si c'est un add/sub/... alors se suffit à lui même; pas besoin de mov
            e.e1.accept(vArgs, e);
        }
        if(e.e1 instanceof App){ //si c'est un appel de fonction, on recupère la valeur de retour (mise dans r0) et on la met là ou le let veut
            System.out.println("mov " + e.id + ", r0");
            System.out.println("LDMFD sp!, {r0-r3, r12, lr}");
        }
        e.e2.accept(vArgs, e); //au cas ou la suite est un noeud qui a besoin de visitarggs
        if(e.e2 instanceof App){ //si c'est un appel de fonction, on recupère la valeur de retour (mise dans r0) et on la met là ou le let veut
            System.out.println("mov " + e.id + ", r0");
            System.out.println("LDMFD sp!, {r0-r3, r12, lr}");
        }
    }

    public void visit(Var e){
        if((e.id.toString().charAt(0) != 'r' && !e.id.toString().equals("sp")) && !fonctions.contains(e.id.toString())) { //Si ce n'est pas un registre et que la fonction n'est pas connue
            System.out.print(" min_caml_" + e.id);
        } else {
            System.out.print(" " + e.id);
        }
    }


    public void visit(LetRec e){
        fonctions.add(e.fd.id.toString());
        System.out.println(e.fd.id + ":"); //label
        //sauvegarde des registres (callee save)
        System.out.println("STMFD sp!, {r4-r10, fp, sp}");
        System.out.println("mov fp, sp");
        e.fd.e.accept(this);
        System.out.println("LDMFD sp!, {r4-r10, fp, sp}");
        System.out.println("bx lr");
        if(!(e.e instanceof LetRec)){
            prologue();
        }
        e.e.accept(this);
    }

    public void visit(App e){
        //sauvegarde des registres (Caller save)
        System.out.println("STMFD sp!, {r0-r3, r12, lr}");
        //mettre les arguments dans les registres
        int i = 0;
        for( Exp arg : e.es){
            System.out.print("mov r" + i + ",");
            arg.accept(this); //registre dans lequel est l'info
            System.out.println();
            i++;
        }
        //brancher vers corps fonction
        System.out.print("bl");
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
        System.out.print("str " + e.id + ", [fp, #");
        System.out.println(e.stackOffset +"]");
        e.e.accept(this);
    }

    public void visit(Load e) {
        System.out.print("ldr " + e.id + ", [fp, #");
        System.out.println(e.stackOffset +"]");
        e.e.accept(this);
    }
}


