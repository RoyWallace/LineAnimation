package etong.lineanimation;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hwt on 2017/8/28.
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(T data);


    public View find(@IdRes int resId){
        return itemView.findViewById(resId);
    }
}
