package com.bluewhaleyt.component.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogUtil {

    private MaterialAlertDialogBuilder dialog;

    public DialogUtil(Context context) {
        dialog = getDialog(context);
    }

    public DialogUtil(Context context, String title) {
        dialog = getDialog(context);
        setTitle(title);
    }

    public DialogUtil(Context context, String title, String message) {
        dialog = getDialog(context);
        setTitle(title);
        setMessage(message);
    }

    public void setTitle(String text) {
        dialog.setTitle(text);
    }

    public void setMessage(String text) {
        dialog.setMessage(text);
    }

    public void setCancelable(boolean canCancel) {
        dialog.setCancelable(canCancel);
    }

    public void setCancelable(boolean canCancel, boolean canCancelOutside) {
        setCancelable(canCancel);
        setCancelableTouchOutside(canCancelOutside);
    }

    public void setCancelableTouchOutside(boolean canCancel) {
        getAlertDialog().setCanceledOnTouchOutside(canCancel);
    }

    public void setPositiveButton(String text, DialogInterface.OnClickListener clickListener) {
        dialog.setPositiveButton(text, clickListener);
    }

    public void setPositiveButton(int textId, DialogInterface.OnClickListener clickListener) {
        dialog.setPositiveButton(textId, clickListener);
    }

    public void setNegativeButton(String text, DialogInterface.OnClickListener clickListener) {
        dialog.setNegativeButton(text, clickListener);
    }

    public void setNegativeButton(int textId, DialogInterface.OnClickListener clickListener) {
        dialog.setNegativeButton(textId, clickListener);
    }

    public void setNeutralButton(String text, DialogInterface.OnClickListener clickListener) {
        dialog.setNeutralButton(text, clickListener);
    }

    public void setNeutralButton(int textId, DialogInterface.OnClickListener clickListener) {
        dialog.setNeutralButton(textId, clickListener);
    }

    public void setView(int v) {
        dialog.setView(v);
    }

    public void setView(View v) {
        dialog.setView(v);
    }

    public void build() {
        create();
        show();
    }

    public void create() {
        dialog.create();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        getAlertDialog().dismiss();
    }

    public void cancel() {
        getAlertDialog().cancel();
    }

    public MaterialAlertDialogBuilder getDialog(Context context) {
        return new MaterialAlertDialogBuilder(context);
    }

    public AlertDialog getAlertDialog() {
        return dialog.create();
    }

}
