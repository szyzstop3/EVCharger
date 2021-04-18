package com.example.evcharger;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.evcharger.DAO.impl.Toolimpl;
import com.example.evcharger.SQLite.MySQliteHelper;
import com.example.evcharger.vo.Charger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RC#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RC extends Fragment {
    private SQLiteOpenHelper litedb;
    private SQLiteDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RC() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RC.
     */
    // TODO: Rename and change types and number of parameters
    public static RC newInstance(String param1, String param2) {
        RC fragment = new RC();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.buttonm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager fm1 = getActivity().getSupportFragmentManager();
//                fm1.popBackStack();

                TextView Name = (TextView) getView().findViewById(R.id.editTextTextPersonName4);
                TextView Brand = (TextView) getView().findViewById(R.id.editTextTextPersonName7);
                TextView Location = (TextView) getView().findViewById(R.id.editTextTextPersonName6);

                Charger charger = new Charger();
                charger.setChargerName("" + Name.getText());
                charger.setBrand("" + Brand.getText());
                charger.setLocation("" + Location.getText());

                //定位和状态接口预留
                charger.setLatitude(40.11);
                charger.setLongitude(116.11);
                charger.setState("ok");


                litedb = new MySQliteHelper(getContext(), "User", null, 1);
                db = litedb.getWritableDatabase();
                Cursor cursor = db.query("User", null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                          charger.setUserid(Integer.parseInt(cursor.getString(cursor.getColumnIndex("userid"))));;
                    } while (cursor.moveToNext());

                }
                cursor.close();
                db.close();


                String name = "" + Name.getText();
                String brand = "" + Brand.getText();
                String location = "" + Location.getText();
                if (name.equals("") || name == "" || brand.equals("") || brand == "" || location.equals("") || location == "") {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("信息");
                    builder.setMessage("请确认输入的信息准确无误后再提交");
                    builder.setPositiveButton("是", null);
                    builder.show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            Toolimpl toolimpl = new Toolimpl();
                            toolimpl.registcharger(charger);
                          Looper.loop();
                        }
                    }).start();
                    ((MainActivity)getActivity()).chargerRS(true);
                    NavController controller = Navigation.findNavController(getView());
                    controller.navigate(R.id.action_RC_to_userinfo_page);
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
        return inflater.inflate(R.layout.fragment_r_c, container, false);
    }
}