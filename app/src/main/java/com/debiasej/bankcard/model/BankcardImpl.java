package com.debiasej.bankcard.model;

import android.content.Context;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by Mario de Biase on 24/1/17.
 */

public class BankcardImpl implements Bankcard {

    private static final int BANK_CARD_HEIGHT = 250; 	// Card's height in dps
    private static final int TAB_WIDTH = 30;            // Tab's width in dps
    private static final double BANKCARD_RATE = 1.55;
    private static final double TAB_RATE = 3;

    private WindowManager windowManager;
    private RelativeLayout rootLayout;
    private int cardWidth;
    private int tabHeight;
    private boolean isBankcardDisplayed;


    public BankcardImpl(Context context, RelativeLayout view) {

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.rootLayout = view;

        cardWidth = (int) Math.round( BANKCARD_RATE * BANK_CARD_HEIGHT );
        tabHeight = (int) Math.round( TAB_RATE * TAB_WIDTH );
        isBankcardDisplayed = true;
    }

    public int bankcardWidth() {
        return cardWidth;
    }

    public int bankcardChatheadWidth() {
        return cardWidth + TAB_WIDTH;
    }

    public int bankcardChatheadHeight() {
        return BANK_CARD_HEIGHT;
    }

    public int tabWidth() {
        return TAB_WIDTH;
    }

    public int tabHeight() {
        return tabHeight;
    }

    public boolean isBankcardDisplayed() {
        return isBankcardDisplayed;
    }

    public void toggleIsBankcardDisplayed() {
        isBankcardDisplayed = !isBankcardDisplayed;
    }

    public int getPosX() {

        int[] location = new int[2];
        rootLayout.getLocationOnScreen(location);

        return  location[0];
    }

    public int getPosY() {
        int[] location = new int[2];
        rootLayout.getLocationOnScreen(location);

        return  location[1];
    }

    public void relocateTo(int x, int y) {

        WindowManager.LayoutParams rootLayoutParams = ((WindowManager.LayoutParams)rootLayout.getLayoutParams());
        rootLayoutParams.x = x;
        rootLayoutParams.y = y;

        windowManager.updateViewLayout(rootLayout, rootLayoutParams);
    }
}
