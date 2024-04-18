package com.rickieyinnovates.juditon.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.rickieyinnovates.juditon.R;
import com.rickieyinnovates.juditon.listeners.ItemClickListener;
import com.rickieyinnovates.juditon.models.Account;
import com.rickieyinnovates.juditon.models.Row;
import com.rickieyinnovates.juditon.models.UserData;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

    List<UserData> list;
    List<Account> accountList;
    ItemClickListener itemClickListener;
    CustomAccountsAdapter arrayAdapter;
    Context context;

    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, List<UserData> list, List<Account> accountList, ItemClickListener itemClickListener) {
        this.list = list;
        this.accountList = accountList;
        this.context = context;
        this.itemClickListener = itemClickListener;

        arrayAdapter = new CustomAccountsAdapter(context, accountList);
    }

    @Override
    public @NotNull MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_add_receipt,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        final UserData userData = list.get(position);

        holder.activitySpinner.setAdapter(arrayAdapter);

        //holder.activitySpinner.setSelection(position);
        holder.tv_email.setText(userData.getAmount());

        holder.tv_email.setOnEditorActionListener((v, actionId, event) -> {

            double message = Double.parseDouble(v.getText().toString());
            double total = 0.00;
            total += message;

            Account activity = (Account) holder.activitySpinner.getSelectedItem();

            int particulars = activity.getId();

            Row row = new Row();
            row.setParticulars(particulars);
            row.setAmount(total);

            itemClickListener.updateRow(row);


            return false;
        });

        holder.tv_delete.setOnClickListener(view -> {
            list.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + accountList.size());

        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_delete;
        EditText tv_email;
        Spinner activitySpinner;

        public MyHolder(View itemView) {
            super(itemView);

            activitySpinner = itemView.findViewById(R.id.bankSpinner);
            tv_email = itemView.findViewById(R.id.tv_email_item);
            tv_delete = itemView.findViewById(R.id.tv_delete_item);
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void UpdateData(int position,UserData userData){

        list.remove(position);
        list.add(userData);
        notifyItemChanged(position);
        notifyItemChanged(position);
    }
}
