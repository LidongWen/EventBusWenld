package wenld.github.eventbuswenld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import wenld.github.eventbus_002.EventBus;
import wenld.github.eventbus_002.Subscribe;
import wenld.github.eventbus_002.ThreadMode;

import static wenld.github.eventbuswenld.TwoActivity.getCurrentMethodName;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_one;
    private Button btn_;
    private Button btn_2;
    private Button btn_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        tv_one = (TextView) findViewById(R.id.tv_one);
        btn_ = (Button) findViewById(R.id.btn_);

        btn_.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_go = (Button) findViewById(R.id.btn_go);
        btn_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_:
                EventBus.getDefault().postSticky("小子，我是哥哥");

                break;
            case R.id.btn_2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().postSticky("小子，我是哥哥");
                    }
                }).start();
                break;
            case R.id.btn_go:
                Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Subscribe()
    public void setText(EventNotify str) {
        Log.e("MainActivity", "methodName :" + getCurrentMethodName() + "   thread : " + Thread.currentThread().getId() + "  msg :" + str);
        tv_one.setText("TwoActivity:" + str.str);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void setTextASY(EventNotify str) {
        Log.e("MainActivity", "methodName :" + getCurrentMethodName() + "   thread : " + Thread.currentThread().getId() + "  msg :" + str);
        tv_one.setText("TwoActivity:" +  str.str);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void setTextInMain(EventNotify str) {
        Log.e("MainActivity", "methodName :" + getCurrentMethodName() + "   thread : " + Thread.currentThread().getId() + "  msg :" + str);
        tv_one.setText("TwoActivity:" +  str.str);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unRegister(this);
        super.onDestroy();
    }
}
