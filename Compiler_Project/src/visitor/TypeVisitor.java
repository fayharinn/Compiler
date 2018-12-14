package visitor;

import java.util.HashMap;

import ast.*;
import ast.Float;
import type.Type;
import utils.Id;

public interface TypeVisitor<E>  {
    E visit(Unit e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Bool e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Int e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Float e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Not e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Neg e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Add e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Sub e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FNeg e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FAdd e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FSub e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FMul e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FDiv e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Eq e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(LE e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(If e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Let e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Var e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(LetRec e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(App e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Tuple e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(LetTuple e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Array e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Get e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Put e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
}
