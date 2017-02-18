package com.witcher.catanaide;


import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.gc.materialdesign.views.ButtonRectangle;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hanks.htextview.HTextView;
import com.witcher.catanaide.entity.Number;
import com.witcher.catanaide.view.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by witcher on 2017/2/18 0018.
 */
public class MainActivity extends BaseTitleActivity implements View.OnClickListener, OnChartValueSelectedListener {
    private List<Number> numbers = new ArrayList<>(11);
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private boolean direction;

    private ButtonRectangle mBtRandom;
    private ButtonRectangle mBtReset;
    private RelativeLayout mRlPeople3;
    private RelativeLayout mRlPeople4;
    private CheckBox mCbPeople3;
    private CheckBox mCbPeople4;
    private HTextView mTvNumber;

    private ArrayList<Integer> colors;
    private PieChart mChart;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    private SoundPool mSoundPool;

    private int mIntCurrentPeopleNum;
    private int mIntReadyNum;
    private List<Integer> mListLastReadyNums = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initSysConfig();
        initSoundPool();
        initStatistics();
    }

    private void initSysConfig() {
        mIntCurrentPeopleNum = SPUtil.getPeopleNum(this);
        L.i("mIntCurrentPeopleNum:" + mIntCurrentPeopleNum);
        mCbPeople3.postDelayed(new Runnable() {
            @Override
            public void run() {
                setPeopleCb();
            }
        }, 500);
    }

    private void initStatistics() {
        for (int i = 2; i < 13; ++i) {
            numbers.add(new Number(0, "点数 " + i));
        }
        colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText("骰子点数统计");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setOnChartValueSelectedListener(this);
        setData();
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
    }

    private void setData() {
        List<PieEntry> list = new ArrayList<>();
        for (Number number : numbers) {
            if (number.num != 0) {
                list.add(new PieEntry(number.num, number.name));
            }
        }
        PieDataSet dataSet = new PieDataSet(list, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    private void initSoundPool() {
        mSoundPool = new SoundPool(11, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool.load(this, R.raw.sound2, 1);
        mSoundPool.load(this, R.raw.sound3, 1);
        mSoundPool.load(this, R.raw.sound4, 1);
        mSoundPool.load(this, R.raw.sound5, 1);
        mSoundPool.load(this, R.raw.sound6, 1);
        mSoundPool.load(this, R.raw.sound7, 1);
        mSoundPool.load(this, R.raw.sound8, 1);
        mSoundPool.load(this, R.raw.sound9, 1);
        mSoundPool.load(this, R.raw.sound10, 1);
        mSoundPool.load(this, R.raw.sound11, 1);
        mSoundPool.load(this, R.raw.sound12, 1);
    }

    private void initViews() {
        mBtRandom = (ButtonRectangle) findViewById(R.id.random);
        mBtReset = (ButtonRectangle) findViewById(R.id.reset);
        mRlPeople3 = (RelativeLayout) findViewById(R.id.rl_people3);
        mRlPeople4 = (RelativeLayout) findViewById(R.id.rl_people4);
        mCbPeople3 = (CheckBox) findViewById(R.id.cb_people3);
        mCbPeople4 = (CheckBox) findViewById(R.id.cb_people4);
        mTvNumber = (HTextView) findViewById(R.id.tv_number);
        mChart = (PieChart) findViewById(R.id.piechart);

        mCbPeople3.setClickable(false);
        mCbPeople4.setClickable(false);


        mBtRandom.setOnClickListener(this);
        mBtReset.setOnClickListener(this);
        mRlPeople3.setOnClickListener(this);
        mRlPeople4.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MaterialMenuDrawable materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (direction) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        drawerLayout = ((DrawerLayout) findViewById(R.id.drawer_layout));
        drawerLayout.setScrimColor(Color.parseColor("#66000000"));
        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                getMaterialMenu(toolbar).setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        direction ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(android.view.View drawerView) {
                direction = true;
            }

            @Override
            public void onDrawerClosed(android.view.View drawerView) {
                direction = false;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        refreshDrawerState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.random: {
                if (isOnReady()) {
                    readyNumber();
                } else {
                    newNumber();
                }
            }
            break;
            case R.id.reset: {
                resetGame();
            }
            break;
            case R.id.rl_people3: {
                setPeopleNum(3);
            }
            break;
            case R.id.rl_people4: {
                setPeopleNum(4);
            }
            break;
        }
    }

    private void setPeopleNum(int i) {
        SPUtil.setPeopleNum(this, i);
        mIntCurrentPeopleNum = i;
        setPeopleCb();
    }

    private void setPeopleCb() {
        mCbPeople3.setChecked(mIntCurrentPeopleNum == 3);
        mCbPeople4.setChecked(mIntCurrentPeopleNum == 4);
    }

    private void resetGame() {
        numbers.clear();
        for (int i = 2; i < 13; ++i) {
            numbers.add(new Number(0, "点数 " + i));
        }
        mTvNumber.animateText("");
        setData();
        drawerLayout.closeDrawer(GravityCompat.START);
        mListLastReadyNums.clear();
        mIntReadyNum = 0;
    }

    private void readyNumber() {
        int no = Util.getNewNo();
        while (mListLastReadyNums.contains(no)) {
            no = Util.getNewNo();
        }
        mListLastReadyNums.add(no);
        mTvNumber.animateText(no + "点");
        mSoundPool.play(--no, 1, 1, 0, 0, 1);
    }

    private void newNumber() {
        int no = Util.getNewNo();
        numbers.get(no - 2).num++;
        setData();
        mTvNumber.animateText(no + "点");
        mSoundPool.play(--no, 1, 1, 0, 0, 1);
    }

    private boolean isOnReady() {
        return mIntReadyNum++ < mIntCurrentPeopleNum;
    }

    private void refreshDrawerState() {
        this.direction = drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private static MaterialMenuDrawable getMaterialMenu(Toolbar toolbar) {
        return (MaterialMenuDrawable) toolbar.getNavigationIcon();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry entry = (PieEntry) e;
        Toast.makeText(this, entry.getLabel() + "出现了" + (int) entry.getY() + "次", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
