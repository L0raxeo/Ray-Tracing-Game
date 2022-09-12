package com.l0raxeo.rayTracingGame.input.keyboard;

public class Key
{

    /**
     * Key identifier through its code.
     */
    private final int keyCode;

    /**
     * Key identifier in the form of individual character representation.
     */
    private final char keyChar;

    /**
     * Current press state of key on keyboard.
     */
    private KeyState state;

    /**
     * Queued state to be set to the key.
     */
    private KeyState queuedState;

    // Class
    public Key(int key, KeyState state, char keyChar)
    {
        this.keyCode = key;
        this.queuedState = state;
        this.keyChar = keyChar;
    }

    public void setState()
    {
        this.state = this.queuedState;
    }

    public void queueState(KeyState state)
    {
        this.queuedState = state;
    }

    // Getters

    public int getKeyCode()
    {
        return keyCode;
    }

    public char getKeyChar()
    {
        return keyChar;
    }

    public KeyState getState()
    {
        return state;
    }

}
