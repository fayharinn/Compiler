package visitor;

import ast.*;
import ast.Float;

public class LetExpressionsVisitor implements ObjVisitor<Exp> {


    public Unit visit(Unit e) {
        return e;
    }

    public Bool visit(Bool e) {
        return e;
    }

    public Int visit(Int e) {
        return e;
    }

    public Float visit(Float e) {
        return e;
    }

    public Not visit(Not e) {
        return e;
    }

    public Neg visit(Neg e) {
        return e;
    }

    public Add visit(Add e) {
        return e ;
    }

    public Sub visit(Sub e) {
        return e;
    }

    public FNeg visit(FNeg e){
        return e;
    }

    public FAdd visit(FAdd e){
        return e;
    }

    public FSub visit(FSub e){
        return e;
    }

    public FMul visit(FMul e) {
        return e;
    }

    public FDiv visit(FDiv e){
        return e;
    }

    public Eq visit(Eq e){
        return e;
    }

    public LE visit(LE e){
        return e;
    }

    public If visit(If e){
        return e;
    }


    public Let visit(Let e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        if (e1 instanceof Let) {
            Exp e_temp = new Let(e.id, e.t, ((Let) e1).e2, e.e2);
            return new Let(((Let) e1).id, ((Let) e1).t, ((Let) e1).e1, e_temp);
        } else {
            return new Let(e.id, e.t, e1, e2);
        }
    }


    public Var visit(Var e){
        return e;
    }

    //A REVOIR POUR SAVOIR SI ON VISITE FD OU PAS
    public LetRec visit(LetRec e){
        Exp exp = e.e.accept(this);
        return new LetRec(e.fd,exp);
    }

    public App visit(App e){
        return e;
    }

    public Tuple visit(Tuple e){
        return e;
    }

    //A REVOIR POUR SAVOIR SI ON VISITE E1 OU PAS
    public LetTuple visit(LetTuple e){
        Exp e2 = e.e2.accept(this);
        return new LetTuple(e.ids,e.ts,e.e1,e2);
    }

    public Array visit(Array e){
        return e;
   }

    public Get visit(Get e){
       return e;
    }

    public Put visit(Put e){
       return e;
    }

}
