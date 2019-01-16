package utils;

public class Id {
    public String id;
    public Id(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return id;
    }
    static int x = -1;
    public static Id gen() {
        x++;
        return new Id("new_varid_v" + x);
    }
    
    
    public boolean equals(Id obj) {
    	  return (this.id == obj.id);
    	}

}
