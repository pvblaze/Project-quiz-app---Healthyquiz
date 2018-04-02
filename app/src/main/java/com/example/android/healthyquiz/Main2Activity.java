package com.example.android.healthyquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    // Declaring constants to be used for saving instance states.
    static final String STATE_Q1 = "question1";
    static final String STATE_Q2 = "question2";
    static final String STATE_Q3 = "question3";
    static final String STATE_Q5 = "question5";
    static final String STATE_Q7_1 = "question7-1";
    static final String STATE_Q7_2 = "question7-2";
    static final String STATE_Q8 = "question8";
    static final String STATE_CHECKBOX = "numberOfCheckedCheckboxes";

    // Method that saves the data inside constants.
    @Override
    public void onSaveInstanceState(Bundle outState) {

        // The right and false answers in the checkboxes (if: right / else: false)
        if (checkSunlight.isChecked()) outState.putInt(STATE_Q7_1, checkSunlight.getId());
        else if (checkCarrot.isChecked()) outState.putInt(STATE_Q7_1, checkCarrot.getId());

        if (checkMushrooms.isChecked()) outState.putInt(STATE_Q7_2, checkMushrooms.getId());
        else if (checkOranges.isChecked())
            outState.putInt(STATE_Q7_2, checkOranges.getId());

        // Save the UI's current state.
        outState.putInt(STATE_Q1, q1Group.getCheckedRadioButtonId());
        outState.putInt(STATE_Q2, q2Group.getCheckedRadioButtonId());
        outState.putInt(STATE_Q3, q3Group.getCheckedRadioButtonId());
        outState.putInt(STATE_Q5, q5Group.getCheckedRadioButtonId());
        outState.putString(STATE_Q8, String.valueOf(userTextInput.getText().toString()));
        outState.putInt(STATE_CHECKBOX, numberCheckedCheckbox);
        super.onSaveInstanceState(outState);
    }

    // Keeps track of the score.
    int totalPoints = 0;

    // Keeps the number of checkboxes that are checked.
    int numberCheckedCheckbox = 0;

    // Text input
    EditText userTextInput;

    // Declaring the RadioGroups.
    private RadioGroup q1Group;
    private RadioGroup q2Group;
    private RadioGroup q3Group;
    private RadioGroup q5Group;

    // Declaring Checkboxes
    CheckBox checkSunlight;
    CheckBox checkCarrot;
    CheckBox checkMushrooms;
    CheckBox checkOranges;

    // This variable keeping track of the unanswered questions.
    int answerMissing = 0;

    // Method that restores the data from the constants.
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getInt(STATE_Q7_1) == checkSunlight.getId())
            checkSunlight.setChecked(true);
        else if (savedInstanceState.getInt(STATE_Q7_1) == checkCarrot.getId())
            checkCarrot.setChecked(true);

        if (savedInstanceState.getInt(STATE_Q7_2) == checkMushrooms.getId())
            checkMushrooms.setChecked(true);
        else if (savedInstanceState.getInt(STATE_Q7_2) == checkOranges.getId())
            checkOranges.setChecked(true);

        q1Group.check(savedInstanceState.getInt(STATE_Q1));
        q2Group.check(savedInstanceState.getInt(STATE_Q2));
        q3Group.check(savedInstanceState.getInt(STATE_Q3));
        q5Group.check(savedInstanceState.getInt(STATE_Q5));
        userTextInput.setText(savedInstanceState.getString(STATE_Q8));
        numberCheckedCheckbox = savedInstanceState.getInt(STATE_CHECKBOX);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Matching the RadioGroups with XML counterparts.
        q1Group = findViewById(R.id.question_group1);
        q2Group = findViewById(R.id.question_group2);
        q3Group = findViewById(R.id.question_group3);
        q5Group = findViewById(R.id.question_group5);

        // Matching the Checkboxes with XML counterparts.
        checkSunlight = findViewById(R.id.sunlight);
        checkCarrot = findViewById(R.id.carrot);
        checkMushrooms = findViewById(R.id.mushrooms);
        checkOranges = findViewById(R.id.oranges);
        userTextInput = findViewById(R.id.text_input);
    }

    // When checkboxes are clicked this method runs.
    public void onCbClick(View view) {

        // Check if max number of checkboxes are checked
        Boolean maxChecked = false;

        // Establishing "checked" status.
        Boolean checked = ((CheckBox) view).isChecked();

        // To be able to use Checkbox methods
        CheckBox theCheckbox = whichCheckbox(view.getId());

        // This method makes sure that only 2 checkboxes can be checked.
        if ((numberCheckedCheckbox >= 2) && checked) {
            theCheckbox.setChecked(false);
            maxChecked = true;

            Toast.makeText(this, "You can only choose 2 checkboxes", Toast.LENGTH_SHORT).show();
        } else if (!checked) {
            theCheckbox.setChecked(true);
            theCheckbox.toggle();
        }

        checked = theCheckbox.isChecked();

        if (checked) {
            numberCheckedCheckbox++;
        } else if (!checked && maxChecked) {

        } else {
            numberCheckedCheckbox--;
        }
    }

    /**
     * @param checkedId is the ID of the Checkbox that user selected.
     * @return This method returns Checkbox object.
     */


    private CheckBox whichCheckbox(int checkedId) {

        if (checkedId == R.id.sunlight) {
            return checkSunlight;

        } else if (checkedId == R.id.carrot) {
            return checkCarrot;

        } else if (checkedId == R.id.mushrooms) {
            return checkMushrooms;

        } else if (checkedId == R.id.oranges) {
            return checkOranges;
        } else return null;

    }
    // Checking for correct answers and declaring points for correct answers.
    public void onScoreClick(View view) {

        // Checking for correct radiobutton answers
        int q1Id = q1Group.getCheckedRadioButtonId();
        totalPoints += calculateScore(q1Id, R.id.answer_30, true);

        int q2Id = q2Group.getCheckedRadioButtonId();
        totalPoints += calculateScore(q2Id, R.id.answer_igf, true);

        int q3Id = q3Group.getCheckedRadioButtonId();
        totalPoints += calculateScore(q3Id, R.id.answer_beetjuice, true);

        int q4Id = q5Group.getCheckedRadioButtonId();
        totalPoints += calculateScore(q4Id, R.id.answer_dso, true);

        // Checkbox question points checked. 25 Points for each correct checkbox.
        if (checkMushrooms.isChecked()) totalPoints += 25;

        if (checkSunlight.isChecked()) totalPoints += 25;

        // Name the berry question (10 points)
        if (userTextInput.getText().toString().toLowerCase().equals("blueberry")) totalPoints += 10;

        // This is to check if there are unanswered questions. And if yes, then show a message warning.
        // If text input is left unanswered:
        if (userTextInput.getText().toString().trim().length() > 1) answerMissing += 1;

        // If checkboxes left unanswered:
        if (numberCheckedCheckbox > 0) answerMissing += 1;

        if (answerMissing < 6 && numberCheckedCheckbox == 0) {
            Toast.makeText(this, "Please answer all the questions\n\nYou have " + (6 - answerMissing) + " more questions left!\n\nTwo checkboxes are missing in Question 4", Toast.LENGTH_SHORT).show();
            totalPoints = 0;
            answerMissing = 0;
            return;
        } else if (answerMissing < 6) {
            Toast.makeText(this, "Please answer all the questions\n\n" + (6 - answerMissing) + " more to go!", Toast.LENGTH_SHORT).show();
            totalPoints = 0;
            answerMissing = 0;
            return;
        } else if (answerMissing == 6 && numberCheckedCheckbox == 1) {
            Toast.makeText(this, "Allmost done!\n\nA Checkbox missing in Question 4", Toast.LENGTH_SHORT).show();
            totalPoints = 0;
            answerMissing = 0;
            return;
        }

        // Sending the score to the Main3Acitivty page for results
        Intent Main3Acitivty = new Intent(this, Main3Activity.class);
        String message = Integer.toString(totalPoints);
        Main3Acitivty.putExtra("theScore", message);
        startActivity(Main3Acitivty);
    }

    /**
     * @param idChecked   is the RadioButton ID that user selected.
     * @param idCorrect   is the RadioButton ID of the correct answer.
     * @param isTrueFalse is a flag for grading purposes. Since True / False questions are rather easy, they provide less points.
     * @return This method returns points for the totalPoints variable.
     */
    private int calculateScore(int idChecked, int idCorrect, boolean isTrueFalse) {

        // Calculating score for each question with 3 arguments. While keeping track of unanswered questions.
        if (idChecked == idCorrect) {
            answerMissing += 1;
            if (isTrueFalse) {
                // Correct answer points
                return 10;
            } else {
                // Incorrect answer points
                return 2;
            }
            // Checking for unanswered questions
        } else if (idChecked == -1) {
            return 0;
        } else {
            answerMissing += 1;
            return 0;
        }
    }
}