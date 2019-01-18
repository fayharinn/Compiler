package visitor;

import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de trouver les variables libres à l'interieur d'une fonction LetRec
 */
public class freeVarVisitor implements  ObjVisitor<ArrayList<String>>{

    /**
     * Liste des variables connues dans le programme
     */
    private ArrayList<String> knownVar ;
    /**
     * Liste des variables qui n'apparaissent pas dans knownVar : des variables libres
     */
    private ArrayList<String> freeVar;

    /**
     * Constructor avec la liste des arguments de la fonction pour remplir knownVar
     * @param args liste des arguments de la fonction appelante
     */
    freeVarVisitor(List<Id> args) {
        knownVar = new ArrayList<>();
        freeVar = new ArrayList<>();
        for ( Id id : args) {
            knownVar.add(id.toString());
        }
    }

    @Override
    public ArrayList<String> visit(Unit e) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> visit(Bool e) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> visit(Int e) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> visit(Float e) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> visit(Not e) {
        return e.e.accept(this);
    }

    @Override
    public ArrayList<String> visit(Neg e) {
        return e.e.accept(this);
    }

    @Override
    public ArrayList<String> visit(Add e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(Sub e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(FNeg e) {
        return e.e.accept(this);
    }

    @Override
    public ArrayList<String> visit(FAdd e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(FSub e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(FMul e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(FDiv e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(Eq e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(LE e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(If e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        e1.addAll(e.e3.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(Let e) {
        knownVar.add(e.id.toString());
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(Var e) {
        if (knownVar.contains(e.id.toString()))
            return new ArrayList<>();
        else
            knownVar.add(e.id.toString());
            ArrayList<String> add_e = new ArrayList<>();
            add_e.add(e.id.toString());
            return add_e;
    }

    @Override
    public ArrayList<String> visit(LetRec e) {
        knownVar.add(e.fd.id.toString());
        return e.e.accept(this);
    }

    @Override
    public ArrayList<String> visit(App e) {
        ArrayList res = new ArrayList();
        for ( Exp arg : e.es) {
            res.addAll(arg.accept(this));
        }
        return res ;
    }

    @Override
    public ArrayList<String> visit(Tuple e) {
        ArrayList res = new ArrayList<>();
        for ( Exp arg : e.es) {
           res.addAll(arg.accept(this));
        }
        return res;
    }

    @Override
    public ArrayList<String> visit(LetTuple e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(Array e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(Get e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(Put e) {
        ArrayList<String> e1 = e.e1.accept(this);
        e1.addAll(e.e2.accept(this));
        e1.addAll(e.e3.accept(this));
        return e1;
    }

    @Override
    public ArrayList<String> visit(Load load) {
        return null;
    }

    @Override
    public ArrayList<String> visit(Save save) {
        return null;
    }
}
