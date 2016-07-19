package com.deepblue.customviewcalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.deepblue.customviewcalendar.CalendarView.BadgeTextView;
import com.deepblue.customviewcalendar.CalendarView.CalendarSign;

public class MainActivity extends AppCompatActivity {

    private CalendarSign calendarSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
    }

    private void initWidget() {
        calendarSign = (CalendarSign) findViewById(R.id.calendarsin);
        BadgeTextView todayBadgeTextView = calendarSign.getTodayBadgeTextView();
        /**
         * 启动动画
         */
        todayBadgeTextView.startDayAnim();
        /**
         * 是否被选中--选中就是粉红色
         */
//        todayBadgeTextView.setChecked(true);
        /**
         * 右上角显示数字
         */
        todayBadgeTextView.setBadgeString("+" + 100);
        final String today = (String) todayBadgeTextView.getText();
        /**
         * 点击事件
         */
        todayBadgeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "今天是:" + today, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
