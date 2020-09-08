package smart.visitor.ir.ui.DeviceInfo;

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

public class DeviceInfoFragment extends Fragment {
    View root ;
    TextView tv_AppInfo_Display;
    TextView tv_AppInfo_Display_1;
    TextView tv_AppInfo_Display_2;
    TextView tv_AppInfo_Display_3;
    TextView tv_AppInfo_Display_4;
    TextView tv_AppInfo_Display_5;
    TextView tv_AppInfo_Display_6;
    TextView tv_AppInfo_Display_7;
    TextView tv_AppInfo_Display_8;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_device_info, container, false);
        InitView();
        SetItems();
        return root;
    }
    private void InitView() {
        this.tv_AppInfo_Display = root.findViewById(R.id.tv_AppInfo_Display);
        this.tv_AppInfo_Display_1 = root.findViewById(R.id.tv_AppInfo_Display_1);
        this.tv_AppInfo_Display_2 = root.findViewById(R.id.tv_AppInfo_Display_2);
        this.tv_AppInfo_Display_3 = root.findViewById(R.id.tv_AppInfo_Display_3);
        this.tv_AppInfo_Display_4 = root.findViewById(R.id.tv_AppInfo_Display_4);
        this.tv_AppInfo_Display_5 = root.findViewById(R.id.tv_AppInfo_Display_5);
        this.tv_AppInfo_Display_6 = root.findViewById(R.id.tv_AppInfo_Display_6);
        this.tv_AppInfo_Display_7 = root.findViewById(R.id.tv_AppInfo_Display_7);
        this.tv_AppInfo_Display_8 = root.findViewById(R.id.tv_AppInfo_Display_8);
    }
    private void SetItems() {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);

        Resources resources = getResources();
        //float f = resources.getDimension(R.dimen.activity_horizontal_margin);
        String DisplaySize = resources.getString(R.string.display_size);

        //int dpm = (int) getResources().getDimension(R.dimen.dpm);

        this.tv_AppInfo_Display.setText("صفحه نمایش");
        this.tv_AppInfo_Display_1.setText("Xdpi: " +String.valueOf(metrics.xdpi));
        this.tv_AppInfo_Display_2.setText("Ydpi: " +String.valueOf(metrics.ydpi));
        this.tv_AppInfo_Display_3.setText("densityDpi: " +String.valueOf(metrics.densityDpi));
        this.tv_AppInfo_Display_4.setText("density: " +String.valueOf(metrics.density));
        this.tv_AppInfo_Display_5.setText("heightPixels: " +String.valueOf(metrics.heightPixels));
        this.tv_AppInfo_Display_6.setText("widthPixels: " +String.valueOf(metrics.widthPixels));
        this.tv_AppInfo_Display_7.setText("scaledDensity: " +String.valueOf(metrics.scaledDensity));
        this.tv_AppInfo_Display_8.setText("DisplaySize: " + DisplaySize);
    }
}