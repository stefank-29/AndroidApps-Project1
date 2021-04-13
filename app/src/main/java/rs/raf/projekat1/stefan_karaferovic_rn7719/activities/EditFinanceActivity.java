package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;

public class EditFinanceActivity extends AppCompatActivity implements Serializable {

    // Codes
    public static final String FINANCE_ID = "financeId";

    // View comps
    private EditText editTitle;
    private EditText editAmount;
    private EditText editDesc;


    // Finance attributes
    private Finance finance;
//    private String title;
//    private int amount;
//    private String desc;
//    private File audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_finance);
        init();
    }

    private void init() {
        parseIntent();
        initView();

    }

    private void initView() {
        editTitle = findViewById(R.id.editTitle);
        editTitle.setText(finance.getTitle());
        editAmount = findViewById(R.id.editAmount);
        editAmount.setText(String.valueOf(finance.getAmount()));
        editDesc = findViewById(R.id.editDesc);
        if (finance.getDescription() instanceof String) {
            editDesc.setText(finance.getDescription().toString());
        } else {
            
        }
    }

    private void parseIntent() {
        Intent intent = getIntent();
        this.finance = (Finance) intent.getExtras().getSerializable(FINANCE_ID);
    }
}