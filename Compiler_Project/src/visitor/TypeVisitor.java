package visitor;

import java.util.HashMap;

import ast.*;
import ast.Float;
import type.Type;
import utils.Id;

public interface TypeVisitor<E>  {
    E visit(Unit e,Type expType);
    E visit(Bool e,Type expType);
    E visit(Int e,Type expType);
    E visit(Float e, Type expType);
    E visit(Not e,Type expType);
    E visit(Neg e,Type expType);
    E visit(Add e,Type expType);
    E visit(Sub e,Type expType);
    E visit(FNeg e,Type expType);
    E visit(FAdd e,Type expType);
    E visit(FSub e,Type expType);
    E visit(FMul e,Type expType);
    E visit(FDiv e,Type expType);
    E visit(Eq e,Type expType);
    E visit(LE e,Type expType);
    E visit(If e,Type expType);
    E visit(Let e,Type expType);
    E visit(Var e,Type expType);
    E visit(LetRec e,Type expType);
    E visit(App e,Type expType);
    E visit(Tuple e,Type expType);
    E visit(LetTuple e,Type expType);
    E visit(Array e,Type expType);
    E visit(Get e,Type expType);
    E visit(Put e,Type expType);
}
