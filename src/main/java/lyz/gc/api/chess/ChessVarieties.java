package lyz.gc.api.chess;

public enum ChessVarieties{
    NONE("");

    private final String name;

    ChessVarieties(String name){
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
