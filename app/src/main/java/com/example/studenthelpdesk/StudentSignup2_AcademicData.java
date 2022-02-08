package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class StudentSignup2_AcademicData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    View branchView;
    StudentData studentData;
    LinearLayout ll;
    String allq[],allans[];
    String allqid[];
    String gender="Male";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsignup2_academic_data);
        studentData=Signup.studentData;
        ll=findViewById(R.id.ll);
        FirebaseFirestore f=FirebaseFirestore.getInstance();
        DocumentReference acadQuestions = f.collection("All Colleges").document(studentData.getCollegeid()).collection("Questions").document("Academic Question");
        acadQuestions.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()==false) {
                    return;
                }
                long noOfQuestions= (long) documentSnapshot.get("Total");
                for(int i=0;i<noOfQuestions;i++)
                {
                    allq=new String[(int) noOfQuestions];
                    allans=new String[(int) noOfQuestions];
                    allqid=new String[(int) noOfQuestions];
                    DocumentReference quesDetails = acadQuestions.collection(i + "").document(i + "");
                    int finalI = i;
                    int finalI1 = i;
                    int finalI2 = i;
                    quesDetails.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            boolean compulsory= (boolean) documentSnapshot.get("Compulsory");
                            String question= (String) documentSnapshot.get("Question");
                            long type=(long)documentSnapshot.get("Type");
                            View addQues=getType(type,question, finalI1);


                            if(compulsory==true)
                            {
                                if(type==0)
                                    addQues.setId((int)8);
                                else
                                    addQues.setId((int) type);
                            }
                            else
                            {
                                if(type==0)
                                    addQues.setId((int)-8);
                                else
                                    addQues.setId((int) -type);
                            }
                            ll.addView(addQues);
                        }
                    });
                }
            }
        });
    }
    public void saveAndNext(View view)
    {
        int quesNumber=ll.getChildCount();
        int i;
        for(i=0;i<quesNumber;i++)
        {
            View v=ll.getChildAt(i);
            int id=v.getId();
            if(id>0)
            {
                //
                if(empty(v,id,allq[i],i))
                {
                    //if a compulsory field is empty cant move forward
                    return;
                }

            }
            else
            {
                fill(v,-id,allq[i],i);
            }
            //all questions done and filled then move to step 2
            if(i==quesNumber-1) {
                studentData.setA_ans(allans);
                studentData.setA_ques(allq);
                studentData.setA_id(allqid);
                startActivity(new Intent(StudentSignup2_AcademicData.this, StudentUploadDocuments.class));
            }
        }
    }
    void fill(View v,int i,String ques,int qId)
    {
        if(i==2)
        {
            //numeric
            View nView=v;

            TextView ans=nView.findViewById(R.id.editvalnumeric);

            allans[qId]=ans.getText().toString();


        }
        if(i==3)
        {
            //numeric decimal
            View nView=v;
            TextView ans=nView.findViewById(R.id.editvalmulti);

            allans[qId]=ans.getText().toString();

        }
        if(i==8)
        {
            //single line string
            View nView=v;
            TextView ans=nView.findViewById(R.id.editTextTextMultiLine);
            Log.e("b",ans.getText().toString());
            allans[qId]=ans.getText().toString();

        }
        if(i==1)
        {
            //multiline string
            View nView=v;
            TextView ans=nView.findViewById(R.id.editTextMultiLine);

            allans[qId]=ans.getText().toString();

        }
        if(i==4)
        {
            //gender

            allans[qId]=gender;


        }
        if(i==5)
        {
            //date
            View nView=v;
            TextView ans=nView.findViewById(R.id.tvDate);

            {
                allans[qId]=ans.getText().toString();
            }

        }
        if(i==6)
        {
            //upload

        }

    }
    boolean empty(View v,int i,String ques,int qID)
    {
        if(i==2)
        {
            //numeric
            View nView=v;

            TextView ans=nView.findViewById(R.id.editvalnumeric);
            if(ans.length()==0)
            {
                ans.setError("This is compulsory");
                return true;
            }
            else
            {
                allans[qID]=ans.getText().toString();
            }
            return  false;

        }
        if(i==3)
        {
            //numeric decimal
            View nView=v;
            TextView ans=nView.findViewById(R.id.editvalmulti);
            if(ans.length()==0)
            {
                ans.setError("This is compulsory");
                return true;
            }
            else
            {
                allans[qID]=ans.getText().toString();
            }
        }
        if(i==8)
        {
            //single line string
            View nView=v;
            TextView ans=nView.findViewById(R.id.editTextTextMultiLine);
            if(ans.length()==0)
            {
                ans.setError("This is compulsory");
                return true;
            }
            else
            {
                allans[qID]=ans.getText().toString();
            }
        }
        if(i==1)
        {
            //multiline string
            View nView=v;
            TextView ans=nView.findViewById(R.id.editTextMultiLine);
            if(ans.length()==0)
            {
                ans.setError("This is compulsory");
                return true;
            }
            else
            {
                allans[qID]=ans.getText().toString();
            }
        }
        if(i==4)
        {
            //gender
            if(gender==null) {
                Toast.makeText(this, "Select Gender", Toast.LENGTH_LONG).show();
                return true;
            }
            Log.e("b gender",gender);
            allans[qID]=gender;
            return false;

        }
        if(i==5)
        {
            //date
            View nView=v;
            TextView ans=nView.findViewById(R.id.tvDate);
            if(ans.length()==0)
            {
                ans.setError("This is compulsory");
                return true;
            }
            else
            {
                allans[qID]=ans.getText().toString();
            }

        }
        if(i==6)
        {
            //upload
            View nView=v;
            TextView ans=nView.findViewById(R.id.document_name);
            if(ans.length()==0)
            {
                ans.setError("This is compulsory");
                return true;
            }
            else
            {
                allans[qID]=ans.getText().toString();
            }

        }
        if(i==7)
        {
            //dropdown
            View nView=v;
            Log.e("b",studentData.getCourse()+" "+studentData.getBranch()+" "+studentData.getYr());
            if(ques.equalsIgnoreCase("Course")&&studentData.getCourse().equalsIgnoreCase(" ")) {
                Toast.makeText(this,"Select Course",Toast.LENGTH_LONG).show();
                return true;
            }
            else if(ques.equalsIgnoreCase("Course"))
            {
                allans[qID]=studentData.getCourse();
            }
            if(ques.equalsIgnoreCase("Branch")&&studentData.getBranch().equalsIgnoreCase(" ")) {
                Toast.makeText(this,"Select Branch",Toast.LENGTH_LONG).show();
                return true;
            }
            else if(ques.equalsIgnoreCase("Branch"))
            {
                allans[qID]=studentData.getBranch();
            }
            if(ques.equalsIgnoreCase("Year")&&studentData.getYr().equalsIgnoreCase(" ")) {
                Toast.makeText(this,"Select Year",Toast.LENGTH_LONG).show();
                return true;
            }
            else if(ques.equalsIgnoreCase("Year"))
            {
                allans[qID]=studentData.getYr();
            }

        }
        return false;
    }
    int noOfq=0;

    View getType(long i, String q,int qNumber)
    {

        allq[noOfq]=q;
        allqid[noOfq++]=qNumber+"";
        if(i==2)
        {
            //numeric
            //View nView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.repeatable_numeric_text_layout, null, false);
            View nView=getLayoutInflater().inflate(R.layout.repeatable_numeric_text_layout,null);
            TextView ques=nView.findViewById(R.id.Ques);
            ques.setText(q);
            ques.setId(qNumber);
            return nView;
        }
        if(i==3)
        {
            //numeric decimal
            View nView=getLayoutInflater().inflate(R.layout.repeatable_number_decimal_layout,null);
            TextView ques=nView.findViewById(R.id.Ques);
            ques.setText(q);
            ques.setId(qNumber);

            return nView;
        }
        if(i==0)
        {
            //single line string
            View nView=getLayoutInflater().inflate(R.layout.repeatable_edit_text_white_layout,null);
            TextView ques=nView.findViewById(R.id.Ques);
            ques.setText(q);
            ques.setId(qNumber);
            return nView;
        }
        if(i==1)
        {
            //multiline string
            View nView=getLayoutInflater().inflate(R.layout.repeatable_multiline_text_layout,null);
            TextView ques=nView.findViewById(R.id.Ques);
            ques.setText(q);
            ques.setId(qNumber);
            return nView;
        }
        if(i==4)
        {
            //gender
            View nView=getLayoutInflater().inflate(R.layout.repeatable_radio_button_layout,null);
            TextView ques=nView.findViewById(R.id.Ques);
            ques.setId(qNumber);
            RadioGroup ans=nView.findViewById(R.id.rg);
            ans.setClickable(true);
            RadioButton m=ans.findViewById(R.id.male);

            RadioButton f=ans.findViewById(R.id.female);

            RadioButton o=ans.findViewById(R.id.not);
            ans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(i==m.getId())
                        gender="Male";
                    else if(i==f.getId())
                        gender="Female";
                    else
                        gender="Other";
                    // Log.e("b gender",gender+i);
                }
            });
            f.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            o.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            ques.setText(q);
            return nView;
        }
        if(i==5)
        {
            //date
            View nView=getLayoutInflater().inflate(R.layout.repeatable_date_input_layout,null);
            TextView ques=nView.findViewById(R.id.dobtext);
            ques.setText(q);
            ques.setId(qNumber);
            Button datePicker =nView.findViewById(R.id.btPickDate);
            TextView datePicked=nView.findViewById(R.id.tvDate);
            datePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePicker mDatePickerDialogFragment;
                    mDatePickerDialogFragment = new DatePicker();
                    mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
                    datePicked.setText(selectedDate);
                }
            });
            return nView;
        }
        if(i==6)
        {
            //upload
            View nView=getLayoutInflater().inflate(R.layout.repeatable_upload_document,null);
            TextView ques=nView.findViewById(R.id.Ques);
            ques.setText(q);
            ques.setId(qNumber);
            return nView;
        }
        if(i==7)
        {

            //dropdown
            View nView=getLayoutInflater().inflate(R.layout.repeatable_dropdown,null);
            TextView ques=nView.findViewById(R.id.Ques);
            ques.setText(q);
            ques.setId(qNumber);
            AutoCompleteTextView drop=nView.findViewById(R.id.dropdown);
            if(q.trim().equalsIgnoreCase("Year"))
            {
                String yr[]={"1","2","3","4","5","5+"};
                ArrayAdapter<String> spinnerList=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,yr);
                drop.setText(yr[0]);
                drop.setAdapter(spinnerList);
                studentData.setYr(yr[0]);
                drop.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                        studentData.setYr(yr[pos]);
                    }
                });

            }
            if(q.equalsIgnoreCase("Course"))
            {
                FirebaseFirestore f=FirebaseFirestore.getInstance();
                DocumentReference allCourse = f.collection("All Colleges").document(studentData.getCollegeid());
                allCourse.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<String> coursesList= (ArrayList<String>) documentSnapshot.get("Courses");
                        ArrayAdapter spinnerList=new ArrayAdapter(StudentSignup2_AcademicData.this,android.R.layout.simple_spinner_item,coursesList);
                        drop.setText(coursesList.get(0));
                        drop.setAdapter(spinnerList);
                        studentData.setCourse(coursesList.get(0));
                        drop.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                                if(coursesList.get(pos).equalsIgnoreCase(studentData.getCourse())&&studentData.getBranch().equalsIgnoreCase(" ")==false)
                                    return;
                                studentData.setCourse(coursesList.get(pos));
                                FirebaseFirestore f = FirebaseFirestore.getInstance();
                                AutoCompleteTextView drop=branchView.findViewById(R.id.dropdown);
                                DocumentReference allBranch = f.collection("All Colleges").document(studentData.getCollegeid()).collection("Branches").document(studentData.getCourse());

                                //Log.e("Value in student",studentData.getCourse());
                                allBranch.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                        if(documentSnapshot.exists()==false) {
                                            drop.setText("Select Course First");
                                            return;
                                        }

                                        ArrayList<String> branchList = (ArrayList<String>) documentSnapshot.get("Branches");

                                        ArrayAdapter spinnerList = new ArrayAdapter(StudentSignup2_AcademicData.this, android.R.layout.simple_spinner_item, branchList);
                                        drop.setText(branchList.get(0));
                                        drop.setAdapter(spinnerList);
                                        studentData.setBranch(branchList.get(0));
                                        drop.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                                                studentData.setBranch(branchList.get(pos));
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
            if(q.equalsIgnoreCase("Branch")) {
                FirebaseFirestore f = FirebaseFirestore.getInstance();
                DocumentReference allCourse = f.collection("All Colleges").document(studentData.getCollegeid()).collection("Branches").document(studentData.getCourse());
                allCourse.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()==false) {
                            drop.setText("Select Course First");
                            return;
                        }
                        ArrayList<String> branchList = (ArrayList<String>) documentSnapshot.get("Branches");
                        ArrayAdapter spinnerList = new ArrayAdapter(StudentSignup2_AcademicData.this, android.R.layout.simple_spinner_item, branchList);
                        drop.setText(branchList.get(0));
                        drop.setAdapter(spinnerList);
                        studentData.setBranch(branchList.get(0));
                        drop.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                                studentData.setBranch(branchList.get(pos));
                            }
                        });

                    }
                });
                branchView=nView;
            }
            return nView;
        }
        return null;
    }
    String selectedDate="";
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());

    }}