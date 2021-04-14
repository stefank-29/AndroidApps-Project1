package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;

public class DisplayFinanceActivity extends AppCompatActivity {

    // Codes
    public static final String FINANCE = "finance_display";

    // View comps
    private TextView titleTv;
    private TextView amountTv;
    private TextView descTv;
    private ImageView btnPlay;
    private ImageView btnPause;

    private Finance finance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_finance);
        init();
    }

    private void init() {
        parseIntent();
        initView();
//        initListeners();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        this.finance = (Finance) intent.getExtras().getSerializable(FINANCE);
    }

    private void initView() {
        titleTv = findViewById(R.id.titleTv);
        titleTv.setText(finance.getTitle());
        amountTv = findViewById(R.id.amountTv);
        amountTv.setText(String.valueOf(finance.getAmount()));
        descTv = findViewById(R.id.descTv);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);

        if (finance.getDescription() instanceof String) {
            descTv.setText(finance.getDescription().toString());
        } else {
            descTv.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        }
    }
}