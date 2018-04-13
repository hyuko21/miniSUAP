package ifrn.tads.ddm.minisuap.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ifrn.tads.ddm.minisuap.R;
import ifrn.tads.ddm.minisuap.models.Class;

public class ClassesFragment extends Fragment {

    TableLayout scrollTable;
    LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_classes, container, false);

        scrollTable = view.findViewById(R.id.classes_table);

        Bundle args = getArguments();

        String registration = args.getString("registration");
        String password = args.getString("password");

        String url = "https://suap.ifrn.edu.br/api/v2/minhas-informacoes/turmas-virtuais/2018/1/";

        new StudentClasses().execute("GET", url, registration, password);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inflater = getLayoutInflater();
    }

    private class StudentClasses extends AsyncTask<String, Void, List<Class>> {

        private List<Class> classes;
        private Type listType = new TypeToken<List<Class>>(){}.getType();

        @Override
        protected List<Class> doInBackground(String... params) {

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
                    classes = new Gson().fromJson(reader, listType);
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return classes;
        }

        @Override
        protected void onPostExecute(List<Class> classes) {

            if (classes != null) {
                View row;
                TextView tv_initials;
                TextView tv_description;
                TextView tv_status;
                TextView tv_class_schedules;

                for (Class c : classes) {
                    row = inflater.inflate(R.layout.table_row_class, null);

                    tv_initials = row.findViewById(R.id.tv_initials);
                    tv_description = row.findViewById(R.id.tv_description);
                    tv_status = row.findViewById(R.id.tv_status);
                    tv_class_schedules = row.findViewById(R.id.tv_class_schedules);

                    tv_initials.setText(c.getSigla());
                    tv_description.setText(c.getDescricao());

                    String status = c.getObservacao();
                    tv_status.setText(status != null ? status : "Cursando");

                    tv_class_schedules.setText(c.getHorarios_de_aula());

                    scrollTable.addView(row);
                }
            }
        }
    }
}
