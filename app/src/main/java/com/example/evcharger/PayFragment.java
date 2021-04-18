package com.example.evcharger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.evcharger.SQLite.MySQliteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayFragment extends Fragment {
    private SQLiteOpenHelper litedb;

    String time;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setInterception(false);
    }

    public PayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayFragment.
     */

    public static PayFragment newInstance(String param1, String param2) {
        PayFragment fragment = new PayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        litedb = new MySQliteHelper(getContext(), "User", null, 1);
        SQLiteDatabase db = litedb.getWritableDatabase();
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        String name = null;
        String phone = null;
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("username"));
                phone = cursor.getString(cursor.getColumnIndex("phone"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        ((TextView)getView().findViewById(R.id.textView11)).setText("姓名："+name);
        ((TextView)getView().findViewById(R.id.textView12)).setText("手机号："+phone);
        ((TextView)getView().findViewById(R.id.time1)).setText("地址："+getArguments().getString("location"));


        Bundle bundle = getArguments();
        if(bundle != null)
        {
            time=  new SimpleDateFormat("yyyy-MM-dd ").format(Calendar.getInstance().getTime())+
                " "+bundle.getString("start")+"-"+bundle.getString("end");
            ((TextView)getView().findViewById(R.id.pay2)).setText("￥"+bundle.getFloat("pay"));
            ((TextView)getView().findViewById(R.id.pay1)).setText("￥"+bundle.getFloat("pay"));
            ((TextView)getView().findViewById(R.id.datatime)).setText("充电时间：" +time);
        }


        getView().findViewById(R.id.button21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox)getView().findViewById(R.id.checkBox)).isChecked()){
                    Bundle bundle = new Bundle();
                    //支付方式
                    if(((RadioButton)getView().findViewById(R.id.alipay)).isChecked()){
                        bundle.putString("payoption","Alipay");
                    }else {
                        bundle.putString("payoption","Wechatpay");
                    }
                    //优惠接口
                    bundle.putFloat("reduction",15.00f);
                    bundle.putFloat("pay",getArguments().getFloat("pay"));
                    bundle.putString("chargerid",getArguments().getString("chargerid"));
                    bundle.putString("time", time);
                    bundle.putString("location",getArguments().getString("location"));

//                    Toast.makeText(getContext(),getArguments().getString("chargerid") , Toast.LENGTH_SHORT).show();


                    NavController navController = Navigation.findNavController(getView());
                    navController.navigate(R.id.action_payFragment_to_judge_page,bundle);
                }else {
                    Toast.makeText(getContext(), "请同意条款后继续支付", Toast.LENGTH_SHORT).show();
                }




            }
        });



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay, container, false);
    }
}