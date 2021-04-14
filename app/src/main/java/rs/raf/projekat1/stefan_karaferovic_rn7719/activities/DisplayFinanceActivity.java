package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

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

    // Audio player
    private MediaPlayer mediaPlayer; // pustanje
    private AudioManager audioManager; // zahteva pristup zvucniku
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener; // promena sa sistema(da li sam dobio ili izgubio pristup zvucniku)
    private AudioFocusRequest audioFocusRequest;

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
        initPlayer();
        initListeners();
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

    private void initPlayer() {
        if (finance.getDescription() instanceof File) {
            mediaPlayer = MediaPlayer.create(this, Uri.fromFile((File) finance.getDescription()));
        } else {
            return;
        }
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    private void initListeners() {
        if (finance.getDescription() instanceof String) {
            return;
        }

        btnPlay.setOnClickListener(v -> {
            play();
        });

        btnPause.setOnClickListener(v -> {
            pause();
        });


        mediaPlayer.setOnCompletionListener(v -> {
            pause();
        });


        onAudioFocusChangeListener = focusChange -> {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: // izgubljen audio fokus na kratak period
                case AudioManager.AUDIOFOCUS_LOSS: { // izgubljen audio fokus skroz
                    pause();
                }
                break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: { // naleteo sam na app sa vecim audio prioritetom
                    playerDuck(true); // smanji se zvuk
                }
                break;
                case AudioManager.AUDIOFOCUS_GAIN: {
                    playerDuck(false); // pojaca se zvuk (opet sam dobio dozvolu)
                    play();
                }
                break;
            }
        };

        // sta sve zahtevam od sistema
        audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN) // pristup zvucniku sa namerom da pustam zvuk
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA) // za sta koristim zvucnik
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) // kakav content pustam
                        .build())
                .setAcceptsDelayedFocusGain(true)
                .setWillPauseWhenDucked(true)
                .setOnAudioFocusChangeListener(onAudioFocusChangeListener) // zakaci se listener (osluskuje promene audio fokusa - sta da radim ako se audioFocus promeni)
                .build();
    }

    private void play() {
        int result = audioManager.requestAudioFocus(audioFocusRequest);
        if (result == audioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            btnPlay.setVisibility(View.GONE);
            btnPause.setVisibility(View.VISIBLE);

            mediaPlayer.start();
        }
    }

    private void pause() {
        btnPlay.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);

        mediaPlayer.pause();
    }


    public synchronized void playerDuck(boolean duck) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(duck ? 0.2f : 1.0f, duck ? 0.2f : 1.0f); // 20% - 100% (leftVolume, rightVolume)
        }
    }

    // oslobadjanje resursa
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocusRequest(audioFocusRequest); // ne interesuje me audioFocus
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }
}