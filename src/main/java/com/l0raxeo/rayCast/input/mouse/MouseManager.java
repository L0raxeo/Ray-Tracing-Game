package com.l0raxeo.rayCast.input.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MouseManager implements MouseListener, MouseMotionListener
{

    /**
     * All registered mouse buttons in the program.
     */
    private static final ArrayList<MBtn> allMBtns = new ArrayList<>();

    /**
     * Cursor position/information
     */
    public static int xMouse = 0, yMouse = 0;
    public static int xMove, yMove;
    public static int xDragged, yDragged;

    /**
     * Updates all mouse buttons and their states.
     */
    public void update()
    {
        for (MBtn mBtn : allMBtns)
        {
            mBtn.setState();

            if (mBtn.getState() == MBtnState.RELEASED)
                mBtn.queueState(MBtnState.IDLE);
            else if (mBtn.getState() == MBtnState.PRESSED)
                mBtn.queueState(MBtnState.HELD);
        }
    }

    /**
     * @return true if the specified mouse button
     * associated with the mouse button event is
     * being held.
     */
    public static boolean isHeld(int btn)
    {
        for (MBtn mBtn : allMBtns)
        {
            if (mBtn.getBtnCode() == btn && mBtn.getState() == MBtnState.HELD || mBtn.getState() == MBtnState.PRESSED)
                return true;
        }

        return false;
    }

    /**
     * @return true if the specified mouse button
     * associated with the mouse button event is
     * being pressed.
     */
    public static boolean onPress(int btn)
    {
        for (MBtn mBtn : allMBtns)
        {
            if (mBtn.getBtnCode() == btn && mBtn.getState() == MBtnState.PRESSED)
                return true;
        }

        return false;
    }

    /**
     * @return true if the specified mouse button
     * associated with the mouse button event is
     * being released.
     */
    public static boolean onRelease(int btn)
    {
        for (MBtn mBtn : allMBtns)
        {
            if (mBtn.getBtnCode() == btn && mBtn.getState() == MBtnState.RELEASED)
                return true;
        }

        return false;
    }

    // Getters

    public static int getMouseX()
    {
        return xMouse;
    }

    public static int getMouseY()
    {
        return yMouse;
    }

    public static int getMouseMoveX()
    {
        return xMove;
    }

    public static int getMouseMoveY()
    {
        return yMove;
    }

    public static int getMouseDraggedX()
    {
        return xDragged;
    }

    public static int getMouseDraggedY()
    {
        return yDragged;
    }

    // Implemented methods

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e)
    {
        for (MBtn mBtn : allMBtns)
        {
            if (mBtn.getState() == MBtnState.HELD)
                return;

            if (mBtn.getBtnCode() == e.getButton() && mBtn.getState() == MBtnState.IDLE)
            {
                mBtn.queueState(MBtnState.PRESSED);
                return;
            }
        }

        allMBtns.add(new MBtn(e.getButton(), MBtnState.PRESSED));
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        for (MBtn mBtn : allMBtns)
        {
            if (mBtn.getBtnCode() == e.getButton())
            {
                mBtn.queueState(MBtnState.RELEASED);
            }
        }

        xDragged = 0;
        yDragged = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        xDragged = -(xMouse - e.getX());
        yDragged = yMouse - e.getY();
        xMove = -(xMouse - e.getX());
        yMove = yMouse - e.getY();
        xMouse = e.getX();
        yMouse = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        xMove = -(xMouse - e.getX());
        yMove = yMouse - e.getY();
        xMouse = e.getX();
        yMouse = e.getY();
    }

}
