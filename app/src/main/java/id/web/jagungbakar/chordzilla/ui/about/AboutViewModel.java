package id.web.jagungbakar.chordzilla.ui.about;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.web.jagungbakar.chordzilla.R;

public class AboutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Version : "+ R.string.app_version);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
