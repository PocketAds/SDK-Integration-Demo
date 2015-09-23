package com.adclient.android.sdksampleapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.adclient.android.sdk.listeners.ClientAdListener;
import com.adclient.android.sdk.type.AdType;
import com.adclient.android.sdk.type.ParamsType;
import com.adclient.android.sdk.view.AbstractAdClientView;
import com.adclient.android.sdk.view.AdClientInterstitial;
import com.adclient.android.sdk.view.AdClientView;

import java.util.HashMap;
import java.util.Map;

public class SampleActivity extends Activity {

    private AdClientView banner;
    private AdClientInterstitial interstitial;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        banner = (AdClientView) findViewById(R.id.bannerView);
        interstitial = new AdClientInterstitial(this);
        HashMap<ParamsType, Object> configuration = new HashMap<ParamsType, Object>();
        configuration.put(ParamsType.KEY, "YOUR_KEY_GOES_HERE");
        configuration.put(ParamsType.ADTYPE, AdType.INTERSTITIAL.toString());
        configuration.put(ParamsType.AD_SERVER_URL, "http://your.custom.server.url/");
        configuration.put(ParamsType.VIEW_BACKGROUND, Color.parseColor("blue"));
        configuration.put(ParamsType.TEXT_ALIGN, "center");
        Map<String, Object> customParams = new HashMap<String, Object>();
        customParams.put("param", "1234");
        configuration.put(ParamsType.CUSTOM, customParams);
        interstitial.setConfiguration(configuration);

        interstitial.setClientAdListener(new ClientAdListener() {
            public void onReceivedAd(AbstractAdClientView adClientView) {
                Log.d("TestApp", "--> Ad received callback.");
            }

            public void onFailedToReceiveAd(AbstractAdClientView adClientView) {
                Log.d("TestApp", "--> Ad failed to be received callback.");
            }

            public void onShowAdScreen(AbstractAdClientView adClientView) {
                Log.d("TestApp", "--> Ad show ad screen callback.");
            }

            @Override
            public void onLoadedAd(AbstractAdClientView adClientView) {
                Log.d("TestApp", "--> Ad loaded callback.");
                if (interstitial.isAdLoaded()) {
                    Log.d("TestApp", "--> Ad loaded.");
                    interstitial.show();
                } else {
                    Log.d("TestApp", "--> Ad not loaded.");
                }
            }

        });
        ((ViewGroup) findViewById(R.id.mainLayout)).addView(interstitial);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                interstitial.load();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (banner != null) {
            banner.resume();
        }
        if (interstitial != null) {
            interstitial.resume();
        }
    }

    @Override
    protected void onPause() {
        if (banner != null) {
            banner.pause();
        }
        if (interstitial != null) {
            interstitial.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (banner != null) {
            banner.destroy();
        }
        if (interstitial != null) {
            interstitial.destroy();
        }
        super.onDestroy();
    }
}
