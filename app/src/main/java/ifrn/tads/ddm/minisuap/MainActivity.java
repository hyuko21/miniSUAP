package ifrn.tads.ddm.minisuap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ifrn.tads.ddm.minisuap.fragments.AboutFragment;
import ifrn.tads.ddm.minisuap.fragments.ClassesFragment;
import ifrn.tads.ddm.minisuap.fragments.ContactsFragment;
import ifrn.tads.ddm.minisuap.fragments.HomeFragment;
import ifrn.tads.ddm.minisuap.fragments.ProfileFragment;
import ifrn.tads.ddm.minisuap.models.Student;

public class MainActivity extends FragmentActivity {

    private Student student;
    private String registration, password;
    private boolean authenticated;

    private EditText registration_input, password_input;
    private BottomNavigationView navigation;
    private BottomNavigationView inner_navigation;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int menuItemId = item.getItemId();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (menuItemId == R.id.navigation_home) {
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fragment_layout, homeFragment, "home");
            } else if (menuItemId == R.id.navigation_about) {
                AboutFragment aboutFragment = new AboutFragment();
                fragmentTransaction.replace(R.id.fragment_layout, aboutFragment, "about");
            }

            fragmentTransaction.commit();
            return true;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener innerMOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int menuItemId = item.getItemId();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (menuItemId == R.id.navigation_profile) {
                ProfileFragment profileFragment = new ProfileFragment();

                Bundle outState = new Bundle();
                outState.putString("registration", student.getMatricula());
                outState.putString("name", student.getNome());
                outState.putString("course", student.getCurso());
                outState.putString("campus", student.getCampus());
                outState.putString("status", student.getSituacao());
                profileFragment.setArguments(outState);

                fragmentTransaction.replace(R.id.fragment_layout, profileFragment, "profile");
            } else if (menuItemId == R.id.navigation_classes) {
                ClassesFragment classesFragment = new ClassesFragment();

                Bundle outState = new Bundle();
                outState.putString("registration", registration);
                outState.putString("password", password);
                classesFragment.setArguments(outState);

                fragmentTransaction.replace(R.id.fragment_layout, classesFragment, "classes");
            } else if (menuItemId == R.id.navigation_contacts) {
                ContactsFragment contactsFragment = new ContactsFragment();
                fragmentTransaction.replace(R.id.fragment_layout, contactsFragment, "contacts");
            }

            fragmentTransaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_layout, homeFragment, "home");
            fragmentTransaction.commit();
        }

        navigation = findViewById(R.id.navigation);
        inner_navigation = findViewById(R.id.inner_navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        inner_navigation.setOnNavigationItemSelectedListener(innerMOnNavigationItemSelectedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registration_input = findViewById(R.id.registration_input);
        password_input = findViewById(R.id.password_input);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("authenticated", authenticated);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getBoolean("authenticated")) {
            navigation.setVisibility(View.GONE);
            inner_navigation.setVisibility(View.VISIBLE);
        }
    }

    public void enter(View view) {
        registration = registration_input.getText().toString();
        password = password_input.getText().toString();

        if (registration.isEmpty()) {
            registration_input.setError("Campo matrícula obrigatório");
            return;
        } else if (password.isEmpty()) {
            password_input.setError("Campo senha obrigatório");
            return;
        }

        String url = "https://suap.ifrn.edu.br/api/v2/edu/alunos/" + registration + "/";
        new AuthenticateSUAP().execute("GET", url, registration, password);
    }

    public void leave(View view) {
        recreate();
        student = null;
        inner_navigation.setVisibility(View.GONE);
        navigation.setVisibility(View.VISIBLE);
        navigation.setSelectedItemId(R.id.navigation_home);
        authenticated = false;
    }

    private class AuthenticateSUAP extends AsyncTask<String, Void, Student> {

        @Override
        protected Student doInBackground(String... params) {

            String credentials, encodedCredentials, basicAuth;
            String registration, password;
            String method;
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                method = params[0];
                url = new URL(params[1]);
                registration = params[2];
                password = params[3];

                credentials = registration + ":" + password;
                encodedCredentials = Base64.encodeToString(credentials.getBytes(), 0);
                basicAuth = "Basic " + encodedCredentials;

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Authorization", basicAuth);
                httpURLConnection.setRequestMethod(method);
                httpURLConnection.connect();

                int response = httpURLConnection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    Reader reader = new InputStreamReader(httpURLConnection.getInputStream());
                    student = new Gson().fromJson(reader, Student.class);
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return student;
        }

        @Override
        protected void onPostExecute(Student student) {
            if (student != null) {
                navigation.setVisibility(View.GONE);
                inner_navigation.setVisibility(View.VISIBLE);
                inner_navigation.setSelectedItemId(R.id.navigation_profile);
                authenticated = true;
            } else {
                Toast.makeText(MainActivity.this, "Matrícula ou senha inválida.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
