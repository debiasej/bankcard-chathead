package com.debiasej.bankcard.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.debiasej.bankcard.R;
import com.debiasej.bankcard.model.Bankcard;
import com.debiasej.bankcard.model.BankcardImpl;


/**
 * Created by Mario de Biase on 19/1/17.
 */

public class BankCardView extends View {

    private static final int ANIMATION_FRAME_RATE = 30;	// Animation frame rate per second.

    private Bankcard bankcard;
    private Context context;
    private WindowManager windowManager;
    private WindowManager.LayoutParams rootLayoutParams;
    private RelativeLayout rootLayout;
    private RelativeLayout contentContainerLayout;

    private int screenHeight;

    // Controls for animations
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

        rootLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.bankcard_view, null);
        contentContainerLayout = (RelativeLayout) rootLayout.findViewById(R.id.content_container);
        contentContainerLayout.setOnTouchListener(new BankcardTouchListener());

        bankcard = new BankcardImpl(context, rootLayout);

        rootLayoutParams = new WindowManager.LayoutParams(
                bankcard.bankcardChatheadWidth(),
                bankcard.bankcardChatheadHeight(),
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        rootLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;

        windowManager.addView(rootLayout, rootLayoutParams);

        createSubviews();
    }

    private void createSubviews() {

        rootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Setup bank card Imageview
                ImageView backCard = new ImageView(context);
                backCard.setBackgroundResource(R.drawable.bank_card);

                RelativeLayout.LayoutParams params =
                        new RelativeLayout.LayoutParams(bankcard.bankcardWidth(), bankcard.bankcardChatheadHeight());
                backCard.setLayoutParams(params);

                contentContainerLayout.addView(backCard);

                // Setup tab
                ImageView tab = new ImageView(context);
                tab.setBackgroundResource(R.drawable.tab_background);

                RelativeLayout.LayoutParams tabParams =
                        new RelativeLayout.LayoutParams(bankcard.tabWidth(), bankcard.tabHeight());
                tabParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                tabParams.addRule(RelativeLayout.CENTER_VERTICAL);
                tab.setLayoutParams(tabParams);
                contentContainerLayout.addView(tab);

                // Setup the root layout
                rootLayoutParams.x = -bankcard.bankcardWidth();
                rootLayoutParams.y = (screenHeight) / 4;
                windowManager.updateViewLayout(rootLayout, rootLayoutParams);

                // Make everything visible
                rootLayout.setVisibility(View.VISIBLE);

                // Animate bank card
                timerTask = new BankcardAnimationTimerTask(bankcard, screenHeight);
            }
        }, ANIMATION_FRAME_RATE);
    }

    private class BankcardTouchListener implements OnTouchListener {

        private float initX;
        private float lastX;
        private float lastY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

           return moveBankcard(event.getActionMasked(), event.getRawX(), event.getRawY());
        }

        // Move the bank card when it is dragged.
        private boolean moveBankcard(int action, float newX, float newY){

            switch (action) {
                case MotionEvent.ACTION_DOWN:

                    // Cancel any currently movement.
                    if (timerTask!=null){
                        timerTask.cancel();
                    }

                    // Save the initial coordinates
                    initX = lastX = newX;
                    lastY = newY;
                    break;

                case MotionEvent.ACTION_MOVE:
                    updateBankcardPositionAccordingDrag(newX, newY);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:

                    displayOrHideBankcard(newX);
                    timerTask = new BankcardAnimationTimerTask(bankcard, screenHeight);
                    break;

                default:
                    return false;
            }

            return  true;
        }

        private void updateBankcardPositionAccordingDrag(float newX, float newY) {

            float deltaX = newX-lastX;
            float deltaY = newY-lastY;

            rootLayoutParams.x += deltaX;
            rootLayoutParams.y += deltaY;
            windowManager.updateViewLayout(rootLayout, rootLayoutParams);

            lastX = newX;
            lastY = newY;
        }

        private void displayOrHideBankcard(float newX) {

            boolean isDisplayedAndDragLeft = ( bankcard.isBankcardDisplayed() && (newX-initX) <= 0 );
            boolean isHiddenAndDragRight = ( !bankcard.isBankcardDisplayed() && (newX-initX) >= 0 );

            if ( isDisplayedAndDragLeft || isHiddenAndDragRight )
                bankcard.toggleIsBankcardDisplayed();
        }
    }

    public void removeBankcardView () {
        if (rootLayout != null) {
            timerTask.cancel();
            windowManager.removeView(rootLayout);
        }
    }
}
