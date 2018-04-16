package ifrn.tads.ddm.minisuap.models;

import com.orm.SugarRecord;

public class StudentContact extends SugarRecord {
    private Contact contact;
    private Student student;

    public StudentContact() {
        super();
    }

    public StudentContact(Student student, Contact contact) {
        this.student = student;
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
