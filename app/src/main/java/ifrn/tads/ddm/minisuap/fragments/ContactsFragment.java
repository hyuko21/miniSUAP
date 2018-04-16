package ifrn.tads.ddm.minisuap.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

import ifrn.tads.ddm.minisuap.NewContact;
import ifrn.tads.ddm.minisuap.R;
import ifrn.tads.ddm.minisuap.models.Contact;
import ifrn.tads.ddm.minisuap.models.Student;
import ifrn.tads.ddm.minisuap.models.StudentContact;

import static android.app.Activity.RESULT_OK;

public class ContactsFragment extends Fragment {

    private LayoutInflater inflater;
    private TableLayout contactsTable;

    private Student student;

    private Bundle args;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), NewContact.class);

            intent.putExtra("registration", args.getString("registration"));
            intent.putExtra("password", args.getString("password"));

            startActivityForResult(intent, 0);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inflater = getLayoutInflater();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_contacts, container, false);

        contactsTable = view.findViewById(R.id.contacts_table);

        args = getArguments();

        student = Student.find(Student.class, "matricula = ?", args.getString("registration")).get(0);

        showContacts();

        Button btn_add_contact = view.findViewById(R.id.btn_add_contact);
        btn_add_contact.setOnClickListener(mOnClickListener);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) showContacts();
    }

    public void showContacts() {
        List<StudentContact> contacts = student.getContacts();

        if (contacts != null) {
            contactsTable.removeAllViews();

            Contact contact;
            View row;
            TextView tv_registration;
            TextView tv_name;
            TextView tv_email;

            for (StudentContact sc: contacts) {
                contact = sc.getContact();
                row = inflater.inflate(R.layout.table_row_contact, null);

                tv_registration = row.findViewById(R.id.tv_registration);
                tv_name = row.findViewById(R.id.tv_name);
                tv_email = row.findViewById(R.id.tv_email);

                tv_registration.setText(contact.getMatricula());
                tv_name.setText(contact.getNome());
                tv_email.setText(R.string.title_suaplivre);

                contactsTable.addView(row);
            }
        }
    }
}
