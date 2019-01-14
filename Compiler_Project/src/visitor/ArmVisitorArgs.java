package visitor;

import ast.*;
import ast.Float;

/**
 * Created by jacquial on 10/01/19.
 */
public class ArmVisitorArgs implements VisitorArgs{

    private ArmVisitor v = new ArmVisitor();

    @Override
    public void visit(Add e, Exp e1) {
        System.out.print("add " );
        e1.accept(v);
        e.e1.accept( v);
        e.e2.accept( v);
        System.out.println("");
    }

    @Override
    public void visit(Sub e, Exp e1) {
        System.out.print("sub " );
        e1.accept(v);
        e.e1.accept( v);
        e.e2.accept( v);
        System.out.println("");
    }

    @Override
    public void visit(Unit e, Exp e1) {
        e.accept(v);
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

    }

    @Override
    public void visit(FSub e, Exp e1) {

    }

    @Override
    public void visit(FMul e, Exp e1) {

    }

    @Override
    public void visit(FDiv e, Exp e1) {

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
        //??????????????????????????????????????????
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

    }

    @Override
    public void visit(Load e, Exp e1) {

    }
}
