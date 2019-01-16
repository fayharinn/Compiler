package type;

public abstract class Type {
    private static int x = 0;
    public static Type gen() {
        return new TVar("?" + x++);
    }
    public abstract String toString();

    /**
     * @return : le Type de l'object
     */
    public Type getReturnType(){
        return new TUnit();
    }

    /**
     * @return : le Type de l'object en string
     */
    public  String TypeToString(){
        return "Type";
    }
}










