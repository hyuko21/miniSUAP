package ifrn.tads.ddm.minisuap.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ifrn.tads.ddm.minisuap.MainActivity;
import ifrn.tads.ddm.minisuap.R;
import ifrn.tads.ddm.minisuap.models.Student;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Student student = mainActivity.getStudent();

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        TextView tv_registration = view.findViewById(R.id.tv_registration);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_course = view.findViewById(R.id.tv_course);
        TextView tv_campus = view.findViewById(R.id.tv_campus);
        TextView tv_status = view.findViewById(R.id.tv_status);

        tv_registration.setText(student.getMatricula());
        tv_name.setText(student.getNome());
        tv_course.setText(student.getCurso());
        tv_campus.setText(student.getCampus());
        tv_status.setText(student.getSituacao());

        return view;
    }
}
