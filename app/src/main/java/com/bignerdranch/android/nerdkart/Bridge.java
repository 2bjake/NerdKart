package com.bignerdranch.android.nerdkart;

import android.webkit.JavascriptInterface;

/**
 * Created by jakefost on 5/13/16.
 */
public class Bridge {
    private KartFragment mKartFragment;

    public Bridge(KartFragment kartFragment) {
        mKartFragment = kartFragment;
    }

    @JavascriptInterface
    public boolean tryPurchaseItem(int price) {
        return mKartFragment.tryPurchaseItem(price);
    }
}
