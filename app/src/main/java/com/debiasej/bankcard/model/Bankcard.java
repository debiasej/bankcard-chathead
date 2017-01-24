package com.debiasej.bankcard.model;

/**
 * Created by Mario de Biase on 24/1/17.
 */

public interface Bankcard {

    int bankcardWidth();

    int bankcardChatheadWidth();

    int bankcardChatheadHeight();

    int tabWidth();

    int tabHeight();

    boolean isBankcardDisplayed();

    void toggleIsBankcardDisplayed();

    int getPosX();

    int getPosY();

    void relocateTo(int x, int y);
}
