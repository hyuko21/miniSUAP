package ifrn.tads.ddm.minisuap.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ifrn.tads.ddm.minisuap.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        TextView tv_registration = view.findViewById(R.id.tv_registration);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_course = view.findViewById(R.id.tv_course);
        TextView tv_campus = view.findViewById(R.id.tv_campus);
        TextView tv_status = view.findViewById(R.id.tv_status);

        Bundle args = getArguments();

        tv_registration.setText(args.getString("registration"));
        tv_name.setText(args.getString("name"));
        tv_course.setText(args.getString("course"));
        tv_campus.setText(args.getString("campus"));
        tv_status.setText(args.getString("status"));

        return view;
    }
}
