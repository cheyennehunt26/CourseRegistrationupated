package com.example.courseregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Dialog;

import android.os.Build;



import android.os.Bundle;


import android.view.View;

import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    String[] course = {"Graduate", "4th Year", "3th Year", "2th Year", "1th Year"};
    RecyclerView studentListRV;
    StudentAdapter studentAdapter;
    List<Student> studentList = new ArrayList<>();
    FloatingActionButton add;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentListRV = findViewById(R.id.student_list);
        add = findViewById(R.id.add);
        studentAdapter = new StudentAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        studentListRV.setLayoutManager(mLayoutManager);
        studentListRV.setItemAnimator(new DefaultItemAnimator());
        studentListRV.setAdapter(studentAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showDialog(true, null,0);
            }
        });
    }
    public void deleteCandidate(Student student, int position) {
        studentList.remove(position);
        studentAdapter.setData(studentList);
    }
    public void editCandidate(Student student, int position) {
        showDialog(false,student,position);
    }
    public void showDialog(final boolean isInsert, Student student, final int position) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_edit);
        if (isInsert) dialog.setTitle("Add Candidate");
        else dialog.setTitle("Edit Candidate");
        final TextInputLayout name = dialog.findViewById(R.id.name);
        final TextInputLayout rollNumber = dialog.findViewById(R.id.roll_no);
        final Spinner courseSpinner = dialog.findViewById(R.id.cource);
        Button submit = dialog.findViewById(R.id.submit);
        //Setting spinner value ArrayAdapter 
        ArrayAdapter aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, course);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(aa);
        if (!isInsert) {
            name.getEditText().setText(student.name);
            rollNumber.getEditText().setText(student.rollNumber);
            courseSpinner.setSelection(student.priority);
        }
// if button is clicked, close the custom dialog 
      submit.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
            if (validateUI(name, rollNumber)) {
                Student student = new Student(name.getEditText().getText().toString(), rollNumber.getEditText().getText().toString(), courseSpinner.getSelectedItem().toString(), courseSpinner.getSelectedItemPosition());
                if(isInsert) studentList.add(student);
                else {
                    studentList.get(position).name=student.name;
                    studentList.get(position).rollNumber=student.rollNumber;
                    studentList.get(position).course=student.course;
                    studentList.get(position).priority=student.priority;
                }
                Collections.sort(studentList, new Comparator<Student>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT) public int compare(Student v1, Student v2) {
                        return Long.compare(v1.getPriority(),v2.getPriority());
                    }
                });
                studentAdapter.setData(studentList);
                dialog.dismiss();
            }
        }
    });
 dialog.show();
}
    private boolean validateUI(TextInputLayout name, TextInputLayout rollNumber) {
        if (name.getEditText().getText().toString().isEmpty()) {
            name.setError("Required");
            return false;
        }
        if (rollNumber.getEditText().getText().toString().isEmpty()) {
            rollNumber.setError("Required");
            return false;
        }
        return true;
    }
}

