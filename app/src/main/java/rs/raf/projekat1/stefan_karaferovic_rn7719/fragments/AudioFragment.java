package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;

public class AudioFragment extends Fragment {

    private MediaRecorder mediaRecorder;
    public static File file;

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
        if (hasPermissions(getContext(), PERMISSIONS)) {
            init(view);
        } else {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
    }

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
                    init(getView());
                } else {
                    Toast.makeText(getContext(), "Missing permissions!" + permissionsDenied.toString(), Toast.LENGTH_SHORT).show();
                    FragmentTransaction f = getChildFragmentManager().beginTransaction();
                    f.remove(this);
                    f.commit();
                }
            }
        }
    }

    private void init(View view) {

    }
}
