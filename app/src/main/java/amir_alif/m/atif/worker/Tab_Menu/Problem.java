package amir_alif.m.atif.worker.Tab_Menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import amir_alif.m.atif.worker.MainActivity;
import amir_alif.m.atif.worker.R;
import amir_alif.m.atif.worker.Share_knowledge;


public class Problem extends Fragment {
    private Button addproblem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_problem, container, false);
        addproblem = (Button)v.findViewById(R.id.addProblem);
        addproblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Share_knowledge.class));
            }
        });
        return v;
    }

}
