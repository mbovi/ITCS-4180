package com.example.devansh.homework02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        setTitle("Edit Contact");

        if(getIntent() != null && getIntent().getExtras() != null){


            Contact thisperson = (Contact) getIntent().getExtras().get("CONTACT");
            setTitle(thisperson.getFname() + " " + thisperson.getLname());
            ((ImageView) findViewById(R.id.editImageView)).setImageDrawable(getResources().getDrawable(R.drawable.default_image));
            ((EditText) findViewById(R.id.editFirstName)).setText(thisperson.getFname());
            ((EditText) findViewById(R.id.editLastName)).setText(thisperson.getLname());
            ((EditText) findViewById(R.id.editAddress)).setText(thisperson.getAddress());
            ((EditText) findViewById(R.id.editBirthday)).setText(thisperson.getBirthday());
            ((EditText) findViewById(R.id.editCompany)).setText(thisperson.getCompany());
            ((EditText) findViewById(R.id.editEmail)).setText(thisperson.getEmail());
            ((EditText) findViewById(R.id.editFb)).setText(thisperson.getFbURL());
            ((EditText) findViewById(R.id.editNickname)).setText(thisperson.getNickname());
            ((EditText) findViewById(R.id.editPhone)).setText(thisperson.getPhone());
            ((EditText) findViewById(R.id.editSkype)).setText(thisperson.getSkypeURL());
            ((EditText) findViewById(R.id.editTwitter)).setText(thisperson.getTwitterURL());
            ((EditText) findViewById(R.id.editURL)).setText(thisperson.getUrl());
            ((EditText) findViewById(R.id.editYoutube)).setText(thisperson.getYoutubeChannel());

        }

        findViewById(R.id.editBtnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String fname, lname, company = "", phone, email = "", url = "", address = "", birthday ="", nickname = "", fbURL ="", twitterURL = "", skypeURL ="", youtubeChannel="";
                boolean fnameCheck = true, lnameCheck = true, phoneCheck = true;
                if(((EditText)findViewById(R.id.editFirstName)).getText().toString() == null) {
                    fnameCheck = false;
                    Toast.makeText(EditContact.this, "Please enter your first name", Toast.LENGTH_SHORT).show();
                }

                if(fnameCheck && ((EditText)findViewById(R.id.editLastName)).getText().toString() == null) {
                    lnameCheck = false;
                    Toast.makeText(EditContact.this, "Please enter your last name", Toast.LENGTH_SHORT).show();
                }

                if(fnameCheck && lnameCheck && ((EditText)findViewById(R.id.editPhone)).getText().toString() == null) {
                    phoneCheck = false;
                    Toast.makeText(EditContact.this, "Please enter your Phone", Toast.LENGTH_SHORT).show();
                }

                if(lnameCheck && fnameCheck && phoneCheck) {
                    fname = ((EditText) findViewById(R.id.editFirstName)).getText().toString();
                    lname = ((EditText) findViewById(R.id.editLastName)).getText().toString();
                    phone = ((EditText) findViewById(R.id.editPhone)).getText().toString();

                    if (((EditText) findViewById(R.id.editCompany)).getText().toString() != null)
                        company = ((EditText) findViewById(R.id.editCompany)).getText().toString();
                    if (((EditText) findViewById(R.id.editEmail)).getText().toString() != null)
                        email = ((EditText) findViewById(R.id.editEmail)).getText().toString();

                    if (((EditText) findViewById(R.id.editURL)).getText().toString() != null)
                        url = ((EditText) findViewById(R.id.editURL)).getText().toString();

                    if (((EditText) findViewById(R.id.editAddress)).getText().toString() != null)
                        address = ((EditText) findViewById(R.id.editAddress)).getText().toString();

                    if (((EditText) findViewById(R.id.editBirthday)).getText().toString() != null)
                        birthday = ((EditText) findViewById(R.id.editBirthday)).getText().toString();

                    if (((EditText) findViewById(R.id.editNickname)).getText().toString() != null)
                        nickname = ((EditText) findViewById(R.id.editNickname)).getText().toString();

                    if (((EditText) findViewById(R.id.editFb)).getText().toString() != null)
                        fbURL = ((EditText) findViewById(R.id.editFb)).getText().toString();

                    if (((EditText) findViewById(R.id.editTwitter)).getText().toString() != null)
                        twitterURL = ((EditText) findViewById(R.id.editTwitter)).getText().toString();

                    if (((EditText) findViewById(R.id.editSkype)).getText().toString() != null)
                        skypeURL = ((EditText) findViewById(R.id.editSkype)).getText().toString();

                    if (((EditText) findViewById(R.id.editYoutube)).getText().toString() != null)
                        youtubeChannel = ((EditText) findViewById(R.id.editYoutube)).getText().toString();

                    Contact editContact = new Contact(fname, lname, company, phone, email, url, address, birthday, nickname, fbURL, twitterURL, skypeURL, youtubeChannel);

                    Intent intent = new Intent();
                    intent.putExtra("EDITTED_CONTACT", editContact);
                    intent.putExtra("LOCATION", (int) getIntent().getExtras().get("LOCATION"));
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });


    }
}
