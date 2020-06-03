package com.projekt_zespolowy.microclimateanalysisclient.view;

        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.FrameLayout;
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

        import org.w3c.dom.Text;

        import java.util.Calendar;
        import java.util.List;

public class MeasurementsHistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MeasurementsHistoryFragment.class.getName();

    private FragmentMeasurementsHistoryBinding binding;
    private MeasurementsHistoryViewModel viewModel;
    private TableLayout dataTable;
    private boolean initialized = false;
    private boolean update = false;

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
        binding.swipeRefresh.setRefreshing(true);
        update();
    }
    
    private void initializeData()
    {
        if(initialized==false || update==true)
        {
            dataTable.removeAllViews();

            createColumns();

            List<Sensors> sensorsList= viewModel.getSensorsHours().getValue();

            if(sensorsList!=null)
                for(int i=sensorsList.size()-1;i>-1;i--)
                {
                    // Convert timestamp to date
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(sensorsList.get(i).getTimestamp()*1000);

                    // Add missing 0's if needed
                    String time;

                    if (calendar.get(Calendar.HOUR_OF_DAY) < 10)
                        time = "0" + calendar.get(Calendar.HOUR_OF_DAY) + ":";
                    else time = calendar.get(Calendar.HOUR_OF_DAY) + ":";

                    if (calendar.get(Calendar.MINUTE) < 10)
                        time += "0" + calendar.get(Calendar.MINUTE);
                    else time += calendar.get(Calendar.MINUTE);

                    String temperature = Float.toString(sensorsList.get(i).getTemperature());
                    String airPressure = Float.toString(sensorsList.get(i).getPressure());
                    String humidity = Float.toString(sensorsList.get(i).getHumidity());
                    String airQuality;

                    if(sensorsList.get(i).getGas()<51) airQuality="Good";
                    else if(sensorsList.get(i).getGas()<101) airQuality="Average";
                    else if(sensorsList.get(i).getGas()<151) airQuality="Little Bad";
                    else if(sensorsList.get(i).getGas()<201) airQuality="Bad";
                    else if(sensorsList.get(i).getGas()<501) airQuality="Very Bad";
                    else airQuality="";


                    dataTable.addView(createRow(time,temperature,airPressure, humidity, airQuality));
                }

            binding.swipeRefresh.setRefreshing(false);
            initialized=true;
        }
    }

    private void update() {
        update=true;
        viewModel.updateSensorsHours(24);
    }


    @Override
    public void onRefresh() {
        update();
    }

    private void createColumns()
    {
        TableRow tableRow = createRow("Time", "Temperature", "Air Pressure", "Humidity", "Air Quality");
        TextView textView;

        for(int i=0; i<tableRow.getChildCount();i++)
        {
            textView = (TextView) tableRow.getChildAt(i);
            textView.setTextColor(getResources().getColor(R.color.foreground));
            textView.setBackgroundColor(Color.TRANSPARENT);
        }

        dataTable.addView(tableRow);
    }


    private TableRow createRow(String time, String temperature, String airPressure, String humidity, String airQuality) {

        TableRow tableRow = new TableRow(getContext());

        // Time Column
        TextView textViewTime = new TextView(getContext());
        textViewTime.setText(time);
        tableRow.addView(textViewTime);

        // Temperature Column
        TextView textViewTemp = new TextView(getContext());
        textViewTemp.setText(temperature);
        tableRow.addView(textViewTemp);

        // Air Pressure Column
        TextView textViewPressure = new TextView(getContext());
        textViewPressure.setText(airPressure);
        tableRow.addView(textViewPressure);

        // Humidity Column
        TextView textViewHumidity = new TextView(getContext());
        textViewHumidity.setText(humidity);
        tableRow.addView(textViewHumidity);

        // Air Quality Column
        TextView textViewAirQuality = new TextView(getContext());
        textViewAirQuality.setText(airQuality);
        tableRow.addView(textViewAirQuality);

        // Set common parameters for all textViews
        TextView textView;

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(0, 0, 2, 2);

        for(int i=0;i<tableRow.getChildCount();i++)
        {
            textView = (TextView) tableRow.getChildAt(i);
            textView.setLayoutParams(params);
            textView.setTextColor(getResources().getColor(R.color.foreground_darker));
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(getResources().getColor(R.color.background_darker));
        }

        return tableRow;
    }
}
