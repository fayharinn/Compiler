package visitor;

import ast.*;
import ast.Float;

/**
 * Created by jacquial on 10/01/19.
 */
public interface VisitorArgs {
    void visit(Add e, Exp e1);
    void visit(Sub e, Exp e1);
    void visit(Unit e, Exp e1);
    void visit(Bool e, Exp e1);
    void visit(Int e, Exp e1);
    void visit(Float e, Exp e1);
    void visit(Not e, Exp e1);
    void visit(Neg e, Exp e1);
    void visit(FNeg e, Exp e1);
    void visit(FAdd e, Exp e1);
    void visit(FSub e, Exp e1);
    void visit(FMul e, Exp e1);
    void visit(FDiv e, Exp e1);
    void visit(Eq e, Exp e1);
    void visit(LE e, Exp e1);
    void visit(If e, Exp e1);
    void visit(Let e, Exp e1);
    void visit(Var e, Exp e1);
    void visit(LetRec e, Exp e1);
    void visit(App e, Exp e1);
    void visit(Tuple e, Exp e1);
    void visit(LetTuple e, Exp e1);
    void visit(Array e, Exp e1);
    void visit(Get e, Exp e1);
    void visit(Put e, Exp e1);

}
