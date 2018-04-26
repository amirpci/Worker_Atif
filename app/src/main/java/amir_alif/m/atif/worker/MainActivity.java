package amir_alif.m.atif.worker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import amir_alif.m.atif.worker.Login_Register.Login;
import amir_alif.m.atif.worker.Tab_Menu.Problem;
import amir_alif.m.atif.worker.Tab_Menu.Profile;
import amir_alif.m.atif.worker.Tab_Menu.ToBeAnswered;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter adapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private int[] tabIcons = {R.drawable.profile ,R.drawable.problem,R.drawable.to_be_reviewed};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // logout = (Button)findViewById(R.id.logout_btn);
        //helloworld = (TextView)findViewById(R.id.helloworld);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new Profile(), "");
        adapter.AddFragment(new Problem(), "");
        adapter.AddFragment(new ToBeAnswered(), "");
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }

    }

}
