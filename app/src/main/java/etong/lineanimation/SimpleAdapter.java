package etong.lineanimation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hwt on 2017/8/28.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.Holder> {

    private List<String> list;

    public SimpleAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, null);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends BaseHolder<String> {

        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) find(R.id.textView);
        }

        @Override
        public void bindData(String data) {
            textView.setText(data);
        }
    }
}
