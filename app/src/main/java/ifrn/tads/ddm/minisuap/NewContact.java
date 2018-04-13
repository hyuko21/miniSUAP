package ifrn.tads.ddm.minisuap;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ifrn.tads.ddm.minisuap.models.Student;

public class NewContact extends FragmentActivity {

    private Student contact;
    private String registration, password;

    private EditText registration_input;
    private TextView tv_registration, tv_name, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        registration_input = findViewById(R.id.registration_input);

        tv_registration = findViewById(R.id.tv_registration);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);

        registration = getIntent().getStringExtra("registration");
        password = getIntent().getStringExtra("password");
    }

    public void findContact(View view) {
        String registration_contact = registration_input.getText().toString();

        if (registration_contact.isEmpty()) {
            registration_input.setError("Campo matrícula obrigatório");
            return;
        }

        String url = "https://suap.ifrn.edu.br/api/v2/edu/alunos/" + registration_contact + "/";

        new FindStudentSUAP().execute("GET", url, registration, password);
    }

    public void addContact(View view) {
        if (registration_input.getText().toString().isEmpty()) {
            registration_input.setError("Campo matrícula obrigatório");
            return;
        } else if (contact == null) {
            Toast.makeText(this, "Clique em 'Procurar'", Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println(contact.getId());
        getIntent().putExtra("contact_id", contact.getId());
//        finish();
    }

    private class FindStudentSUAP extends AsyncTask<String, Void, Student> {

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
                    contact = new Gson().fromJson(reader, Student.class);
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return contact;
        }

        @Override
        protected void onPostExecute(Student contact) {
            if (contact != null) {
                tv_registration.setText(contact.getMatricula());
                tv_name.setText(contact.getNome());
                tv_email.setText(R.string.title_suaplivre);
            } else {
                Toast.makeText(NewContact.this, "Matrícula inválida", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
