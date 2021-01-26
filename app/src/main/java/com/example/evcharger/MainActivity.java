package com.example.evcharger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;

public class MainActivity extends AppCompatActivity {
    private final Integer REQUEST_CODE_SCAN_ONE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView viewById = findViewById(R.id.textView5);
        viewById.setText("4调用回调接口");
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            //导入图片扫描返回结果
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj != null) {
                Bundle bundle = new Bundle();
                bundle.putString("testvalue",obj.getOriginalValue());
                //展示解码结果
                Navigation.findNavController(viewById).navigate(R.id.action_main_page_to_blankFragment_test,bundle);
//                TextView viewById1 = findViewById(R.id.textView10);
//                viewById1.setText(obj.getOriginalValue());
            }
        }
    }


}