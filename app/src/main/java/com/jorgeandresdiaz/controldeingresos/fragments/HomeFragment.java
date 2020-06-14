package com.jorgeandresdiaz.controldeingresos.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jorgeandresdiaz.controldeingresos.R;
import com.jorgeandresdiaz.controldeingresos.fragments.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_outcome)
    TextView tvOutcome;
    @BindView(R.id.barChart)
    BarChart barChart;

    private double total;
    private double income;
    private double outcome;

    private List<Transaction> transactions;

    private HomeFragment(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public static Fragment newFragment(List<Transaction> transactions) {
        return new HomeFragment(transactions);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        initAmountValues();
        initBarChart();
    }

    private void initBarChart() {
        List<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, (float) total));
        barEntries.add(new BarEntry(2, (float) income));
        barEntries.add(new BarEntry(3, (float) outcome));

        BarDataSet barDataSet = new BarDataSet(barEntries, getString(R.string.conventions));
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barDataSet.setColors(ColorTemplate.createColors(new int[]{Color.BLUE, Color.GREEN, Color.RED}));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);

        String[] titles = new String[]{null, getString(R.string.total), getString(R.string.income), getString(R.string.outcome)};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(titles));
    }

    private void initAmountValues() {
        total = getTotal();
        tvTotal.setTextColor(total > 0 ? Color.BLUE : Color.RED);
        tvTotal.setText("$ " + total);

        income = getIncome();
        tvIncome.setText("$ " + income);

        outcome = getOutcome();
        tvOutcome.setText("$ " + outcome);
    }

    private double getIncome() {
        double income = 0;
        for (Transaction transaction : transactions) {
            if (transaction.isIncome()) {
                income += transaction.getAmount();
            }
        }

        return income;
    }

    private double getOutcome() {
        double outcome = 0;
        for (Transaction transaction : transactions) {
            if (!transaction.isIncome()) {
                outcome += transaction.getAmount();
            }
        }

        return outcome;
    }

    private double getTotal() {
        double total = 0;
        for (Transaction transaction : transactions) {
            total += transaction.isIncome() ? transaction.getAmount() : -transaction.getAmount();
        }

        return total;
    }

}
