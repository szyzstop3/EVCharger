package com.example.evcharger;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.evcharger.DAO.impl.Userimpl;
import com.example.evcharger.vo.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //一句话实现导航
        getView().findViewById(R.id.button4).setOnClickListener(Navigation.
                createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment));
        getView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
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



                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        User user = new User();
                        user.setName("sss");
                        user.setPassword("nnnnnn");
                        user.setPhone("213123");
                        user.setSex(1);
                        Boolean option =  new Userimpl().InsertUser(user);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        if(option){
                            builder.setTitle("确认" ) ;
                            builder.setMessage("数据上传成功！" ) ;
                            builder.setPositiveButton("是" ,  null );
                        }
                        else {
                            builder.setTitle("确认" ) ;
                            builder.setMessage("数据上传失败！" ) ;
                            builder.setPositiveButton("是" ,  null );
                        }
                        builder.show();
                        Looper.loop();
                    }
                }).start();


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