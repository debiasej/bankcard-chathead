package com.debiasej.bankcard;

import android.content.Context;
import android.view.WindowManager;

import com.debiasej.bankcard.view.BankCardView;

/**
 * Created by Mario de Biase on 20/1/17.
 */

public class BankcardManagerImpl implements BankcardManager {

    private Context context;
    private BankCardView bankcardView;

    public BankcardManagerImpl(Context context) {
        this.context = context;
    }

    public void initBankcardView() {
        bankcardView = new BankCardView(context);
    }

    public void removeBankcardView() {
        bankcardView.removeBankcardView();
    }
}
