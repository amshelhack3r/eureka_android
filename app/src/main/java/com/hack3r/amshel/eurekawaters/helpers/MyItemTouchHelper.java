package com.hack3r.amshel.eurekawaters.helpers;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.hack3r.amshel.eurekawaters.helpers.CustomSmsAdapter;

public class MyItemTouchHelper extends ItemTouchHelper.SimpleCallback{
    public itemTouchListener listener;

    public MyItemTouchHelper(int dragDir, int swipeDir, itemTouchListener listener){
        super(dragDir, swipeDir);
        this.listener = listener;

    }

    /**return true
     * why???
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View view = ((CustomSmsAdapter.MyViewHolder) viewHolder).foreground_view;

        getDefaultUIUtil().onDraw(c,recyclerView, view, dX, dY, actionState, isCurrentlyActive);

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View view = ((CustomSmsAdapter.MyViewHolder) viewHolder).foreground_view;

        getDefaultUIUtil().onDrawOver(c,recyclerView, view, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            View view = ((CustomSmsAdapter.MyViewHolder) viewHolder).foreground_view;

            getDefaultUIUtil().onSelected(view);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwipped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        View view = ((CustomSmsAdapter.MyViewHolder) viewHolder).foreground_view;

        getDefaultUIUtil().clearView(view);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface itemTouchListener{
        void onSwipped(RecyclerView.ViewHolder viewHolder, int direction, int postion);
    }

}
