package main.org.example.entities;

public class Cell {
    private int x;
    private int y;
    private CellEntityType cellEntityType;
    boolean visited;

    public Cell(int x, int y, CellEntityType cellEntityType) {
        this.x = x;
        this.y = y;
        this.cellEntityType = cellEntityType;
        this.visited = false;
    }

    // getters & setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public CellEntityType getCellEntityType() {
        return cellEntityType;
    }
    public void setCellEntityType(CellEntityType cellEntityType) {
        this.cellEntityType = cellEntityType;
    }
    public boolean getVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public Cell getCell() {return this;}
}
