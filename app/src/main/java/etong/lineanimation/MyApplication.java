package etong.lineanimation;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by hwt on 2017/8/28.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtils.init(getApplicationContext());
        Fresco.initialize(this);
    }
}
