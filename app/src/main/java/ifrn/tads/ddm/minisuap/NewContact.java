package ifrn.tads.ddm.minisuap;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

import ifrn.tads.ddm.minisuap.models.Contact;
import ifrn.tads.ddm.minisuap.models.Student;
import ifrn.tads.ddm.minisuap.models.StudentContact;

public class NewContact extends FragmentActivity {

    private Contact contact;
    private String registration, password;

    private EditText registration_input;
    private Button btn_add_contact;
    private TextView tv_title_result, tv_registration, tv_name, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        registration_input = findViewById(R.id.registration_input);

        btn_add_contact = findViewById(R.id.btn_add_contact);

        tv_title_result = findViewById(R.id.title_result);
        tv_registration = findViewById(R.id.tv_registration);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);

        registration = getIntent().getStringExtra("registration");
        password = getIntent().getStringExtra("password");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (contact != null) {
            outState.putString("registration", contact.getMatricula());
            outState.putString("name", contact.getNome());
            outState.putString("email", tv_email.getText().toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            contact = new Contact(
                savedInstanceState.getString("registration"),
                savedInstanceState.getString("name"),
                savedInstanceState.getString("email")
            );

            tv_registration.setText(contact.getMatricula());
            tv_name.setText(contact.getNome());
            tv_email.setText(contact.getEmail());

            tv_title_result.setVisibility(View.VISIBLE);
            btn_add_contact.setVisibility(View.VISIBLE);
        }
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
        Student student = Student.find(Student.class, "matricula = ?", registration).get(0);
        List<Contact> contacts = Contact.find(Contact.class, "matricula = ?", contact.getMatricula());

        if (contacts.size() > 0) { // este contato está relacionado a um outro estudante
            List<StudentContact> studentContacts = StudentContact.find(
                StudentContact.class, "student = ? AND contact = ?",
                student.getId().toString(), contacts.get(0).getId().toString()
            );

            if (studentContacts.size() > 0 && student.getContacts().indexOf(studentContacts.get(0)) != 0) { // o estudante autenticado já tem este contato
                Toast.makeText(NewContact.this, "Erro! Contato já existente", Toast.LENGTH_LONG).show();
                return;
            }
        }
        // este contato não está relacionado a um outro estudante
        contact.setEmail("suapnaofornece@suap.livre");
        contact.save();

        StudentContact studentContact = new StudentContact(student, contact);
        studentContact.save();

        getIntent().putExtra("contact_id", contact.getId());
        setResult(RESULT_OK);
        finish();
    }

    public void back(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private class FindStudentSUAP extends AsyncTask<String, Void, Contact> {

        @Override
        protected Contact doInBackground(String... params) {

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
                    contact = new Gson().fromJson(reader, Contact.class);
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return contact;
        }

        @Override
        protected void onPostExecute(Contact contact) {
            if (contact != null) {
                tv_title_result.setVisibility(View.VISIBLE);
                btn_add_contact.setVisibility(View.VISIBLE);

                tv_registration.setText(contact.getMatricula());
                tv_name.setText(contact.getNome());
                tv_email.setText(R.string.title_suaplivre);
            } else {
                Toast.makeText(NewContact.this, "Matrícula inválida", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
