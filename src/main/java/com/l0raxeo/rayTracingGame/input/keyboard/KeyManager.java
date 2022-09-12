package com.l0raxeo.rayTracingGame.input.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public final class KeyManager implements KeyListener
{

    /**
     * All registered keys in the program.
     */
    private static final ArrayList<Key> allKeys = new ArrayList<>();

    /**
     * Updates all keys and their states.
     */
    public void update()
    {
        for (Key k : allKeys)
        {
            k.setState();

            if (k.getState() == KeyState.RELEASED)
                k.queueState(KeyState.IDLE);
            else if (k.getState() == KeyState.PRESSED)
                k.queueState(KeyState.HELD);
        }
    }

    // Both instances of each type of method do the
    // same thing, but the first one detects the key
    // associated with the key event's key code, and
    // the second one detects the key associated with
    // its character.
    /**
     * @return true if the specified key
     * associated with the key event is
     * being held.
     */
    public static boolean isHeld(int key)
    {
        for (Key k : allKeys)
        {
            if (k.getKeyCode() == key && k.getState() == KeyState.HELD)
                return true;
        }

        return false;
    }

    public static boolean isHeld(char key)
    {
        for (Key k : allKeys)
        {
            if (k.getKeyChar() == key && k.getState() == KeyState.HELD)
                return true;

            if (k.getKeyChar() == key && k.getState() == KeyState.PRESSED)
                return true;
        }

        return false;
    }

    /**
     * @return true if the specified key
     * associated with the key event is
     * being pressed.
     */
    public static boolean onPress(int key)
    {
        for (Key k : allKeys)
        {
            if (k.getKeyCode() == key && k.getState() == KeyState.PRESSED)
                return true;
        }

        return false;
    }

    public static boolean onPress(char key)
    {
        for (Key k : allKeys)
        {
            if (k.getKeyChar() == key && k.getState() == KeyState.PRESSED)
                return true;
        }

        return false;
    }

    /**
     * @return true if the specified key
     * associated with the key event is
     * being released.
     */
    public static boolean onRelease(int key)
    {
        for (Key k : allKeys)
        {
            if (k.getKeyCode() == key && k.getState() == KeyState.RELEASED)
                return true;
        }

        return false;
    }

    public static boolean onRelease(char key)
    {
        for (Key k : allKeys)
        {
            if (k.getKeyChar() == key && k.getState() == KeyState.RELEASED)
                return true;
        }

        return false;
    }

    // Implemented methods

    // Is invoked once key has gone down, and released
    @Override
    public void keyTyped(KeyEvent e) {}

    // Is invoked while key is held
    @Override
    public void keyPressed(KeyEvent e)
    {
        for (Key k : allKeys)
        {
            if (k.getState() == KeyState.HELD && k.getKeyCode() == e.getKeyCode())
                return;

            if (k.getKeyCode() == e.getKeyCode() && k.getState() == KeyState.IDLE)
            {
                k.queueState(KeyState.PRESSED);
                return;
            }
        }

        allKeys.add(new Key(e.getKeyCode(), KeyState.PRESSED, e.getKeyChar()));
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        for (Key k : allKeys)
        {
            if (k.getKeyCode() == e.getKeyCode())
                k.queueState(KeyState.RELEASED);
        }
    }

}
