package com.jorgeandresdiaz.controldeingresos.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jorgeandresdiaz.controldeingresos.R;
import com.jorgeandresdiaz.controldeingresos.fragments.model.Transaction;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransactionFragment extends Fragment {

    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.btn_outcome)
    Button btnOutcome;
    @BindView(R.id.btn_income)
    Button btnIncome;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.tv_date)
    TextView tvDate;

    private Context context;
    private int currentDay;
    private int currentMonth;
    private int currentYear;
    private boolean incomePressed;

    private Callback callback;

    private TransactionFragment() {
    }

    public static Fragment newFragment() {
        return new TransactionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initProps();
        initView();
    }

    private void initProps() {
        context = getContext();

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
    }


    private void initView() {
        tvDate.setText((currentMonth + 1) + "/" + currentDay + "/" + currentYear);
    }

    @OnClick(R.id.btn_outcome)
    void onOutcomeButtonClicked() {
        btnIncome.setAlpha(0.3f);
        btnOutcome.setAlpha(1);
        etAmount.setTextColor(Color.RED);

        incomePressed = false;
    }

    @OnClick(R.id.btn_income)
    void onIncomeButtonClicked() {
        btnOutcome.setAlpha(0.3f);
        btnIncome.setAlpha(1);
        etAmount.setTextColor(Color.GREEN);

        incomePressed = true;
    }


    @OnClick(R.id.btn_date)
    void onDateButtonClicked() {
        DatePickerDialog datePicker = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDate.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, currentYear, currentMonth, currentDay);


        long currentTime = new Date().getTime();
        datePicker.getDatePicker().setMaxDate(currentTime);

        datePicker.show();

    }


    @OnClick(R.id.btn_accept)
    void onAcceptButtonClicked() {
        double amount = Double.parseDouble(etAmount.getText().toString());
        boolean income = incomePressed;
        String description = etDescription.getText().toString();
        String date = tvDate.getText().toString();

        Transaction transaction = new Transaction(amount, income, description, date);
        callback.saveTransaction(transaction);

        Toast.makeText(context, "Se guardó la transacción", Toast.LENGTH_SHORT).show();
        cleanView();
    }

    private void cleanView() {
        etAmount.setText(null);
        etAmount.setTextColor(Color.BLACK);
        incomePressed = false;
        btnIncome.setAlpha(1);
        btnOutcome.setAlpha(1);
        etDescription.setText(null);
        initView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    public interface Callback {

        void saveTransaction(Transaction transaction);

    }

}

