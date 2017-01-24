package com.debiasej.bankcard.view;

import android.os.Handler;
import com.debiasej.bankcard.model.Bankcard;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mario de Biase on 20/1/17.
 */

// Timer for animation of the bankcard.
public class BankcardAnimationTimerTask extends TimerTask {

    // Ultimate destination coordinates toward which the bankcard will move
    private int destX;
    private int destY;

    private Handler animationHandler = new Handler();
    private Timer animationtimer;

    private static final int MOVEMENT_REGION_FRACTION = 6;	// Controls fraction of y-axis on screen within which the bank card stays.
    private static final int ANIMATION_FRAME_RATE = 30;	// Animation frame rate per second.

    private Bankcard bankcard;


    public BankcardAnimationTimerTask(Bankcard bankcard, int screenHeight) {

        // Setup destination coordinates depending if the bankcard is displayed.
        super();

        this.bankcard = bankcard;

        if (!bankcard.isBankcardDisplayed()) {
            destX = -bankcard.bankcardWidth();
        } else {
            destX = 0;
        }

        // Keep upper edge of the widget within the upper limit of screen
        destY = Math.max( screenHeight/MOVEMENT_REGION_FRACTION, bankcard.getPosY() );

        // Keep lower edge of the widget within the lower limit of screen
        destY = Math.min(
                ((MOVEMENT_REGION_FRACTION-1)*screenHeight)/MOVEMENT_REGION_FRACTION - ( bankcard.bankcardWidth() + bankcard.tabWidth()),
                destY);

        animationtimer = new Timer();
        animationtimer.schedule(this, 0, ANIMATION_FRAME_RATE);
    }

    // This function is called after every frame.
    @Override
    public void run() {

        // Run the function on main UI thread.
        animationHandler.post(new Runnable() {
            @Override
            public void run() {

                // Update layout
                int x = (2*(bankcard.getPosX()-destX))/3 + destX;
                int y = (2*(bankcard.getPosY()-destY))/3 + destY;

                bankcard.relocateTo(x,y);
                cancelAnimationIfDestinationReached();
            }

            private void cancelAnimationIfDestinationReached() {
                if (Math.abs(bankcard.getPosX()-destX)<2 && Math.abs(bankcard.getPosY()-destY)<2){
                    BankcardAnimationTimerTask.this.cancel();
                    animationtimer.cancel();
                }
            }
        });
    }

    // Cancel both animation and TimerTask
    @Override
    public boolean cancel() {
        boolean result = super.cancel();
        animationtimer.cancel();

        return result;
    }
}