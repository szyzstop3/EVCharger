package com.example.evcharger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.evcharger.SQLite.MySQliteHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Userinfo_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Userinfo_page extends Fragment {
    private SQLiteOpenHelper litedb;

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
        ((MainActivity) getActivity()).setInterception(false);
    }

    public Userinfo_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Userinfo_page.
     */
    // TODO: Rename and change types and number of parameters
    public static Userinfo_page newInstance(String param1, String param2) {
        Userinfo_page fragment = new Userinfo_page();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(((MainActivity)getActivity()).getcresuc()){
            Toast.makeText(getContext(), "恭喜共享成功！", Toast.LENGTH_LONG).show();
            ((MainActivity)getActivity()).chargerRS(false);
        }

        getView().findViewById(R.id.button20).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_userinfo_page_to_RC));

        litedb = new MySQliteHelper(getContext(), "User", null, 1);
        SQLiteDatabase db = litedb.getWritableDatabase();
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        String name = null;
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("username"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        TextView textView = (TextView)getView().findViewById(R.id.textView11);
        textView.setText(name);

        getView().findViewById(R.id.imageView9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.action_userinfo_page_to_setFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getView().findViewById(R.id.imageView9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.action_userinfo_page_to_setFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userinfo_page, container, false);
    }
}