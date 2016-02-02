package com.hkm.gogosushi.pages;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hkm.gogosushi.R;

public class ArrayListFragment extends ListFragment {

    private int number;
    private static ArrayAdapter<String> adapter;

    public static ArrayListFragment newInstance(int number) {
       final ArrayListFragment f = new ArrayListFragment();
        Log.d("ArrayListFragment", "newInstance");
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("number", number);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ArrayListFragment", "onCreate");
        number = getArguments() != null ? getArguments().getInt("number") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("ArrayListFragment", "onCreate");
        View view = inflater.inflate(R.layout.listf, container,
                false);
        View textView = view.findViewById(R.id.text);
        ((TextView) textView).setText("Fragment - " + number);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("ArrayListFragment", "onCreate");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, headPart.CHESSES);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "FragmentList position - " + position + " Item clicked: id - " + id,
                Toast.LENGTH_LONG).show();
    }
}