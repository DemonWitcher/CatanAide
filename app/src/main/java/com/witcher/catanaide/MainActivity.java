package com.witcher.catanaide;


import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.gc.materialdesign.views.ButtonRectangle;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hanks.htextview.HTextView;
import com.witcher.catanaide.entity.Number;
import com.witcher.catanaide.view.CustomCheckBox;
import com.witcher.catanaide.view.CustomDrawerLayout;
import com.witcher.catanaide.view.CustomSlider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;


/**
 * Created by witcher on 2017/2/18 0018.
 */
public class MainActivity extends BaseTitleActivity implements View.OnClickListener, OnChartValueSelectedListener {
    private List<Number> mListNumberCount = new ArrayList<>(11);
    private List<Integer> mListNumbers = new ArrayList<>();
    private Toolbar toolbar;
    private CustomDrawerLayout customDrawerLayout;
    private boolean direction;

    private ButtonRectangle mBtRandom;
    private ButtonRectangle mBtReset;
    private RelativeLayout mRlPeople3;
    private RelativeLayout mRlPeople4;
    private CustomCheckBox mCbPeople3;
    private CustomCheckBox mCbPeople4;
    private HTextView mTvNumber;
    private CustomSlider mSliderTime;

    private ArrayList<Integer> colors;
    private PieChart mPieChart;
    private LineChart mLineChart;
    private BarChart mBarChart;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    private SoundPool mSoundPool;

    private int mIntCurrentPeopleNum;
    private int mIntReadyNum;
    private int mIntTimer;
    private final AtomicInteger mIntCurrentTimer = new AtomicInteger();
    private int mIntNumber;
    private List<Integer> mListLastReadyNums = new ArrayList<>();
    private Thread mThreadTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initSysConfig();
        initSoundPool();
        initPieChart();
        initLineChart();
        initBarChart();
    }

    private void initBarChart() {
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawGridBackground(false);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.animateY(2500);
        mBarChart.getLegend().setEnabled(false);
        setLeftAndRight(mBarChart);
    }

    private void setBarData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < mListNumbers.size(); i++) {
            yVals1.add(new BarEntry(i, mListNumbers.get(i)));
        }
        BarDataSet set1;
        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "骰子柱状图");
            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "";
                }
            });
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(true);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            mBarChart.setData(data);
            mBarChart.setFitBars(true);
        }
        mBarChart.invalidate();
    }

    private void initLineChart() {
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragDecelerationFrictionCoef(0.9f);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setHighlightPerDragEnabled(true);
        mLineChart.setPinchZoom(true);
        mLineChart.setBackgroundColor(Color.WHITE);
        mLineChart.animateX(2500);
        Legend l = mLineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
//        xAxis.setLabelCount(0);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + "";
            }
        });
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        setLeftAndRight(mLineChart);
    }

    private void setLeftAndRight(BarLineChartBase chart) {
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(12);
        leftAxis.setAxisMinimum(2);
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(11);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }

    private void setLineData() {
        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < mListNumbers.size(); i++) {
            yVals1.add(new Entry(i, mListNumbers.get(i)));
        }
