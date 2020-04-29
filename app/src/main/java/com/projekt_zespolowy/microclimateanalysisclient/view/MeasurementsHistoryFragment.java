package com.projekt_zespolowy.microclimateanalysisclient.view;

        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.FrameLayout;
        import android.widget.ListView;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProvider;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import com.projekt_zespolowy.microclimateanalysisclient.R;
        import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentMeasurementsHistoryBinding;
        import com.projekt_zespolowy.microclimateanalysisclient.model.Sensors;
        import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsHistoryViewModel;

        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;

public class MeasurementsHistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MeasurementsHistoryFragment.class.getName();

    private FragmentMeasurementsHistoryBinding binding;
    private MeasurementsHistoryViewModel viewModel;
    private TableLayout dataTable;
    private boolean initialized = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MeasurementsHistoryViewModel.class);

        viewModel.getSensorsHours().observe(getViewLifecycleOwner(), sensors -> {
            binding.swipeRefresh.setRefreshing(false);
            initializeData();
        });

        binding = FragmentMeasurementsHistoryBinding.inflate(inflater);
        binding.swipeRefresh.setColorSchemeResources(R.color.accent);
        binding.swipeRefresh.setOnRefreshListener(this);

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
        binding.swipeRefresh.setRefreshing(true);
        update();
    }

    private void initializeData()
    {
        if(initialized==false)
        {
            List<Sensors> sensorsList= viewModel.getSensorsHours().getValue();

            if(sensorsList!=null)
                for(int i=sensorsList.size()-1;i>-1;i--)
                {
                    // Convert timestamp to date
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(sensorsList.get(i).getTimestamp()*1000);

                    String time = calendar.get(Calendar.HOUR)+ ":" + calendar.get(Calendar.MINUTE);
                    String temperature = Float.toString(sensorsList.get(i).getTemperature());
                    String airPressure = Float.toString(sensorsList.get(i).getPressure());
                    String humidity = Float.toString(sensorsList.get(i).getHumidity());

                    addRow(time,temperature,airPressure, humidity, "airQuality");
                }

            binding.swipeRefresh.setRefreshing(false);
            initialized=true;
        }
    }

    private void update() {
        viewModel.updateSensorsHours(24);
    }


    @Override
    public void onRefresh() {
        update();
    }

    private void createColumns()
    {
        TableRow tableRow = new TableRow(getContext());

        // Time Column
        TextView textViewTime = new TextView(getContext());
        textViewTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        textViewTime.setText(("Time"));
        textViewTime.setTextColor(getResources().getColor(R.color.foreground));
        textViewTime.setGravity(Gravity.CENTER);

        tableRow.addView(textViewTime);

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

    private void addRow(String time, String temperature, String airPressure, String humidity, String airQuality) {

        TableRow tableRow = new TableRow(getContext());

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(0, 0, 2, 2);

        // Time Column
        TextView textViewTime = new TextView(getContext());
        textViewTime.setLayoutParams(params);
        textViewTime.setText(time);
        textViewTime.setTextColor(getResources().getColor(R.color.foreground_darker));
        textViewTime.setGravity(Gravity.CENTER);
        textViewTime.setBackgroundColor(getResources().getColor(R.color.background_darker));

        tableRow.addView(textViewTime);

        // Temperature Column
        TextView textViewTemp = new TextView(getContext());
        textViewTemp.setLayoutParams(params);
        textViewTemp.setText(temperature);
        textViewTemp.setTextColor(getResources().getColor(R.color.foreground_darker));
        textViewTemp.setGravity(Gravity.CENTER);
        textViewTemp.setBackgroundColor(getResources().getColor(R.color.background_darker));

        tableRow.addView(textViewTemp);

        // Air Pressure Column
        TextView textViewPressure = new TextView(getContext());
        textViewPressure.setLayoutParams(params);
        textViewPressure.setText(airPressure);
        textViewPressure.setTextColor(getResources().getColor(R.color.foreground_darker));
        textViewPressure.setGravity(Gravity.CENTER);
        textViewPressure.setBackgroundColor(getResources().getColor(R.color.background_darker));

        tableRow.addView(textViewPressure);

        // Humidity Column
        TextView textViewHumidity = new TextView(getContext());
        textViewHumidity.setLayoutParams(params);
        textViewHumidity.setText(humidity);
        textViewHumidity.setTextColor(getResources().getColor(R.color.foreground_darker));
        textViewHumidity.setGravity(Gravity.CENTER);
        textViewHumidity.setBackgroundColor(getResources().getColor(R.color.background_darker));

        tableRow.addView(textViewHumidity);

        // Air Quality Column
        TextView textViewAirQuality = new TextView(getContext());
        textViewAirQuality.setLayoutParams(params);
        textViewAirQuality.setText(airQuality);
        textViewAirQuality.setTextColor(getResources().getColor(R.color.foreground_darker));
        textViewAirQuality.setGravity(Gravity.CENTER);
        textViewAirQuality.setBackgroundColor(getResources().getColor(R.color.background_darker));

        tableRow.addView(textViewAirQuality);


        dataTable.addView(tableRow);
    }
}
