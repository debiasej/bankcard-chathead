package com.debiasej.bankcard;

import android.content.Context;
import android.view.WindowManager;

import com.debiasej.bankcard.view.BankCardView;

/**
 * Created by Mario de Biase on 20/1/17.
 */

public class BankcardManagerImpl implements BankcardManager {

    private Context context;

    public BankcardManagerImpl(Context context) {
        this.context = context;
    }

    public void initBankcardView() {

        BankCardView bankcardView = new BankCardView(context);
    }

}
