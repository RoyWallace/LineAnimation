package etong.lineanimation;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SimpleDraweeView imageView;
    TextView textView;

    final static String imageUrl = "http://s1.dwstatic.com/group1/M00/2F/52/ba35209be5aa30edaa2b9900f689f493.jpg";
    final static String text = "动物世界";
    final static String subTitle = "春天到了，又到了交配的季节。动物世界动物世界动物世界动物世界动物世界";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (SimpleDraweeView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);

        textView.setText(text);
        imageView.setImageURI(imageUrl);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.TITLE, text);
                intent.putExtra(DetailActivity.SUBTITLE, subTitle);
                intent.putExtra(DetailActivity.IMAGEURL, imageUrl);

                ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(
                                MainActivity.this,
                                new Pair<View, String>(imageView,
                                        DetailActivity.VIEW_NAME_HEADER_IMAGE),
                                new Pair<View, String>(textView,
                                        DetailActivity.VIEW_NAME_HEADER_TITLE));
                ActivityCompat.startActivityForResult(MainActivity.this, intent, 100, activityOptions.toBundle());
            }
        });

        setExitSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames,
                                           List<View> sharedElements,
                                           List<View> sharedElementSnapshots) {

                super.onSharedElementEnd(sharedElementNames, sharedElements,
                        sharedElementSnapshots);

                for (View view : sharedElements) {
                    if (view instanceof SimpleDraweeView) {
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
