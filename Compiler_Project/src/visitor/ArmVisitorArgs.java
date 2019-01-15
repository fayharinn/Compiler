package visitor;

import ast.*;
import ast.Float;
import utils.Id;

/**
 * Created by jacquial on 10/01/19.
 */
public class ArmVisitorArgs implements VisitorArgs{

    private static ArmVisitor v;

    public ArmVisitorArgs(ArmVisitor v){
        this.v = v;
    }

    @Override
    public void visit(Add e, Exp e1) {
        if(e1 instanceof Let){
            System.out.print("add "  + ((Let)e1).id);
            e.e1.accept( v);
            e.e2.accept( v);
            System.out.println("");
        } else {
            System.out.println("Ya une pouille dans le cotage add");
        }

    }

    @Override
    public void visit(Sub e, Exp e1) {
        if(e1 instanceof Let){
            System.out.print("sub "  + ((Let)e1).id);
            e.e1.accept( v);
            e.e2.accept( v);
            System.out.println("");
        } else {
            System.out.println("Ya une pouille dans le cotage sub");
        }
    }

    @Override
    public void visit(Unit e, Exp e1) {
        e.accept(new ArmVisitor());
    }

    @Override
    public void visit(Bool e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(Int e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(Float e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(Not e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(Neg e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(FNeg e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(FAdd e, Exp e1) {
        if(e1 instanceof Let){
            System.out.print("vadd "  + ((Let)e1).id);
            e.e1.accept( v);
            e.e2.accept( v);
            System.out.println("");
        } else {
            System.out.println("Ya une pouille dans le cotage vadd");
        }

    }

    @Override
    public void visit(FSub e, Exp e1) {
        if(e1 instanceof Let){
            System.out.print("vsub "  + ((Let)e1).id);
            e.e1.accept( v);
            e.e2.accept( v);
            System.out.println("");
        } else {
            System.out.println("Ya une pouille dans le cotage vsub");
        }
    }

    @Override
    public void visit(FMul e, Exp e1) {
        if (e1 instanceof Let) {
            System.out.print("nvmul " + ((Let) e1).id);
            e.e1.accept(v);
            e.e2.accept(v);
            System.out.println("");
        } else {
            System.out.println("Ya une pouille dans le cotage nvmul");
        }
    }
    @Override
    public void visit(FDiv e, Exp e1) {
            if(e1 instanceof Let){
                System.out.print("vdiv "  + ((Let)e1).id);
                e.e1.accept( v);
                e.e2.accept( v);
                System.out.println("");
            } else {
                System.out.println("Ya une pouille dans le cotage vdiv");
            }
    }

    @Override
    public void visit(Eq e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(LE e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(If e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(Let e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(Var e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(LetRec e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(App e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(Tuple e, Exp e1) {

    }

    @Override
    public void visit(LetTuple e, Exp e1) {

    }

    @Override
    public void visit(Array e, Exp e1) {

    }

    @Override
    public void visit(Get e, Exp e1) {

    }

    @Override
    public void visit(Put e, Exp e1) {

    }

    @Override
    public void visit(Save e, Exp e1) {
        e.accept(v);
    }

    @Override
    public void visit(Load e, Exp e1) {
        e.accept(v);
    }
}
