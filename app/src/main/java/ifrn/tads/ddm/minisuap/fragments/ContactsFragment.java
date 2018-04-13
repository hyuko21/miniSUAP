package ifrn.tads.ddm.minisuap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ifrn.tads.ddm.minisuap.MainActivity;
import ifrn.tads.ddm.minisuap.NewContact;
import ifrn.tads.ddm.minisuap.R;

public class ContactsFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_contacts, container, false);

        args = getArguments();

        Button btn_add_contact = view.findViewById(R.id.btn_add_contact);
        btn_add_contact.setOnClickListener(mOnClickListener);

        return view;
    }
}
