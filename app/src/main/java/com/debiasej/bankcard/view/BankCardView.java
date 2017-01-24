package com.debiasej.bankcard.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.debiasej.bankcard.R;

import java.util.Timer;

/**
 * Created by Mario de Biase on 19/1/17.
 */

public class BankCardView extends View {

    private static final int BANK_CARD_HEIGHT = 250; 	// Card's height in dps
    private static final int TAB_WIDTH = 30;            // Tab's width in dps
    private static final double BANKCARD_RATE = 1.55;
    private static final int TAB_RATE = 3;

    private Context context;
    private WindowManager windowManager;
    private WindowManager.LayoutParams rootLayoutParams;
    private RelativeLayout rootLayout;
    private RelativeLayout contentContainerLayout;

    private int screenHeight;
    private int screenWidth;
    private int bankCardWidth;

    private BankcardAnimationTimerTask timerTask;

    public BankCardView(Context context) {
        super(context);
        this.context = context;
        init();


    }

    public BankCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }
    public BankCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void init() {

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        bankCardWidth = (int) Math.round( BANKCARD_RATE * BANK_CARD_HEIGHT );

        rootLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.bankcard_view, null);
        contentContainerLayout = (RelativeLayout) rootLayout.findViewById(R.id.content_container);
        contentContainerLayout.setOnTouchListener(new BankcardTouchListener());

        rootLayoutParams = new WindowManager.LayoutParams(bankCardWidth + TAB_WIDTH, BANK_CARD_HEIGHT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        rootLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;

        windowManager.addView(rootLayout, rootLayoutParams);

        createSubviews();
    }

    private void createSubviews() {

        rootLayout.post(new Runnable() {
            @Override
            public void run() {

                // Setup Bank card imageview
                ImageView backCard = new ImageView(context);
                backCard.setBackgroundResource(R.drawable.bank_card);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(bankCardWidth, BANK_CARD_HEIGHT);
                backCard.setLayoutParams(params);

                contentContainerLayout.addView(backCard);

                // Setup tab
                int tabHeight = TAB_WIDTH * TAB_RATE;
                ImageView tab = new ImageView(context);
                tab.setBackgroundResource(R.drawable.tab_background);

                RelativeLayout.LayoutParams tabParams = new RelativeLayout.LayoutParams(TAB_WIDTH, tabHeight);
                tabParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                tabParams.addRule(RelativeLayout.CENTER_VERTICAL);
                tab.setLayoutParams(tabParams);
                contentContainerLayout.addView(tab);

                // Setup the root layout
                rootLayoutParams.x = 0;
                rootLayoutParams.y = (screenHeight) / 4;
                windowManager.updateViewLayout(rootLayout, rootLayoutParams);

                // Make everything visible
                rootLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    private class BankcardTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            final int action = event.getActionMasked();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Manage the events
                    Toast.makeText(context, "View touched - onTouchEvent", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return false;
            }
            return true;

        }
    }
}
