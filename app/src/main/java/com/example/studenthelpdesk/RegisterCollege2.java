package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
//In this activity we ask all the department names of the college
public class RegisterCollege2 extends AppCompatActivity {
    final String deptQ="Enter Department Name",ansHint="Department name here";
    LinearLayout ll;
    int numberOfDept;
    ArrayList<String> departmentName;
    CollegeRegistrationData allData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_college2);
        ll=findViewById(R.id.linearL);
        allData=RegisterCollege.allData;
        View questionRepeatable = getLayoutInflater().inflate(R.layout.edit_text_layout, null);
        TextView question=questionRepeatable.findViewById(R.id.Heading2);
        EditText answer=questionRepeatable.findViewById(R.id.EditText);
        answer.setHint(ansHint);
        question.setText(deptQ);
        departmentName=new ArrayList<>();
        ll.addView(questionRepeatable);
        numberOfDept=1;

    }
    //when click on floating plus button... this adds a question
    public void addQuestion(View v)
    {

        Log.d("hi","hi");
        if(lastQuestionFilled()) {
            View questionRepeatable = getLayoutInflater().inflate(R.layout.edit_text_layout, null);
            TextView question = questionRepeatable.findViewById(R.id.Heading2);
            EditText answer = questionRepeatable.findViewById(R.id.EditText);
            answer.setHint(ansHint);
            question.setText(deptQ);
            ll.addView(questionRepeatable);
            numberOfDept++;
        }
    }
    public void saveAndNext(View v)
    {
        for (int i = 0; i < numberOfDept; i++) {
                View question1 = ll.getChildAt(i);
                EditText ans = question1.findViewById(R.id.EditText);
                String dept = ans.getText().toString();
                if (i < departmentName.size()) {
                    departmentName.set(i, dept);
                } else {
                    departmentName.add(dept);
                }
        }
        allData.setDeptName(departmentName);
        //intent to registration step 3
        startActivity(new Intent(RegisterCollege2.this,RegisterCollege3.class));
    }
    public boolean lastQuestionFilled()
    {
        View question1 = ll.getChildAt(numberOfDept-1);
        EditText ans=question1.findViewById(R.id.EditText);
        String dept=ans.getText().toString();
        if(dept.length()==0) {
            ans.setError("Enter data here");
            return false;
        }
        else
            return true;

    }
}