package com.l0raxeo.rayCast.maze.model;

import com.l0raxeo.rayCast.maze.algo.generation.PassageTree;

import java.util.function.Consumer;

import static com.l0raxeo.rayCast.maze.model.Cell.Type.PASSAGE;
import static com.l0raxeo.rayCast.maze.model.Cell.Type.WALL;

/**
 * This class encapsulates the internal representation of the com.l0raxeo.rayCast.maze and provides
 * methods for creating, managing and extracting information about it.
 *
 * @author Philipp Malkovsky
 */
public class Maze {

    /**
     * The height of the com.l0raxeo.rayCast.maze in cells.
     */
    private final int height;

    /**
     * The width of the com.l0raxeo.rayCast.maze in cells.
     */
    private final int width;

    /**
     * Two-dimensional array of cells representing com.l0raxeo.rayCast.maze.
     */
    private final Cell[][] grid;

    /**
     * Generates a new com.l0raxeo.rayCast.maze of given height and width.
     *
     * @param height height of a com.l0raxeo.rayCast.maze
     * @param width  width of a com.l0raxeo.rayCast.maze
     */
    public Maze(int height, int width) {
        if (height < 3 || width < 3) {
            throw new IllegalArgumentException(
                "Both the height and the width " +
                    "of the com.l0raxeo.rayCast.maze must be at least 3");
        }
        this.height = height;
        this.width = width;
        grid = new Cell[height][width];
        fillGrid();
    }

    /**
     * Generates a new square com.l0raxeo.rayCast.maze of a given size.
     *
     * @param size size of a com.l0raxeo.rayCast.maze
     */
    public Maze(int size) {
        this(size, size);
    }

    /**
     * Fills the cells of the new com.l0raxeo.rayCast.maze such that the com.l0raxeo.rayCast.maze becomes
     * simply connected, i.e. containing no loops and no detached walls.
     */
    private void fillGrid() {
        fillAlternately();
        fillGaps();
        makeEntranceAndExit();
        generatePassages();
    }

    /**
     * Creates a new cell with given coordinates and a type in the grid.
     *
     * @param row    a row in the grid
     * @param column a column in the grid
     * @param type   the new cell type
     */
    private void putCell(int row, int column, Cell.Type type) {
        grid[row][column] = new Cell(row, column, type);
    }

    /**
     * Fills every second cell with a passage and the others with a wall.
     * After this method, a com.l0raxeo.rayCast.maze looks like this:
     * <pre>
     * ██████████
     * ██  ██  ██
     * ██████████
     * ██  ██  ██
     * ██████████
     * </pre>
     */
    private void fillAlternately() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i & 1) == 0 || (j & 1) == 0) {
                    putCell(i, j, WALL);
                } else {
                    putCell(i, j, PASSAGE);
                }
            }
        }
    }

    /**
     * If the com.l0raxeo.rayCast.maze has an even height or width it is needed to fill the
     * last row or column of the grid with a wall (or, otherwise, it will
     * contain passages at the outer border).
     * <pre>
     * ████████████
     * ██  ██  ██
     * ████████████
     * ██  ██  ██
     * ████████████
     * ██  ██  ██
     * </pre>
     * becomes
     * <pre>
     * ████████████
     * ██  ██  ████
     * ████████████
     * ██  ██  ████
     * ████████████
     * ████████████
     * </pre>
     */
    private void fillGaps() {
        if (height % 2 == 0) wallLastRow();
        if (width % 2 == 0) wallLastColumn();
    }

    /**
     * Fills the last column in the grid with a wall.
     */
    private void wallLastColumn() {
        for (int i = 0; i < height; i++)
            putCell(i, width - 1, WALL);
    }

    /**
     * Fills the last row in the grid with a wall.
     */
    private void wallLastRow() {
        for (int i = 0; i < width; i++)
            putCell(height - 1, i, WALL);
    }

    /**
     * Calculates the index of the passage in the last row. For a com.l0raxeo.rayCast.maze
     * with an odd (1) and even (2) width its indices differ:
     * <pre>
     * (1) ██████  ██
     * (2) ██████  ████
     * </pre>
     *
     * @return the index of the passage in the last row
     */
    private int getExitColumn() {
        return width - 3 + width % 2;
    }

    /**
     * Puts entrance and exit passages to upper left and lower right
     * corners. For example:
     * <pre>
     * ████████████
     * ██  ██  ████
     * ████████████
     * ██  ██  ████
     * ████████████
     * ████████████
     * </pre>
     * becomes
     * <pre>
     * ██  ████████
     * ██  ██  ████
     * ████████████
     * ██  ██  ████
     * ██████  ████
     * ██████  ████
     * </pre>
     */
    private void makeEntranceAndExit() {
        putCell(0, 1, PASSAGE);
        putCell(height - 1, getExitColumn(), PASSAGE);
        if (height % 2 == 0)
            putCell(height - 2, getExitColumn(), PASSAGE);
    }

    /**
     * Creates random passages between isolated passage cells such
     * that every cell is connected to the other in one way and
     * has no cycles. For example:
     * <pre>
     * ██  ██████
     * ██  ██  ██
     * ██████████
     * ██  ██  ██
     * ██████  ██
     * </pre>
     * can become
     * <pre>
     * ██  ██████
     * ██      ██
     * ██████  ██
     * ██      ██
     * ██████  ██
     * </pre>
     *
     * @see PassageTree
     */
    private void generatePassages() {
        new PassageTree(height, width)
            .generate()
            .forEach(putCell());
    }

    /**
     * Puts a cell in the corresponding place in grid.
     *
     * @return lambda to put a cell
     */
    private Consumer<Cell> putCell() {
        return cell -> grid[cell.getRow()][cell.getColumn()] = cell;
    }

    /**
     * Return the string representation of the grid. The path
     * from the entrance to the exit can be displayed if it
     * is already found and {@code showEscape} is {@code true}.
     * The path is found on demand.
     *
     * <p>
     * For example:<br>
     * if path is already found and {@code showEscape} is
     * {@code true}
     * <pre>
     * ██░░██████████
     * ██░░░░░░██  ██
     * ██████░░██  ██
     * ██    ░░    ██
     * ██████░░██████
     * ██    ░░░░░░██
     * ██████████░░██
     * </pre>
     * if {@code showEscape} is {@code false}
     * <pre>
     * ██  ██████████
     * ██      ██  ██
     * ██████  ██  ██
     * ██          ██
     * ██████  ██████
     * ██          ██
     * ██████████  ██
     * </pre>
     *
     * @param showEscape show path or not
     * @return string representation of the com.l0raxeo.rayCast.maze
     */
    private String toString(boolean showEscape) {
        var sb = new StringBuilder();
        for (var row : grid) {
            for (var cell : row) {
                if (cell.isWall()) {
                    sb.append("██");
                } else if (showEscape && cell.isEscape()) {
                    sb.append("▓▓");
                } else {
                    sb.append("  ");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Return the string representation of the grid.
     * The path is never displayed. For example:
     * <pre>
     * ██  ██████████
     * ██      ██  ██
     * ██████  ██  ██
     * ██          ██
     * ██████  ██████
     * ██          ██
     * ██████████  ██
     * </pre>
     *
     * @return string representation of the com.l0raxeo.rayCast.maze
     */
    @Override
    public String toString() {
        return toString(false);
    }

}
