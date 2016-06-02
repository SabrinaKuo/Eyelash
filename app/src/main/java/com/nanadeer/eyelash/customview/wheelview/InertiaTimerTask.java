package com.nanadeer.eyelash.customview.wheelview;

import java.util.TimerTask;

final class InertiaTimerTask extends TimerTask {

    float a;
    final float velocityY;
    final WheelView wheelView;

    InertiaTimerTask(WheelView wheelView, float velocityY) {
        super();
        this.wheelView = wheelView;
        this.velocityY = velocityY;
        a = Integer.MAX_VALUE;
    }

    @Override
    public final void run() {
        if (a == Integer.MAX_VALUE) {
            if (Math.abs(velocityY) > 2000F) {
                if (velocityY > 0.0F) {
                    a = 2000F;
                } else {
                    a = -2000F;
                }
            } else {
                a = velocityY;
            }
        }
        if (Math.abs(a) >= 0.0F && Math.abs(a) <= 20F) {
            this.wheelView.cancelFuture();
            this.wheelView.handler.sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
            return;
        }
        int i = (int) ((a * 10F) / 1000F);
        WheelView wheelView = this.wheelView;
        wheelView.totalScrollY = wheelView.totalScrollY - i;
        if (!this.wheelView.isLoop) {
            float itemHeight = this.wheelView.lineSpacingMultiplier * this.wheelView.maxTextHeight;
            if (this.wheelView.totalScrollY <= (int) ((float) (-this.wheelView.initPosition) * itemHeight)) {
                a = 40F;
                this.wheelView.totalScrollY = (int) ((float) (-this.wheelView.initPosition) * itemHeight);
            } else if (this.wheelView.totalScrollY >= (int) ((float) (this.wheelView.items.size() - 1 - this.wheelView.initPosition) * itemHeight)) {
                this.wheelView.totalScrollY = (int) ((float) (this.wheelView.items.size() - 1 - this.wheelView.initPosition) * itemHeight);
                a = -40F;
            }
        }
        if (a < 0.0F) {
            a = a + 20F;
        } else {
            a = a - 20F;
        }
        this.wheelView.handler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
    }
}
