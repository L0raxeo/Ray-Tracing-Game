package com.l0raxeo.rayCast.components;

public class RayCastReceiver extends Component
{

    private boolean isCastUpon = false;
    private int raysReceived = 0;

    @Override
    public void update(double dt) {
        if (raysReceived > 0)
            raysReceived--;

        isCastUpon = raysReceived > 0;
    }

    public void reset()
    {
        isCastUpon = false;
    }

    public void onReceive()
    {
        if (raysReceived < 3)
            raysReceived++;
    }

    public boolean isCastUpon()
    {
        return isCastUpon;
    }

}
