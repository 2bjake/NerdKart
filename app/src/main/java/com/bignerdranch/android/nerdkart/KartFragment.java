package com.bignerdranch.android.nerdkart;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jakefost on 5/13/16.
 */
public class KartFragment extends Fragment {

    private int mCash = 1000;

    private TextView mCashTextView;
    private WebView mWebView;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kart, container, false);

        mHandler = new Handler();

        mCashTextView = (TextView)view.findViewById(R.id.cash_text_view);
        mWebView = (WebView)view.findViewById(R.id.web_view);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                result.confirm();
                return true;
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.addJavascriptInterface(new Bridge(this), "Bridge");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        updateCash();

        return view;
    }

    private void updateCash() {
        mCashTextView.setText("$" + mCash);
    }

    public synchronized boolean tryPurchaseItem(int price) {
        if (price > mCash) {
            return false;
        } else {
            mCash -= price;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateCash();
                }
            });
            return true;
        }
    }
}
