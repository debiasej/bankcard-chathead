package com.debiasej.bankcard;

/**
 * Created by Mario de Biase on 20/1/17.
 */

public class BankcardServicePresenter {

    private final BankcardManager serviceManager;

    public BankcardServicePresenter(BankcardManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void createBankcardChathead() {
        serviceManager.initBankcardView();
    }

    public void destroyBankcardChathead() {
        serviceManager.removeBankcardView();
    }

}
