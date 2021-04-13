package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.IOException;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.InputViewModel;

public class AudioFragment extends Fragment {

    private MediaRecorder mediaRecorder;
    public static File file;
    InputViewModel inputViewModel;

    private final int PERMISSION_ALL = 1;

    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public AudioFragment() {
        super(R.layout.fragment_audio);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputViewModel = new ViewModelProvider(requireActivity()).get(InputViewModel.class);
        init(view);

    }


    private void init(View view) {
        File folder = new File(getActivity().getFilesDir(), "sounds");
        if (!folder.exists()) folder.mkdir(); // ako ne postoji napravim ga
        file = new File(folder, "record.3gp");
        initListeners(view);
    }


    private void initMediaRecorder(File file) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(file); // ono sto se snimi ispise se u file-u

    }

    private void initListeners(View view) {
        ImageView btnMic = view.findViewById(R.id.btnMic);
        ImageView btnRecording = view.findViewById(R.id.btnRecording);
        btnMic.setOnClickListener(v -> {
            try {
                btnMic.setVisibility(View.GONE);
                btnRecording.setVisibility(View.VISIBLE);
                initMediaRecorder(file);
                mediaRecorder.prepare(); // lifecycle
                mediaRecorder.start(); // pocinje snimanje


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnRecording.setOnClickListener(v -> {
            btnMic.setVisibility(View.VISIBLE);
            btnRecording.setVisibility(View.GONE);
            mediaRecorder.stop(); // zaustavi se snimanje
            inputViewModel.storeAudio(file);
            mediaRecorder.release();
            mediaRecorder = null; // skidam referencu da ne bi bilo memory leakova

        });


    }
}
