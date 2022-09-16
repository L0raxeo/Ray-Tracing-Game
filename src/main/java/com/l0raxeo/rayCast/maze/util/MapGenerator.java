package com.l0raxeo.rayCast.maze.util;

import com.l0raxeo.rayCast.maze.model.Maze;

import static java.lang.Integer.parseInt;

/**
 * This class is a console wrapper for user interaction.
 * Reads an input and prints the output to the console.
 * Stores a com.l0raxeo.rayCast.maze internally.
 *
 * @author Philipp Malkovsky
 * @see Maze
 */
public class MapGenerator {

    /**
     * The current com.l0raxeo.rayCast.maze. It is null when the game starts.
     * {@code isMazeAvailable} indicates whether the
     * com.l0raxeo.rayCast.maze exists.
     */
    private static Maze maze;

    /**
     * Asks a user to enter the dimensions of the new com.l0raxeo.rayCast.maze
     * and then generates and prints the new one.
     */
    public static Maze generate(int width, int height) {

        System.out.println("Enter the size of the new com.l0raxeo.rayCast.maze (in the [size] or [height width] format)");
        maze = new Maze(height, width);
        return getMaze();
    }

    /**
     * Prints the current com.l0raxeo.rayCast.maze.
     */
    public static Maze getMaze() {
        return maze;
    }


}
