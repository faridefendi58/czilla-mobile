package id.web.jagungbakar.chordzilla.ui.mysong;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MySongsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MySongsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my songs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
