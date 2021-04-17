package com.example.evcharger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.evcharger.DAO.impl.Toolimpl;
import com.example.evcharger.SQLite.MySQliteHelper;
import com.example.evcharger.vo.Comment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link judge_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class judge_page extends Fragment {
    private SQLiteOpenHelper litedb;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public judge_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment judge_page.
     */
    // TODO: Rename and change types and number of parameters
    public static judge_page newInstance(String param1, String param2) {
        judge_page fragment = new judge_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(),getArguments().getString("chargerid") , Toast.LENGTH_SHORT).show();


                Comment comment = new Comment();
//
                comment.setStars(((RatingBar)getView().findViewById(R.id.ratingBar)).getRating());
                comment.setChargerid(Integer.parseInt(getArguments().getString("chargerid")));
                comment.setPay(getArguments().getFloat("pay"));
                comment.setReduction(getArguments().getFloat("reduction"));
                comment.setPayoption(getArguments().getString("payoption"));
                comment.setComment(((TextView)getView().findViewById(R.id.comment)).getText().toString());

                litedb = new MySQliteHelper(getContext(), "User", null, 1);
                SQLiteDatabase db = litedb.getWritableDatabase();
                Cursor cursor = db.query("User", null, null, null, null, null, null);
                String userid = null;
                if (cursor.moveToFirst()) {
                    do {
                        userid = cursor.getString(cursor.getColumnIndex("userid"));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
                comment.setUserid(Integer.parseInt(userid));


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toolimpl toolimpl = new Toolimpl();
                        toolimpl.addComment(comment);
                        Looper.loop();
                    }
                }).start();

                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.action_judge_page_to_main_page);
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
        return inflater.inflate(R.layout.fragment_judge_page, container, false);
    }
}