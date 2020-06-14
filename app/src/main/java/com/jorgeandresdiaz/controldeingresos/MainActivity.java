package com.jorgeandresdiaz.controldeingresos;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jorgeandresdiaz.controldeingresos.database.TinyDB;
import com.jorgeandresdiaz.controldeingresos.fragments.HomeFragment;
import com.jorgeandresdiaz.controldeingresos.fragments.TransactionFragment;
import com.jorgeandresdiaz.controldeingresos.fragments.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TransactionFragment.Callback {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    private TinyDB tinyDB;

    private static final String TRANSACTIONS_KEY = "TRANSACTIONS_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDatabase();
        initBottomNavigationView();
        openHomeFragment();
    }

    private void initDatabase() {
        tinyDB = new TinyDB(getApplicationContext());
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedItem -> {

            switch (selectedItem.getItemId()) {
                case R.id.btn_home:
                    openHomeFragment();
                    return true;

                case R.id.btn_transaction:
                    openTransactionFragment();
                    return true;
            }

            return false;
        });
    }

    private void openTransactionFragment() {
        Fragment transactionFragment = TransactionFragment.newFragment();
        openFragment(transactionFragment);
    }

    private void openHomeFragment() {
        List<Transaction> transactions = tinyDB.getListObject(TRANSACTIONS_KEY, Transaction.class);

        Fragment homeFragment = HomeFragment.newFragment(transactions);
        openFragment(homeFragment);
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commitNowAllowingStateLoss();
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        List<Transaction> transactions = tinyDB.getListObject(TRANSACTIONS_KEY, Transaction.class);

        if (transactions == null) {
            transactions = new ArrayList<>();
        }

        transactions.add(transaction);

        tinyDB.putListObject(TRANSACTIONS_KEY, transactions);
    }
}
