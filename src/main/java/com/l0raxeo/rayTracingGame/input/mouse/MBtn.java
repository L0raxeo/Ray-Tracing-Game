package com.l0raxeo.rayTracingGame.input.mouse;

public class MBtn
{

    /**
     * Mouse button reference code.
     */
    private final int btnCode;
    /**
     * Current press state of button.
     */
    private MBtnState state;
    /**
     * Queued press state of button.
     */
    private MBtnState queuedState;

    // Class
    public MBtn(int btn, MBtnState state)
    {
        this.btnCode = btn;
        this.queuedState = state;
    }

    /**
     * Sets button press state to queued state.
     */
    public void setState()
    {
        this.state = this.queuedState;
    }

    /**
     * Queues specified button press state to be set.
     */
    public void queueState(MBtnState state)
    {
        this.queuedState = state;
    }

    // Getters

    /**
     * Gets code of this button.
     */
    public int getBtnCode()
    {
        return btnCode;
    }

    /**
     * Gets current state of mouse button.
     */
    public MBtnState getState()
    {
        return state;
    }

}
