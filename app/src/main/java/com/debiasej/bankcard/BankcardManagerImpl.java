package com.debiasej.bankcard;

import android.content.Context;

import com.debiasej.bankcard.view.BankcardView;

/**
 * Created by Mario de Biase on 20/1/17.
 */

public class BankcardManagerImpl implements BankcardManager {

    private Context context;
    private BankcardView bankcardView;

    public BankcardManagerImpl(Context context) {
        this.context = context;
    }

    public void initBankcardView() {
        bankcardView = new BankcardView(context);
    }

    public void removeBankcardView() {
        bankcardView.removeBankcardView();
    }
}
