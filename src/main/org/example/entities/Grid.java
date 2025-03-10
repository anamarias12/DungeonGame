package main.org.example.entities;

import main.org.example.characters.Character;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Grid extends ArrayList<ArrayList<Cell>> {
    int width, length;
    Character crtCharacter;
    Cell crtCell;
    private final Game game;

    //constructorul
    public Grid(int length, int width, Game game) {
        this.length = length;
        this.width = width;
        this.game = game;
        for (int i = 0; i < length; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            this.add(row);
        }
    }

    // getters & setters
    public Character getCrtCharacter() {
        return crtCharacter;
    }
    public void setCrtCharacter(Character crtCharacter) {
        this.crtCharacter = crtCharacter;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
   }
    public int getLength() {
        return  length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public Cell getCrtCell() {
        return crtCell;
    }
    public void setCrtCell(Cell crtCell) {
        this.crtCell = crtCell;
    }

    // genereaza un grid cu continut
    public static Grid generateGrid(Game game) {
        Random random = new Random();
        int length = random.nextInt(5) + 5;
        int width = random.nextInt(5) + 5;
        Grid grid = new Grid(length, width, game);
        grid.addSpecialCells(CellEntityType.SANCTUARY, 2);
        grid.addSpecialCells(CellEntityType.ENEMY, 4);
        grid.addSpecialCells(CellEntityType.PORTAL, 1);
        grid.addPlayer();
        return grid;
    }

    // completeaza caracterele speciale
    private void addSpecialCells(CellEntityType type, int count) {
        Random random = new Random();
        int placed = 0;
        while (placed < count) {
            int x = random.nextInt(length);
            int y = random.nextInt(width);
            Cell cell = this.get(x).get(y);
            if (cell.getCellEntityType() == CellEntityType.VOID) {
                cell.setCellEntityType(type);
                placed++;
            }
        }
    }

    // completeaza player
    private void addPlayer() {
        Random random = new Random();
        while (true) {
            int x = random.nextInt(length);
            int y = random.nextInt(width);
            Cell cell = this.get(x).get(y);
            if (cell.getCellEntityType() == CellEntityType.VOID) {
                crtCell = cell;
                cell.setCellEntityType(CellEntityType.PLAYER);
                break;
            }
        }
    }

    // sistemul de miscare in grid
    public Cell goNorth() {
        try {
            if (crtCell.getX() - 1 < 0) {
                throw new Exception("Cannot move north! Out of bounds.");
            }
            return handleMove(crtCell.getX() - 1, crtCell.getY());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Movement Error", JOptionPane.ERROR_MESSAGE);
            return crtCell;
        }
    }
    public Cell goSouth() {
        try {
            if (crtCell.getX() + 1 >= length) {
                throw new Exception("Cannot move south! Out of bounds.");
            }
            return handleMove(crtCell.getX() + 1, crtCell.getY());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Movement Error", JOptionPane.ERROR_MESSAGE);
            return crtCell;
        }
    }
    public Cell goWest() {
        try {
            if (crtCell.getY() - 1 < 0) {
                throw new Exception("Cannot move west! Out of bounds.");
            }
            return handleMove(crtCell.getX(), crtCell.getY() - 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Movement Error", JOptionPane.ERROR_MESSAGE);
            return crtCell;
        }
    }
    public Cell goEast() {
        try {
            if (crtCell.getY() + 1 >= width) {
                throw new Exception("Cannot move east! Out of bounds.");
            }
            return handleMove(crtCell.getX(), crtCell.getY() + 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Movement Error", JOptionPane.ERROR_MESSAGE);
            return crtCell;
        }
    }

    boolean use = true;
    public void setUse(boolean use) {
        this.use = use;
    }
    // gestioneaza miscarea bazat pe tipul de celula
    private Cell handleMove(int newX, int newY) {
        Cell targetCell = this.get(newX).get(newY);
        switch (targetCell.getCellEntityType()) {
            case ENEMY:
                Enemy enemy = new Enemy();
                crtCharacter.getAbilities().clear();
                crtCharacter.generateRandomAbilities();
                enemy.getAbilities().clear();
                enemy.generateRandomAbilities();
                game.setUpFight(enemy, true);
                break;
            case SANCTUARY:
                game.setUpSanctuaryPage();
                break;
            case PORTAL:
                game.portalPage();
                break;
            case VOID:
                break;
        }
        Cell prevCell = crtCell;
        crtCell = targetCell;
        if (!use) {
            prevCell.setVisited(true);
            prevCell.setCellEntityType(CellEntityType.SANCTUARY);
            crtCell.setVisited(true);
            crtCell.setCellEntityType(CellEntityType.PLAYER);
            use = true;
        } else {
            prevCell.setVisited(true);
            prevCell.setCellEntityType(CellEntityType.VOID);
            crtCell.setVisited(true);
            crtCell.setCellEntityType(CellEntityType.PLAYER);
        }

        return crtCell;
    }

    public Cell[][] getGridStructure() {
        Cell[][] structure = new Cell[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                structure[i][j] = this.get(i).get(j).getCell();
            }
        }
        return structure;
    }
}
