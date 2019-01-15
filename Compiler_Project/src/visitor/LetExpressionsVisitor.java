package visitor;

import ast.*;
import ast.Float;

/**
 * Classe permettant d'applatir les noeuds Let imbriqués
 */
public class LetExpressionsVisitor implements ObjVisitor<Exp> {

    /**
     * Fonction permettant de "recoller" la suite du code au bon endroit dans l'arbre
     * @param e2 la branche e2 lors de l'appel de la fonction
     * @param e le noeud plus haut pour interchanger
     * @return Renvoie un noeud "applati" dans l'ordre
     */
    public Exp concat(Let e2, Let e) {
        if (e2.e2 instanceof Let) {
            return new Let(e2.id,e2.t,e2.e1, concat((Let) e2.e2,e));
        } else {
            Exp new_e = new Let(e.id,e.t,e2.e2,e.e2);
            return new Let(e2.id,e2.t,e2.e1,new_e);
        }
    }

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
        if (e.e1 instanceof  Let) {
            Exp e1 = e.e1.accept(this);
            Exp e2 = e.e2.accept(this);
            Exp new_e = new Let(e.id,e.t,e1,e2);
            if (((Let) e1).e2 instanceof Let) {
                return new Let(((Let) e1).id, ((Let) e1).t, ((Let) e1).e1, concat((Let) ((Let) e1).e2, (Let) new_e));
            } else {
                new_e = new Let(e.id,e.t,((Let) e1).e2,e2);
                return new Let(((Let) e1).id, ((Let) e1).t, ((Let) e1).e1, new_e);
            }
        } else {
            Exp e2 = e.e2.accept(this);
            return new Let(e.id,e.t,e.e1,e2);
        }
    }


    public Var visit(Var e){
        return e;
    }

    public LetRec visit(LetRec e){
        Exp exp = e.e.accept(this);
        Exp efd = e.fd.e.accept(this);
        FunDef fd = new FunDef(e.fd.id,e.fd.type,e.fd.args,efd);
        return new LetRec(fd,exp);
    }

    public App visit(App e){
        return e;
    }

    public Tuple visit(Tuple e){
        return e;
    }

    public LetTuple visit(LetTuple e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new LetTuple(e.ids,e.ts,e1,e2);
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
