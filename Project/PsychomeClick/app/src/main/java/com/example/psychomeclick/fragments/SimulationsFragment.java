package com.example.psychomeclick.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.psychomeclick.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimulationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimulationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SimulationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimulationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimulationsFragment newInstance(String param1, String param2) {
        SimulationsFragment fragment = new SimulationsFragment();
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

        View view =inflater.inflate(R.layout.fragment_simulations, container, false);
        createButtons(view);
        return view;
    }
    private void createButtons(View view){
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.relativeLayoutTestContainer);

        // Inflate the layout for this fragment
        for (int i = 2023; i >= 2018; i--) {
            //create button
            String[] seasons = {"חורף","סתיו","קיץ","אביב"};

            for (int j=0;j<4;j++){
                createSingleButton(view,linearLayout, seasons[j]+" "+i);
            }
        }

    }
    private void createSingleButton(View view,LinearLayout linearLayout,String text){
        Button btn1 = new Button(view.getContext());
        btn1.setText(text);
        btn1.setId(View.generateViewId());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        //set button properties
        int marginInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        layoutParams.bottomMargin=marginInPixels;


        btn1.setLayoutParams(layoutParams);
        int textSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        btn1.setTextSize(textSize);
        btn1.setOnClickListener(v -> {

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    // Fetch the HTML content from the URL
                    Document document = Jsoup.connect("https://www.nite.org.il/psychometric-entrance-test/preparation/hebrew-practice-tests/").get();
                    Elements td = document.select("td");

                    Element span = td.select("span:containsOwn(" + text+ ")").first();
                    if (span != null) {
                        Element linkElement = span.parent();
                        String url = linkElement.attr("href");
                        System.out.println("Found link: " + url);
                        // ALL webview methods must be called on the SAME THREAD

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setDataAndType(Uri.parse(url), "application/pdf");
                        startActivity(intent);
                        //webview1.post(() -> webview1.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url));
                    } else {
                        System.out.println(text + " not found");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        });
        linearLayout.addView(btn1);

    }


    }



