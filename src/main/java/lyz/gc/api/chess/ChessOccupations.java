package lyz.gc.api.chess;

public enum ChessOccupations {
    NONE("");

    private final String name;

    ChessOccupations(String name){
        this.name = name;
    }

    public String toString()
    {
        return this.getName();
    }

    public String getName()
    {
        return this.name;
    }

}
