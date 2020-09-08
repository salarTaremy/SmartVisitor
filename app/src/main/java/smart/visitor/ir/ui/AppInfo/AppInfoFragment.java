package smart.visitor.ir.ui.AppInfo;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import smart.visitor.ir.R;
public class AppInfoFragment extends Fragment {
    View root ;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_app_info, container, false);
        InitView();
        SetItems();
        return root;
    }
    private void InitView() {

    }
    private void SetItems() {

    }
}