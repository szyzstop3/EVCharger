package com.example.evcharger;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.evcharger.DAO.impl.Userimpl;
import com.example.evcharger.vo.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    String Ssex;
    RadioGroup rg;
    RadioButton rb_Male;
    RadioButton rb_Female;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    class MyRadioButtonListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 选中状态改变时被触发
            switch (checkedId) {
                case R.id.man:
                    Log.i("sex", "当前用户选择" + rb_Female.getText().toString());
                    Ssex = "1";
                    break;
                case R.id.woman:
                    Log.i("sex", "当前用户选择" + rb_Male.getText().toString());
                    Ssex = "0";
                    break;
            }
        }
    }

    public static boolean isMobileNO(String mobiles) {
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位
        String telRegex = "[1][3578]\\d{9}";
        return mobiles.matches(telRegex);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Ssex = "1";

        rg = (RadioGroup) getView().findViewById(R.id.sex);
        rb_Male = (RadioButton) getView().findViewById(R.id.man);
        rb_Female = (RadioButton) getView().findViewById(R.id.woman);
        rg.setOnCheckedChangeListener(new MyRadioButtonListener());


        //一句话实现导航
        getView().findViewById(R.id.button4).setOnClickListener(Navigation.
                createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment));
        getView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                TextView Name = (TextView) getView().findViewById(R.id.editTextTextPersonName2);
                TextView Pwd = (TextView) getView().findViewById(R.id.editTextTextPersonName3);
                TextView Phon = (TextView) getView().findViewById(R.id.editTextTextPersonName5);
//                User user = new User();
//                TextView Name = getView().findViewById(R.id.editTextTextPersonName2);
//                TextView Pwd = getView().findViewById(R.id.editTextTextPersonName3);
//                TextView Telep = getView().findViewById(R.id.editTextTextPersonName5);
//                RadioGroup TSex = getView().findViewById(R.id.sex);
//                RadioButton Sex = getView().findViewById(TSex.getCheckedRadioButtonId());
//                user.setName(Name.getText().toString());
//                user.setPassword(Pwd.getText().toString());
//                user.setPhone(Telep.getText().toString());
//                if(Sex.getText()=="男"){
//                    user.setSex(1);
//                }else if(Sex.getText()=="女"){
//                    user.setSex(0);
//                }
//                new Userimpl().InsertUser(user);

                String name = ""+Name.getText();
                String pwd = ""+Pwd.getText();
                String phon = ""+Phon.getText();
                if(name.equals("")||name==""||pwd.equals("")||pwd==""||phon.equals("")||phon==""){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("信息" ) ;
                    builder.setMessage("请确认输入的信息准确无误后再提交" ) ;
                    builder.setPositiveButton("是", null);
                    builder.show();
                }else {
                    if(!isMobileNO(phon)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("信息" ) ;
                        builder.setMessage("请确认输入的是正确的手机号后重新输入" ) ;
                        builder.setPositiveButton("是", null);
                        builder.show();
                    }else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                User user = new User();
                                user.setName(""+Name.getText());
                                user.setPassword(""+Pwd.getText());
                                user.setPhone(""+Phon.getText());
                                user.setSex(Integer.parseInt(Ssex));
                                Boolean option =  new Userimpl().InsertUser(user);

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                if(option){
                                    builder.setTitle("信息" ) ;
                                    builder.setMessage("恭喜注册成功！" ) ;
                                    builder.setPositiveButton("是", null);
                                }
                                else {
                                    builder.setTitle("信息" ) ;
                                    builder.setMessage("数据上传失败！" ) ;
                                    builder.setPositiveButton("是" ,  null );
                                }
                                builder.show();
                                NavController controller= Navigation.findNavController(getView());
                                controller.navigate(R.id.action_registerFragment_to_loginFragment);
                                Looper.loop();
                            }
                        }).start();
                    }


                }




                /**
                 * 设置消息弹框
                 */



            }
        });
//        BT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController NAV = Navigation.findNavController(view);
//                NAV.navigate(R.id.action_registerFragment_to_loginFragment);
//            }
//        });
    }
}

