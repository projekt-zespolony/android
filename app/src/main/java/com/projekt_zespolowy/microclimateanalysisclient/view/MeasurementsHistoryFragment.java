package com.projekt_zespolowy.microclimateanalysisclient.view;

        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.FrameLayout;
        import android.widget.LinearLayout;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import androidx.annotation.ColorRes;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProvider;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import com.google.android.material.tabs.TabLayout;
        import com.projekt_zespolowy.microclimateanalysisclient.MainActivity;
        import com.projekt_zespolowy.microclimateanalysisclient.R;
        import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentMeasurementsHistoryBinding;
        import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsFromDayViewModel;

public class MeasurementsHistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MeasurementsHistoryFragment.class.getName();

    private FragmentMeasurementsHistoryBinding binding;
    private MeasurementsFromDayViewModel viewModel;
    private TableLayout dataTable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MeasurementsFromDayViewModel.class);

        binding = FragmentMeasurementsHistoryBinding.inflate(inflater);
        dataTable = binding.DataTable;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(15, 10, 15, 10);
        dataTable.setLayoutParams(params);
        dataTable.setBackgroundColor(getResources().getColor(R.color.table_background));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_measurements_history);
        createColumns();
        update();
    }

    private void update() {


    }

    @Override
    public void onRefresh() {
        update();
    }

    private void createColumns()
    {
        TableRow tableRow = new TableRow(getContext());

        // Time Column
        TextView textViewId = new TextView(getContext());
        textViewId.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        textViewId.setText(("Time"));
        textViewId.setTextColor(getResources().getColor(R.color.foreground));
        textViewId.setGravity(Gravity.CENTER);

        tableRow.addView(textViewId);

        // Temperature Column
        TextView textViewTemp = new TextView(getContext());
        textViewTemp.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        textViewTemp.setText(("Temperature"));
        textViewTemp.setTextColor(getResources().getColor(R.color.foreground));
        textViewTemp.setGravity(Gravity.CENTER);

        tableRow.addView(textViewTemp);

        // Air Pressure Column
        TextView textViewPressure = new TextView(getContext());
        textViewPressure.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        textViewPressure.setText(("Air Pressure"));
        textViewPressure.setTextColor(getResources().getColor(R.color.foreground));
        textViewPressure.setGravity(Gravity.CENTER);

        tableRow.addView(textViewPressure);

        // Humidity Column
        TextView textViewHumidity = new TextView(getContext());
        textViewHumidity.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        textViewHumidity.setText(("Humidity"));
        textViewHumidity.setTextColor(getResources().getColor(R.color.foreground));
        textViewHumidity.setGravity(Gravity.CENTER);

        tableRow.addView(textViewHumidity);

        // Air Quality Column
        TextView textViewAirQuality = new TextView(getContext());
        textViewAirQuality.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        textViewAirQuality.setText(("Air quality"));
        textViewAirQuality.setTextColor(getResources().getColor(R.color.foreground));
        textViewAirQuality.setGravity(Gravity.CENTER);

        tableRow.addView(textViewAirQuality);


        dataTable.addView(tableRow);
    }
}
