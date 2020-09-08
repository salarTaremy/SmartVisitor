package smart.visitor.ir.ui.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import Tools.LocationTools;
import smart.visitor.ir.Activity.AddOrderActivity;
import smart.visitor.ir.Activity.BaseActivity;
import smart.visitor.ir.Activity.OrderManageActivity;
import smart.visitor.ir.Activity.UpdateActivity;
import smart.visitor.ir.R;


public class HomeFragment extends Fragment {
    Context context;
    LocationTools locationTools;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        SetOnClickListener(root);
        this.context = getActivity();
        return root;
    }
    private void SetOnClickListener(View root) {
        root.findViewById(R.id.cv_AddNewOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddOrderActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.cv_ManageOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderManageActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.cv_Update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateActivity.class);
                startActivity(intent);
            }
        });
    }
}