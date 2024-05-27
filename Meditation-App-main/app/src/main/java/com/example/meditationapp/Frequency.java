package com.example.meditationapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Frequency extends Fragment {


    public Frequency() {
        // Required empty public constructor
    }

    ArrayList<listview> headerdescription;
    ListView listView;
    list list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frequency, container, false);

        headerdescription = new ArrayList<>();
       new Thread(new Runnable() {
           @Override
           public void run() {
               Log.i(TAG, "Thread Running: "+Thread.currentThread().getId());

               headerdescription.add(new listview("МИНИ-МЕДИТАЦИЯ ДЛЯ РАССЛАБЛЕНИЯ","Сделайте расслабляющий перерыв в том, что вы делаете, чтобы погрузиться в блаженство собственного бытия.", "528HZ",R.raw.ras_lob));
               headerdescription.add(new listview("МЕДИТАЦИЯ В НАСТОЯЩЕМ МОМЕНТЕ","Ежедневная 8-минутная спокойная медитация осознанности для мощного восстановления и воссоединения с настоящим.", "428HZ",R.raw.mom_dyx));
               headerdescription.add(new listview("ПЕНИЕ ОМ","ОМ - это изначальный звук Вселенной. Это звук, который отражается во всем космосе и в каждой клетке нашего тела.", "828HZ",R.raw.ommm_m));
               headerdescription.add(new listview("АФФИРМАЦИИ","Сила благодарности притягивает любовь, изобилие, успех, крепкое здоровье и все, чего вы мечтаете достичь. ", "788HZ",R.raw.affir_m));
               headerdescription.add(new listview("МОЩНЫЕ АФФИРМАЦИИ","Аффирмации для обретения уверенности в себе, внутренней силы и дисциплинированного ума.", "328HZ",R.raw.syp_aff));
               headerdescription.add(new listview("УПРАВЛЯЕМАЯ МЕДИТАЦИЯ ДЛЯ НАЧИНАЮЩИХ","Эта 5-минутная управляемая медитация осознанности позволит вам замедлиться и осознать настоящий момент. Очистите голову и расслабьтесь в НАСТОЯЩЕМ моменте.", "228HZ",R.raw.min_nach));
               headerdescription.add(new listview("КОРОТКИЙ ОТДЫХ"," 3-минутная медитация с шаманом для расслабления, которую вы можете провести во время перерыва в работе, когда вам нужно снять стресс или успокоиться.", "128HZ",R.raw.sham_an));
               headerdescription.add(new listview("УСПОКАИВАЮЩИЕ МОРСКИЕ ВОЛНЫ","Успокойте свой разум успокаивающим шумом морских волн.", "128HZ",R.raw.oou_sh));

           }
       }).start();
         list = new list(getContext(), headerdescription);

        listView = view.findViewById(R.id.listMode);

        listView.setAdapter(list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                switch (position)
                {
                    case 1:
                    case 6:
                    case 5:
                    case 4:
                    case 3: {
                        listview abc = headerdescription.get(position);
                        MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("backgroundColor", Color.parseColor("#FF8A00"));
                        bundle.putInt("lightShadow", Color.parseColor("#FFA63D"));
                        bundle.putInt("darkShadow", Color.parseColor("#33000000"));
                        bundle.putInt("visualizer", Color.parseColor("#FF8A00"));
                        bundle.putInt("music",abc.getAudioresrc());
                        bundle.putBoolean("check",true);
                        musicPlayerFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getActivity()
                                .getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,R.anim.fadeout,R.anim.fadein,R.anim.fadeout).addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame, musicPlayerFragment);
                        fragmentTransaction.commit();
                        break;

                    }
                    case 2:
                    case 9:
                    case 7: {
                        listview abc = headerdescription.get(position);
                        MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("backgroundColor", Color.parseColor("#FF8A00"));
                        bundle.putInt("lightShadow", Color.parseColor("#FFA63D"));
                        bundle.putInt("darkShadow", Color.parseColor("#33000000"));
                        bundle.putInt("visualizer", Color.parseColor("#FF8A00"));
                        bundle.putInt("music",abc.getAudioresrc());
                        bundle.putBoolean("check",false);
                        musicPlayerFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getActivity()
                                .getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,R.anim.fadeout,R.anim.fadein,R.anim.fadeout).addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame, musicPlayerFragment);
                        fragmentTransaction.commit();
                        break;

                    }
                    default:
                    {



                            MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("backgroundColor", Color.parseColor("#FF8A00"));
                            bundle.putInt("lightShadow", Color.parseColor("#FFA63D"));
                            bundle.putInt("darkShadow", Color.parseColor("#33000000"));
                            bundle.putInt("visualizer", Color.parseColor("#FF8A00"));
                            bundle.putInt("music",R.raw.light_m);
                            bundle.putBoolean("check",true);
                            musicPlayerFragment.setArguments(bundle);

                            FragmentTransaction fragmentTransaction = getActivity()
                                    .getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,R.anim.fadeout,R.anim.fadein,R.anim.fadeout).addToBackStack(null);
                            fragmentTransaction.replace(R.id.frame, musicPlayerFragment);
                            fragmentTransaction.commit();

                        break;

                    }










                }
            }
        });


        return view;
    }


}