//        mLineChart.getXAxis().setLabelCount(mListNumbers.size());
        LineDataSet set1;
        if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0 && mListNumbers.size() > 0) {
            set1 = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "点数走势图");
            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "";
                }
            });
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
            mLineChart.setData(data);
        }
        mLineChart.invalidate();
    }

    private void initSysConfig() {
        mIntCurrentPeopleNum = SPUtil.getPeopleNum(this);
        mIntTimer = SPUtil.getTimer(this);
        mIntCurrentTimer.set(0);
        L.i("mIntCurrentPeopleNum:" + mIntCurrentPeopleNum);
        mCbPeople3.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSliderTime.setValue(mIntTimer);
                setPeopleCb();
                mSliderTime.invalidate();
            }
        }, 300);
        mThreadTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (mThreadTimer) {
                        if (mIntCurrentTimer.get() == 0) {

                        } else {
                            mIntCurrentTimer.getAndDecrement();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setTvNumber();
                                }
                            });
                            try {
                                mThreadTimer.wait(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    }
                }
            }
        });
        mThreadTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThreadTimer.interrupt();
    }

    private void initPieChart() {
        for (int i = 2; i < 13; ++i) {
            mListNumberCount.add(new Number(0, "点数 " + i));
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
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setCenterTextTypeface(mTfLight);
        mPieChart.setCenterText("骰子点数统计");
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);
        mPieChart.setDrawCenterText(true);
        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);
        setPieData();
        mPieChart.animateY(1400, Easing.EaseInOutQuad);
        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.setEntryLabelTypeface(mTfRegular);
        mPieChart.setEntryLabelTextSize(12f);
    }

    private void setPieData() {
        List<PieEntry> list = new ArrayList<>();
        for (Number number : mListNumberCount) {
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
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
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
        mSliderTime = findViewById(R.id.slider_time);
        mBtRandom = findViewById(R.id.random);
        mBtReset = findViewById(R.id.reset);
        mRlPeople3 = findViewById(R.id.rl_people3);
        mRlPeople4 = findViewById(R.id.rl_people4);
        mCbPeople3 = findViewById(R.id.cb_people3);
        mCbPeople4 = findViewById(R.id.cb_people4);
        mTvNumber = findViewById(R.id.tv_number);
        mPieChart = findViewById(R.id.piechart);
        mLineChart = findViewById(R.id.linechart);
        mBarChart = findViewById(R.id.barchart);

//        mLineChart.setOnChartValueSelectedListener(this);
        mPieChart.setOnChartValueSelectedListener(this);

        mBtRandom.setOnClickListener(this);
        mBtReset.setOnClickListener(this);
        mRlPeople3.setOnClickListener(this);
        mRlPeople4.setOnClickListener(this);

        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MaterialMenuDrawable materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (direction) {
                    customDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    customDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        customDrawerLayout = findViewById(R.id.drawer_layout);
        customDrawerLayout.setScrimColor(Color.parseColor("#66000000"));
        customDrawerLayout.setDrawerListener(new CustomDrawerLayout.SimpleDrawerListener() {

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
        mSliderTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        customDrawerLayout.setmIsIntercept(false);
                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        customDrawerLayout.setmIsIntercept(true);
                    }
                    break;
                    case MotionEvent.ACTION_CANCEL: {
                        customDrawerLayout.setmIsIntercept(true);
                    }
                    break;
                }
                return false;
            }
        });
        mSliderTime.setOnValueChangedListener(new CustomSlider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                setTimer(value);
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

    private void setTimer(int timer) {
        mIntTimer = timer;
//        mIntCurrentTimer.set(timer);
        SPUtil.setTimer(this, timer);
    }

    private void resetGame() {
        mListNumberCount.clear();
        mListNumbers.clear();
        for (int i = 2; i < 13; ++i) {
            mListNumberCount.add(new Number(0, "点数 " + i));
        }
        mTvNumber.animateText("");
        setPieData();
//        setLineData();
        customDrawerLayout.closeDrawer(GravityCompat.START);
        mListLastReadyNums.clear();
        mIntReadyNum = 0;
        synchronized (mThreadTimer){
            mIntCurrentTimer.set(0);
            mThreadTimer.notifyAll();
        }
    }

    private void readyNumber() {
        mIntNumber = Util.getNewNo();
        while (mListLastReadyNums.contains(mIntNumber)) {
            mIntNumber = Util.getNewNo();
        }
        mListLastReadyNums.add(mIntNumber);
        mTvNumber.animateText(mIntNumber + "点");
        mSoundPool.play(mIntNumber - 1, 1, 1, 0, 0, 1);
    }

    private void newNumber() {
        synchronized (mThreadTimer){
            mIntCurrentTimer.set(mIntTimer+1);
            mThreadTimer.notifyAll();
        }
        mIntNumber = Util.getNewNo();
        mListNumberCount.get(mIntNumber - 2).num++;
        mListNumbers.add(mIntNumber);
        setPieData();
        setBarData();
        setLineData();
        mSoundPool.play(mIntNumber - 1, 1, 1, 0, 0, 1);
    }

    private void setTvNumber() {
        synchronized (mThreadTimer){
            mTvNumber.animateText(mIntNumber + "点(" + mIntCurrentTimer + ")");
        }
    }

    private boolean isOnReady() {
        return mIntReadyNum++ < mIntCurrentPeopleNum;
    }

    private void refreshDrawerState() {
        this.direction = customDrawerLayout.isDrawerOpen(GravityCompat.START);
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
