package etong.lineanimation;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity {

    SimpleDraweeView imageView;
    TextView textView;

    final static String imageUrl = "http://s1.dwstatic.com/group1/M00/2F/52/ba35209be5aa30edaa2b9900f689f493.jpg";
    final static String text = "七夕节";
    final static String subTitle = "人家牛郎织女一年特么才见一次面\n你们居然还要庆祝";

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
                ActivityCompat.startActivity(MainActivity.this, intent, activityOptions.toBundle());
            }
        });
    }
}
