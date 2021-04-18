package com.example.evcharger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link charge_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class charge_page extends Fragment {

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

    public charge_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment charge_page.
     */
    // TODO: Rename and change types and number of parameters
    public static charge_page newInstance(String param1, String param2) {
        charge_page fragment = new charge_page();
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
        return inflater.inflate(R.layout.fragment_charge_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);





        getView().findViewById(R.id.button21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getView());
                Bundle bundle = new Bundle();
                //充电桩硬件反馈接口
                bundle.putFloat("pay",98.21f);
                bundle.putString("start","14:51");
                bundle.putString("end","15:34");
                bundle.putString("chargerid",getArguments().getString("chargerid"));
                bundle.putString("location",getArguments().getString("location"));
//                Toast.makeText(getContext(),getArguments().getString("chargerid") , Toast.LENGTH_SHORT).show();

                navController.navigate(R.id.action_charge_page_to_payFragment,bundle);
            }
        });
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            ((TextView)getView().findViewById(R.id.chargername)).setText("名称："+bundle.getString("chargername"));
            TextView tvSub = getView().findViewById(R.id.textView10);
            tvSub.setText("充电桩状态："+bundle.getString("state"));
        }
    }
}
