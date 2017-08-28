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
    SubTitleView subTitleView;
    TextView titleTextView;
    Toolbar toolbar;
    RecyclerView recyclerView;

    String title;
    String backgroundImage;
    String subTitle;

    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        bgImageView = (SimpleDraweeView) findViewById(R.id.bgImageView);
        subTitleView = (SubTitleView) findViewById(R.id.subTitle);
        titleTextView = (TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        title = getIntent().getStringExtra(TITLE);
        subTitle = getIntent().getStringExtra(SUBTITLE);
        backgroundImage = getIntent().getStringExtra(IMAGEURL);

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
            subTitleView.setVisibility(View.VISIBLE);
            subTitleView.setText(subTitle);
            subTitleView.setTextColor(0x00ffffff);
            subTitleView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subTitleView.animate(1);
                    appBar.addOnOffsetChangedListener(DetailActivity.this);
                }
            }, 500);

            titleTextView.post(new Runnable() {
                @Override
                public void run() {
                    calculateSubTitleSize();
                }
            });
        } else {
            titleTextView.setY(ScreenUtils.dip2px(16));
            ((CollapsingToolbarLayout.LayoutParams) titleTextView.getLayoutParams()).setParallaxMultiplier(0.5f);
            subTitleView.setVisibility(View.GONE);
        }

        bgImageView.setImageURI(backgroundImage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new SimpleAdapter(list));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (!TextUtils.isEmpty(subTitle)) {
            float value = appBar.getHeight() - toolbar.getHeight() * 2;
            float a = (value + verticalOffset) / value;
            if (a < 0) {
                a = 0;
            }
            subTitleView.setValue(a);
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

    public void calculateSubTitleSize() {
        int titleWidth = titleTextView.getWidth();

        if (titleWidth > (ScreenUtils.screenWidth - getResources().getDimensionPixelSize(R.dimen.min_line) * 4)) {
            titleTextView.getLayoutParams().width = ScreenUtils.screenWidth - getResources().getDimensionPixelSize(R.dimen.min_line) * 4;
            subTitleView.getLayoutParams().width = ScreenUtils.screenWidth - getResources().getDimensionPixelSize(R.dimen.min_line) * 2;
            subTitleView.setMinLine(getResources().getDimensionPixelSize(R.dimen.min_line) * 2);
            subTitleView.requestLayout();
            titleTextView.requestLayout();
        } else if (subTitleView.getWidth() < ScreenUtils.screenWidth - getResources().getDimensionPixelSize(R.dimen.min_line) * 2) {
            if (titleWidth + getResources().getDimensionPixelSize(R.dimen.min_line) * 6 > ScreenUtils.screenWidth) {
                subTitleView.getLayoutParams().width = ScreenUtils.screenWidth - getResources().getDimensionPixelSize(R.dimen.min_line) * 2;
                subTitleView.setMinLine(subTitleView.getLayoutParams().width - titleWidth);
            } else if (subTitleView.getWidth() < titleWidth + getResources().getDimensionPixelSize(R.dimen.min_line) * 4) {
                subTitleView.getLayoutParams().width = titleWidth + getResources().getDimensionPixelSize(R.dimen.min_line) * 4;
                subTitleView.setMinLine(getResources().getDimensionPixelSize(R.dimen.min_line) * 4);
            }
            subTitleView.setMinLine(subTitleView.getWidth() - titleWidth);
            subTitleView.requestLayout();
            titleTextView.requestLayout();
        } else {
            subTitleView.setMinLine(subTitleView.getWidth() - titleWidth);
            subTitleView.requestLayout();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        subTitleView.setVisibility(View.GONE);
    }
}
