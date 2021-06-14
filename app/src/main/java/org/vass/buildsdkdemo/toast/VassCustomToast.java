/*
 * Copyright  © VASS. All rights reserved
 * Author: chumin99 on 6/14/21, 2:43 PM
 */

package org.vass.buildsdkdemo.toast;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;

import org.vass.buildsdkdemo.R;


public class VassCustomToast {
    private static Toast mToast;

    public static Toast makeText(Activity activity, String msg, int duration, ToastType type) {
        if (mToast == null) {
            mToast = new Toast(activity);
        }
        mToast.setDuration(duration);

        View customToastView = LayoutInflater.from(activity).inflate(R.layout.item_custom_toast, (ViewGroup) activity.getWindow().getDecorView(), false);

        ToastItem toastItem = new ToastItem(msg, type);

        TextView tvMsg = customToastView.findViewById(R.id.txt_mess_custom_toast);
        ImageButton ibnClose = customToastView.findViewById(R.id.ibn_close_custom_toast);


        customToastView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(toastItem.getType().secondaryColor)));
        tvMsg.setText(toastItem.getMsg());
        tvMsg.setCompoundDrawablesWithIntrinsicBounds(type.resIconId, 0, 0, 0);

        Drawable drawable = ibnClose.getDrawable();
        drawable.setTint(Color.parseColor(toastItem.getType().primaryColor));
        ibnClose.setImageDrawable(drawable);
        ibnClose.setOnClickListener(v -> mToast.cancel());

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            mToast.setView(customToastView);
        }
        mToast.setGravity(Gravity.TOP, 0, 200);

        return mToast;
    }

    public enum ToastType {
        INFO("#2196f3", "#e9f5fe", R.drawable.ic_info_circle_solid),
        SUCCESS("#4caf50", "#edfae1", R.drawable.ic_check_circle_solid),
        WARNING("#ffb300", "#fff9e6", R.drawable.ic_warning),
        ERROR("#f44336", "#fde9ef", R.drawable.ic_error_solid);

        String primaryColor;
        String secondaryColor;
        @DrawableRes int resIconId;

        ToastType(String primaryColor, String secondaryColor, int resIconId) {
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
            this.resIconId = resIconId;
        }
    }

    public static class ToastItem {
        private String msg;
        private ToastType type;

        public ToastItem(String msg, ToastType type) {
            this.msg = msg;
            this.type = type;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ToastType getType() {
            return type;
        }

        public void setType(ToastType type) {
            this.type = type;
        }
    }
}
