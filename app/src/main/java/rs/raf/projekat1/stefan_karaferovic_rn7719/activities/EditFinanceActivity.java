package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.AudioFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.ExpensesListFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.IncomesListFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;

public class EditFinanceActivity extends AppCompatActivity implements Serializable {

    // Codes
    public static final String FINANCE = "finance";
    public static final String FINANCE_TYPE = "finance_type";


    // View comps
    private EditText editTitle;
    private EditText editAmount;
    private EditText editDesc;
    private ImageView btnMic;
    private ImageView btnRec;
    private Button cancelBtn;
    private Button editBtn;

    // Permission
    private final int PERMISSION_ALL = 1;
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    // Finance attributes
    private Finance finance;
    private String financeType;

    // Recorder
    private MediaRecorder mediaRecorder;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_finance);
        parseIntent();
    }

    private void init() {
//        parseIntent();
        initView();
        initListeners();
        if (finance.getDescription() instanceof File) {
            initFile();
            initRecordingListeners();
        }
    }

    private void parseIntent() {
        Intent intent = getIntent();
        this.finance = (Finance) intent.getExtras().getSerializable(FINANCE);
        this.financeType = intent.getExtras().getString(FINANCE_TYPE);
        if (finance.getDescription() instanceof File) {
            if (hasPermissions(this, PERMISSIONS)) {
                init();
            } else {
                requestPermissions(PERMISSIONS, PERMISSION_ALL);
            }
            return;
        }
        init();
    }

    private void initView() {
        editTitle = findViewById(R.id.editTitle);
        editTitle.setText(finance.getTitle());
        editAmount = findViewById(R.id.editAmount);
        editAmount.setText(String.valueOf(finance.getAmount()));
        editDesc = findViewById(R.id.editDesc);
        btnMic = findViewById(R.id.buttonMic);
        btnRec = findViewById(R.id.buttonRecording);
        cancelBtn = findViewById(R.id.cancelBtn);
        editBtn = findViewById(R.id.editBtn);

        if (finance.getDescription() instanceof String) {
            editDesc.setText(finance.getDescription().toString());
        } else {
            editDesc.setVisibility(View.GONE);
            btnMic.setVisibility(View.VISIBLE);
        }
    }


    private void initListeners() {
        cancelBtn.setOnClickListener(v -> {
            finish();
        });

        editBtn.setOnClickListener(v -> {
            boolean valid = true;
            if (editTitle.getText().toString().equals("")) {
                valid = false;
                Toast.makeText(this, "Naslov je obavezan", Toast.LENGTH_SHORT).show();
            } else {
                finance.setTitle(editTitle.getText().toString());
            }

            if (editAmount.getText().toString().equals("")) {
                valid = false;
                Toast.makeText(this, "Kolicina je obavezna", Toast.LENGTH_SHORT).show();
            } else {
                finance.setAmount(Integer.parseInt(editAmount.getText().toString()));
            }

            if (finance.getDescription() instanceof String) {
                if (editDesc.getText().toString().equals("")) {
                    valid = false;
                    Toast.makeText(this, "Opis je obavezan", Toast.LENGTH_SHORT).show();
                } else {
                    finance.setDescription(editDesc.getText().toString());
                }
            } else {
//                if (inputViewModel.getAudio().getValue().length() == 0) {
//                    valid = false;
//                    Toast.makeText(getContext(), "Opis je obavezan", Toast.LENGTH_SHORT).show();
//                } else {
//                    file = inputViewModel.getAudio().getValue();
//                }
            }

            if (!valid) {
                return;
            }

            Intent returnIntent = new Intent();
            if (this.financeType.equals("income")) {
                returnIntent.putExtra(IncomesListFragment.INCOME_RECEIVED, this.finance);
                setResult(Activity.RESULT_OK, returnIntent);

            } else if (this.financeType.equals("expense")) {
                returnIntent.putExtra(ExpensesListFragment.EXPENSE_RECEIVED, this.finance);
                setResult(Activity.RESULT_OK, returnIntent);
            }
            finish();

        });

    }

    // Recording
    private void initFile() {
        File folder = new File(this.getFilesDir(), "sounds");
        String uniqueString = UUID.randomUUID().toString();
        if (!folder.exists()) folder.mkdir(); // ako ne postoji napravim ga
        file = new File(folder, uniqueString + ".3pg");
    }

    private void initMediaRecorder(File file) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(file); // ono sto se snimi ispise se u file-u

    }

    private void initRecordingListeners() {
        btnMic.setOnClickListener(v -> {
            try {
                btnMic.setVisibility(View.GONE);
                btnRec.setVisibility(View.VISIBLE);
                initMediaRecorder(file);
                mediaRecorder.prepare(); // lifecycle
                mediaRecorder.start(); // pocinje snimanje


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnRec.setOnClickListener(v -> {
            btnMic.setVisibility(View.VISIBLE);
            btnRec.setVisibility(View.GONE);
            mediaRecorder.stop(); // zaustavi se snimanje
            finance.setDescription(file);
            mediaRecorder.release();
            mediaRecorder = null; // skidam referencu da ne bi bilo memory leakova

        });
    }


    // Permissions
    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0) {
                StringBuilder permissionsDenied = new StringBuilder();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        permissionsDenied.append("\n").append(permissions[i]);
                    }
                }
                // nijedna nije odbijena
                if (permissionsDenied.toString().length() == 0) {
                    init();
                } else {
                    Toast.makeText(this, "Missing permissions!" + permissionsDenied.toString(), Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        }
    }

}