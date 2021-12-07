package com.tbse.wifi.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.LiveData;

import java.util.function.BiFunction;

public class ReceiverLiveData<T> extends LiveData<T> {

    private final Context context;
    private final IntentFilter filter;
    private final BiFunction<Context, Intent, T> mapFunc;

    public ReceiverLiveData(Context context, IntentFilter filter, BiFunction<Context, Intent, T> mapFunc) {
        this.context = context;
        this.filter = filter;
        this.mapFunc = mapFunc;
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        context.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onActive() {
        super.onActive();
        setValue(mapFunc.apply(context, new Intent()));
        context.registerReceiver(mBroadcastReceiver, filter);
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setValue(mapFunc.apply(context, intent));
        }
    };
}