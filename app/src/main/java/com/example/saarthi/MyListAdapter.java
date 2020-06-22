package com.example.saarthi;

import android.app.Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saarthi.Model.Product;

import java.util.HashMap;
import java.util.List;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private List<Product> productList;

    public void updateData(List<Product> products) {
        productList = products;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    public MyListAdapter(Activity context, List<Product> productList) {
        super(context, R.layout.my_list_view);
        // TODO Auto-generated constructor stub
        this.productList = productList;
        this.context=context;
        System.out.println(productList.size());

    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_list_view, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);

        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        EditText Quantity = (EditText) rowView.findViewById(R.id.Quantity);

        titleText.setText(productList.get(position).getProductname());
        subtitleText.setText(productList.get(position).getPrice().toString());
        System.out.println(productList.get(position).getPrice().toString());
        Quantity.setText(productList.get(position).getQuantity().toString());

        Quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0)
                    productList.get(position).setQuantity(Long.parseLong(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rowView;

    };

    public HashMap<String, Product> getHashMap() {
        HashMap<String, Product> hashMap = new HashMap<>();
        for(int i= 0; i<productList.size(); i++) {
            if(productList.get(i).getQuantity() > 0) {
                hashMap.put(productList.get(i).getId().toString(), productList.get(i));
            }
        }
        return hashMap;
    }
}