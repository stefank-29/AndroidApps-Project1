package rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;

public class InputViewModel extends ViewModel {
    public static int counter = 21;

    private final MutableLiveData<String> title = new MutableLiveData<>();
    private final MutableLiveData<Integer> amount = new MutableLiveData<>();
    private final MutableLiveData<String> description = new MutableLiveData<>();
    private final MutableLiveData<File> audio = new MutableLiveData<>();


    public InputViewModel() {
        description.setValue("");
    }

    public void storeTitle(String input) {
        title.setValue(input);
    }

    public void storeAmount(Integer input) {
        amount.setValue(input);
    }

    public void storeDescription(String input) {
        description.setValue(input);
    }

    public void storeAudio(File input) {
        audio.setValue(input);
    }


    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<Integer> getAmount() {
        return amount;
    }

    public LiveData<String> getDesciption() {
        return description;
    }

    public LiveData<File> getAudio() {
        return audio;
    }
}
