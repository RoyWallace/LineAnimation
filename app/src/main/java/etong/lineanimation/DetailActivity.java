package etong.lineanimation;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    public final static String VIEW_NAME_HEADER_IMAGE = "header_image";
    public final static String VIEW_NAME_HEADER_TITLE = "header_title";

    public final static String TITLE = "titleTextView";
    public final static String SUBTITLE = "subTitleView";
    public final static String IMAGEURL = "imageUrl";

    AppBarLayout appBar;
    CollapsingToolbarLayout collapsingToolbar;
    SimpleDraweeView bgImageView;
    SubtitleView subtitleView;
    TextView titleTextView;
    Toolbar toolbar;
    RecyclerView recyclerView;

    String title;
    String backgroundImage;
    String subTitle;

    Button button;

    List<String> list = new ArrayList<>();

    private int subtitleMargin;
    private int minLength;
    private int maxTitleWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        bgImageView = (SimpleDraweeView) findViewById(R.id.bgImageView);
        subtitleView = (SubtitleView) findViewById(R.id.subTitle);
        titleTextView = (TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        title = getIntent().getStringExtra(TITLE);
        subTitle = getIntent().getStringExtra(SUBTITLE);
        backgroundImage = getIntent().getStringExtra(IMAGEURL);

        button = (Button) findViewById(R.id.button);

        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");

        subtitleMargin = getResources().getDimensionPixelSize(R.dimen.subtitle_margin);
        minLength = getResources().getDimensionPixelSize(R.dimen.min_length);
        maxTitleWidth = ScreenUtils.screenWidth - subtitleMargin * 2 - minLength * 2;
        onAfterViews();
    }

    void onAfterViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ViewCompat.setTransitionName(bgImageView, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(titleTextView, VIEW_NAME_HEADER_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//解决SimpleDraweeView不能加载图片问题
            getWindow().setSharedElementEnterTransition(
                    DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                            ScalingUtils.ScaleType.CENTER_CROP)); // 进入
            getWindow().setSharedElementReturnTransition(
                    DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                            ScalingUtils.ScaleType.CENTER_CROP)); // 返回
        }

        collapsingToolbar.setTitle(" ");

        titleTextView.setText(title);
        if (!TextUtils.isEmpty(subTitle)) {
            ((CollapsingToolbarLayout.LayoutParams) titleTextView.getLayoutParams()).setParallaxMultiplier(0.65f);
            subtitleView.setVisibility(View.VISIBLE);
            subtitleView.setText(subTitle);
            subtitleView.setTextColor(0x00ffffff);
            subtitleView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subtitleView.animate(1);
                    appBar.addOnOffsetChangedListener(DetailActivity.this);
                }
            }, 500);

            titleTextView.post(new Runnable() {
                @Override
                public void run() {
                    calculateSubTitle();
                }
            });
        } else {
            titleTextView.setY(ScreenUtils.dip2px(16));
            ((CollapsingToolbarLayout.LayoutParams) titleTextView.getLayoutParams()).setParallaxMultiplier(0.5f);
            subtitleView.setVisibility(View.GONE);
        }

        bgImageView.setImageURI(backgroundImage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new SimpleAdapter(list));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtitleView.setPercent(0);
                subtitleView.animate(1);
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (!TextUtils.isEmpty(subTitle)) {
            float value = appBar.getHeight() - toolbar.getHeight() * 2;
            float p = (value + verticalOffset) / value;
            if (p < 0) {
                p = 0;
            }
            subtitleView.setPercent(p);
        }

//        if (Math.abs(verticalOffset) > appBarLayout.getHeight() / 2) {
//            int color = getResources().getColor(R.color.colorPrimary);
//            titleTextView.setTextColor(color);
//            toolbar.getNavigationIcon().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
//        } else {
//            int white = getResources().getColor(R.color.white);
//            toolbar.getNavigationIcon().setColorFilter(white, PorterDuff.Mode.MULTIPLY);
//            titleTextView.setTextColor(white);
//        }

    }

    public void calculateSubTitle() {
        int titleWidth = titleTextView.getWidth();

        if (titleWidth > maxTitleWidth) {
            titleTextView.getLayoutParams().width = maxTitleWidth;
            subtitleView.getLayoutParams().width = maxTitleWidth + minLength * 2;
            subtitleView.setLength(minLength * 2);
            subtitleView.requestLayout();
            titleTextView.requestLayout();
        } else if (subtitleView.getWidth() > ScreenUtils.screenWidth - subtitleMargin * 2) {
            subtitleView.getLayoutParams().width = ScreenUtils.screenWidth - subtitleMargin * 2;
            subtitleView.setLength(subtitleView.getLayoutParams().width - titleWidth);
            subtitleView.requestLayout();
        } else if (subtitleView.getWidth() < titleWidth + minLength * 2) {
            subtitleView.getLayoutParams().width = titleWidth + minLength * 2;
            subtitleView.setLength(subtitleView.getLayoutParams().width - titleWidth);
            subtitleView.requestLayout();
        } else {
            subtitleView.setLength(subtitleView.getLayoutParams().width - titleWidth);
            subtitleView.requestLayout();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        subtitleView.setVisibility(View.GONE);
    }
}
