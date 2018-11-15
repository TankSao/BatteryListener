package com.example.administrator.batterylistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/11/15.
 */

public class BatteryActivity extends AppCompatActivity {
    private TextView num;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        num = findViewById(R.id.num);
        this.registerReceiver(this.mBatteryReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
    }
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int level=arg1.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int scale=arg1.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int levelPercent = (int)(((float)level / scale) * 100);
            if(num!=null) {
                num.setText(levelPercent + "%");
                if (level <= 15) {
                    num.setTextColor(Color.RED);
                    if(level <= 8) {
                        showExit();
                    }
                } else {
                    num.setTextColor(Color.LTGRAY);
                }
            }
            int status = arg1.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            String strStatus = "未知状态";;
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    strStatus = "充电中";
                    num.setText(strStatus);
                    num.setTextColor(Color.GREEN);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    strStatus = "放电中";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    strStatus = "未充电";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    strStatus = "充电完成";
                    num.setText(strStatus);
                    num.setTextColor(Color.GREEN);
                    break;
            }
        }
    };

    private void showExit() {
        AlertDialog.Builder builder = null;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("电量过低，请关闭应用!");
        builder.setCancelable(false); // 将对话框设置为不可取消
        // 给按钮添加注册监听
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BatteryActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });
        builder.show();
    }
}
