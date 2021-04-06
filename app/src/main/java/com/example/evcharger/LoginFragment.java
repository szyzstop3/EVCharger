package com.example.evcharger;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.evcharger.DAO.impl.Userimpl;
import com.example.evcharger.SQLite.MySQliteHelper;
import com.example.evcharger.vo.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private Boolean option;
    private SQLiteOpenHelper litedb;
    private SQLiteDatabase db;
    String userid = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setInterception(true);
        ((MainActivity) getActivity()).resetFirstIn();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        litedb = new MySQliteHelper(getContext(), "User", null, 1);
        db = litedb.getWritableDatabase();
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        String name = null;
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("username"));
            } while (cursor.moveToNext());
            if(name!=null){
                NavController NC = Navigation.findNavController(getView());
                NC.navigate(R.id.action_loginFragment_to_main_page);
            }
        }
        cursor.close();
        db.close();

        Button B1;
        B1 = getView().findViewById(R.id.button3);
        //设置页面导航按钮
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController NC = Navigation.findNavController(view);
                NC.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
//        getView().findViewById(R.id.button2).setOnClickListener
//                (Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_main_page));
        getView().findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView Name = (TextView) getView().findViewById(R.id.editTextTextPersonName);
                TextView Pwd = (TextView) getView().findViewById(R.id.editTextTextPassword);

                 String name = ""+Name.getText();
                 String pwd = ""+Pwd.getText();



                User user = new User();
                user.setName(name);
                user.setPassword(pwd);

                Userimpl ui =  new Userimpl();

                 if(name.isEmpty()||pwd.isEmpty()||name.equals("")||pwd.equals("")){
                     AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                     builder.setTitle("信息" ) ;
                     builder.setMessage("请输入用户名和密码后再试！" ) ;
                     builder.setPositiveButton("是", null);
                     builder.show();
                 }else {
                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                             Looper.prepare();


                             option = ui.LoginUser(user);
                             System.out.println(option);
                             Looper.loop();
                         }
                     }).start();

                     while (option==null){
                         try {
                             Thread.currentThread().sleep(100);
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                     }
                     if(option){

                         option = null;
                         NavController NC = Navigation.findNavController(view);
                         litedb = new MySQliteHelper(getContext(),"User",null,1);
                         db = litedb.getWritableDatabase();
                         ContentValues contentValues = new ContentValues();
                         contentValues.put("username",name);
                         contentValues.put("pwd",pwd);




                             new Thread(new Runnable() {
                                 @Override
                                 public void run() {
                                     Looper.prepare();
                                     userid = ui.getUserid(user);
                                     Looper.loop();
                                 }
                             }).start();

                         while (userid==null){
                             try {
                                 Thread.currentThread().sleep(100);
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }


                         contentValues.put("userid",userid);
                         db.insert("User",null,contentValues);
                         NC.navigate(R.id.action_loginFragment_to_main_page);
                     }else {
                         option = null;
                         AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                         builder.setTitle("信息" ) ;
                         builder.setMessage("用户名或密码错误！" ) ;
                         builder.setPositiveButton("是", null);
                         builder.show();
                     }
                 }


            }
        });
    }

}