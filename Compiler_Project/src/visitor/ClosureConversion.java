package visitor;
import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Classe permmettant de dé-imbriquer les fonctions et de traiter les variables libres dans les fonctions
 * Les closures ne sont pas totalement complétés
 */
public class ClosureConversion implements ObjVisitor<Exp> {

    /**
     * La pile des differentes declaration de function, utile pour remonter les declarations
     */
    private Stack<LetRec> declarationFun = new Stack<>();
    /**
     * Liste des fonctions simples sans variable libre
     */
    private ArrayList<String> known = new ArrayList<>();
    /**
     * HashMap qui lie une fonction avec ses variables libres
     */
    private HashMap<String,ArrayList<String>> make_closure = new HashMap<>();
    /**
     * Liste des fonctions qui necessite une closure
     */
    private ArrayList<String> apply_closure = new ArrayList<>();
    /**
     * HashMap qui lie une variable libre à la nouvelle variable en parametre de la fonction
     */
    private HashMap<String,String> replacableVar = new HashMap<>();

    /**
     * Fonction qui permet de remettre les declarations de fonctions au debut du programme
     * @param e L'arbre complet, vidé des déclarations de fonctions
     * @return Renvoie l'arbre complet avec les declarations au debut de l'arbre
     */
    public Exp moveToFront(Exp e) {
        Exp res = e;

        while (!declarationFun.empty()) {
            LetRec fun = declarationFun.pop();
            res = new LetRec(fun.fd,res);
        }

        return  res;
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
        return new Not (e.e.accept(this));
    }

    public Neg visit(Neg e) {
        return new Neg (e.e.accept(this));
    }

    public Add visit(Add e) {
        return new Add(e.e1.accept(this),e.e2.accept(this)) ;
    }

    public Sub visit(Sub e) {
        return new Sub(e.e1.accept(this),e.e2.accept(this));
    }

    public FNeg visit(FNeg e){
        return new FNeg(e.e.accept(this));
    }

    public FAdd visit(FAdd e){
        return new FAdd(e.e1.accept(this),e.e2.accept(this));
    }

    public FSub visit(FSub e){
        return new FSub(e.e1.accept(this),e.e2.accept(this));
    }

    public FMul visit(FMul e) {
        return new FMul(e.e1.accept(this),e.e2.accept(this));
    }

    public FDiv visit(FDiv e){
        return new FDiv(e.e1.accept(this),e.e2.accept(this));
    }

    public Eq visit(Eq e){
        return new Eq(e.e1.accept(this),e.e2.accept(this));
    }

    public LE visit(LE e){
        return new LE(e.e1.accept(this),e.e2.accept(this));
    }

    public If visit(If e){
        return new If(e.e1.accept(this),e.e2.accept(this),e.e3.accept(this));
    }

    public Let visit(Let e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new Let(e.id, e.t, e1, e2);
    }

    public Exp visit(Var e) {
        if (replacableVar.containsKey(e.id.toString())) {
            return new Var(new Id(replacableVar.get(e.id.toString())));
        }
        if (make_closure.containsKey(e.id.toString())) {
            ArrayList<Exp> lTuple = new ArrayList<>();
            lTuple.add(new Var(new Id(e.id.toString())));
            for (String arg : make_closure.get(e.id.toString())) {
                Var new_var = new Var(new Id(arg));
                lTuple.add(new_var);
            }
            return new Tuple(lTuple);
        } else {
            return e;
        }
    }

    public Exp visit(LetRec e){
        ArrayList<String> freeVar = e.fd.e.accept(new freeVarVisitor(e.fd.args));
        Exp e1;
        if (freeVar.isEmpty()) {
            //CAS NON VARIABLE LIBRE
            //System.out.println("PAS DE VARIABLE LIBRE");
            known.add(e.fd.id.toString());
            int size = make_closure.size();
            e1 = e.fd.e.accept(this);
            if (make_closure.size() != size) {
                apply_closure.add(e.fd.id.toString());
            }
        } else {
            //System.out.println("/! DES VARIABLE LIBRE");
            for (int i = 0 ; i < freeVar.size(); i++) {
                Id new_id = Id.gen();
                e.fd.args.add(0,new_id);
                replacableVar.put(freeVar.get(i),new_id.toString());
                //System.out.println(freeVar.get(i));
            }
            e1 = e.fd.e.accept(this);
            for (int i = 0 ; i < freeVar.size(); i++) {
                replacableVar.remove(freeVar.get(i));
                make_closure.put(e.fd.id.toString(),freeVar);
            }
        }
        FunDef fd = new FunDef(e.fd.id,e.fd.type,e.fd.args,e1);
        LetRec new_e = new LetRec(fd,e.e);
        declarationFun.push(new_e);
        return e.e.accept(this);
    }

    public Exp visit(App e){
        if (e.e instanceof Var) {
            if (known.contains(((Var) e.e).id.toString())) {
                return e;
            } if (apply_closure.contains(((Var) e.e).id.toString())) {
              // Closure non implementee
                return  e;
            } else {
                return e;
            }
        } else
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
        return new Array(e.e1.accept(this),e.e2.accept(this));
   }

    public Get visit(Get e){
       return new Get(e.e1.accept(this),e.e2.accept(this));
    }

    public Put visit(Put e){
       return new Put(e.e1.accept(this),e.e2.accept(this),e.e3.accept(this));
    }

    @Override
    public Exp visit(Load load) {
        return null;
    }

    @Override
    public Exp visit(Save save) {
        return null;
    }

}

