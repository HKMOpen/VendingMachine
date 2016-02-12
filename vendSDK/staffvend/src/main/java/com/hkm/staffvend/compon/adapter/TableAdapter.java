package com.hkm.staffvend.compon.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hkm.staffvend.R;
import com.hkm.staffvend.ui.SecPayment;
import com.hkm.staffvend.event.Utils;
import com.hkm.staffvend.event.faster.FastScroller;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Bill.BillContainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import co.hkm.soltag.TagView;
import co.hkm.soltag.ext.LayouMode;
import eu.davidea.flexibleadapter.FlexibleAdapter;

import static com.hkm.staffvend.event.ApplicationConstant.INTENT_BILL_ID;
import static com.hkm.staffvend.event.ApplicationConstant.INTENT_TABLE_FUNCTION;
import static com.hkm.staffvend.event.ApplicationConstant.MAKE_PAYMENT;
import static com.hkm.staffvend.event.ApplicationConstant.RESULT_NEW_ORDER;

/**
 * Created by hesk on 27/1/16.
 */
public class TableAdapter extends FlexibleAdapter<ItemBeforePaid, Bill> implements FastScroller.BubbleTextGetter {

    private static final String TAG = TableAdapter.class.getSimpleName();

    public interface OnItemClickListener {
        /**
         * Delegate the click event to the listener and check if selection MULTI enabled.<br/>
         * If yes, call toggleActivation.
         *
         * @param position n+ position
         * @return true if MULTI selection is enabled, false for SINGLE selection
         */
        boolean onListItemClick(int position);

        /**
         * This always calls toggleActivation after listener event is consumed.
         *
         * @param position long click position
         */
        void onListItemLongClick(int position);
    }

    private int layout_res;
    private Activity mContext;
    private static final int
            EXAMPLE_VIEW_TYPE = 0,
            ROW_VIEW_TYPE = 1;

    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;

    //Selection fields
    private boolean
            mLastItemInActionMode = false,
            mSelectAll = false;
    private BillContainer instance;

    public TableAdapter(Object activity, BillContainer container, @LayoutRes final int item_layout) {
        super(new ArrayList<Bill>());
        instance = container;
        this.mContext = (Activity) activity;
        this.mClickListener = (OnItemClickListener) activity;
        layout_res = item_layout;
        //   addUserLearnedSelection();
    }

    private void addUserLearnedSelection() {
        if (!hasSearchText()) {
            //Define Example View
            //    Item item = new Item();
            //    item.setId(0);
            //    item.setTitle(mContext.getString(R.string.uls_title));
            //    item.setSubtitle(mContext.getString(R.string.uls_subtitle));
            //   this.mItems.set(0, item);
        }
    }

    public void loadList(List<Bill> ls) {
        Iterator<Bill> it = ls.iterator();
        while (it.hasNext()) {
            Bill bv = it.next();
            addItem(0, bv);
        }
        notifyDataSetChanged();
    }

    public final void removeAll() {
        final int e = mItems.size();
        for (int i = 0; i < e; i++) {
            removeItem(i);
        }
    }

    /**
     * Param, in this example, is not used.
     *
     * @param param A custom parameter to filter the type of the DataSet
     */
    @Override
    public void updateDataSet(String param) {
        //Refresh the original content
        // mItems = container.f()
        mItems = instance.getAll();
        addUserLearnedSelection();
        //Fill and Filter mItems with your custom list
        //Note: In case of userLearnSelection mItems is pre-initialized and after filtered.
        filterItems(mItems);
        notifyDataSetChanged();
    }

    @Override
    public void setMode(int mode) {
        super.setMode(mode);
        if (mode == MODE_SINGLE) mLastItemInActionMode = true;
    }

    @Override
    public void selectAll() {
        mSelectAll = true;
        super.selectAll(EXAMPLE_VIEW_TYPE);
    }

    @Override
    public int getItemViewType(int position) {
        //   return (position == 0 && !DatabaseService.userLearnedSelection&& !hasSearchText() ? EXAMPLE_VIEW_TYPE : ROW_VIEW_TYPE);

        return ROW_VIEW_TYPE;
    }

    @Override
    public ItemBeforePaid onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder for viewType " + viewType);
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        switch (viewType) {
           /* case EXAMPLE_VIEW_TYPE:
                return new ItemBeforePaid(
                        mInflater.inflate(R.layout.recycler_uls_row, parent, false),
                     this);*/

            default:
                return new ItemBeforePaid(
                        mInflater.inflate(layout_res, parent, false),
                        this);
        }
    }

    @Override
    public void onBindViewHolder(ItemBeforePaid holder, final int position) {
        Log.d(TAG, "onBindViewHolder for position " + position);

        //holder.mImageView.setImageResource(R.drawable.ic_account_circle_white_24dp);
        //NOTE: ViewType Must be checked ALSO here to bind the correct view
        if (getItemViewType(position) == ROW_VIEW_TYPE) {
            final Bill item = getItem(position);
            String bill_number = item.getBill_number_code() + "";
            String subtotal = BillContainer.getProjectedTotal(item) + "";
            holder.itemView.setActivated(true);
            holder.mTitle.setSelected(true);//For marquee
            StringBuilder st = new StringBuilder();
            st.append(item.getTable_id());
            st.append(" ");
            st.append("#");
            st.append(bill_number);
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(st.toString());
            holder.mTitle.setText(spanText);
            holder.mSubtitle.setText(Html.fromHtml("$" + subtotal));

            if (item.getPay_time() != null && holder.mTime != null) {
                holder.mTime.setText(item.getPay_time());
            }

            holder.newOrderFn(new Runnable() {
                @Override
                public void run() {
                    // BS.setCurrentBillEngage(item);
                    Intent in = new Intent();
                    Bundle b = new Bundle();
                    b.putLong(INTENT_BILL_ID, item.getBill_number_code());
                    in.putExtras(b);
                    mContext.setResult(RESULT_NEW_ORDER);
                    mContext.setIntent(in);
                    mContext.finish();
                }
            });

            holder.setTagClick(new TagView.OnTagClickListener() {
                @Override
                public void onTagClick(int position, String text) {

                }

                @Override
                public void onTagLongClick(int position, String text) {

                }
            });

            holder.displayEntries(BillContainer.getOrderedItemsChinese(item));

            holder.mPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(mContext, SecPayment.class);
                    Bundle b = new Bundle();
                    b.putLong(INTENT_BILL_ID, item.getBill_number_code());
                    b.putInt(INTENT_TABLE_FUNCTION, MAKE_PAYMENT);
                    in.putExtras(b);
                    mContext.startActivityForResult(in, MAKE_PAYMENT);
                }
            });

            //IMPORTANT: Example View finishes here!!
            return;
        }

        //When user scrolls this bind the correct selection status
        holder.itemView.setActivated(isSelected(position));

        //ANIMATION EXAMPLE!! ImageView - Handle Flip Animation on Select and Deselect ALL
        if (mSelectAll || mLastItemInActionMode) {
            //Reset the flags with delay
           /*  holder.mImageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSelectAll = mLastItemInActionMode = false;
                }
            }, 200L);*/
            //Consume the Animation
            //flip(holder.mImageView, isSelected(position), 200L);
        } else {
            //Display the current flip status
            //setFlipped(holder.mImageView, isSelected(position));
        }

        //This "if-else" is just an example
        if (isSelected(position)) {
            // holder.mArea.setBackgroundResource(R.drawable.image_round_selected);
        } else {
            //     holder.mArea.setBackgroundResource(R.drawable.image_round_normal);
            holder.mArea.setBackgroundResource(R.drawable.item_b_whi);
        }

        //In case of searchText matches with Title or with an Item's field
        // this will be highlighted
        if (hasSearchText()) {
            //      setHighlightText(holder.mTitle, bill_number, mSearchText);
            //      setHighlightText(holder.mSubtitle, subtotal, mSearchText);
        } else {
            //      holder.mTitle.setText(bill_number);
            //      holder.mSubtitle.setText(subtotal);
        }
    }

    @Override
    public String getTextToShowInBubble(int position) {
        //  if (!DatabaseService.userLearnedSelection && position == 0) {
        //This 'if' is for my example only
        //This is the normal line you should use: Usually it's the first letter
        //     return getItem(position).getTitle().substring(0, 1).toUpperCase();
        // }
        String code = getItem(position).getBill_number_code() + "";
        return code.substring(5); //This is for my example only
    }

    private void setHighlightText(TextView textView, String text, String searchText) {
        Spannable spanText = Spannable.Factory.getInstance().newSpannable(text);
        int i = text.toLowerCase(Locale.getDefault()).indexOf(searchText);
        if (i != -1) {
            spanText.setSpan(new ForegroundColorSpan(Utils.getColorAccent(mContext)), i,
                    i + searchText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.BOLD), i,
                    i + searchText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spanText, TextView.BufferType.SPANNABLE);
        } else {
            textView.setText(text, TextView.BufferType.NORMAL);
        }
    }

    /**
     * Custom filter.
     *
     * @param myObject   The item to filter
     * @param constraint the current searchText
     * @return true if a match exists in the title or in the subtitle, false if no match found.
     */
    @Override
    protected boolean filterObject(Bill myObject, String constraint) {
        String code = myObject.getBill_number_code() + "";
        String subtotal = BillContainer.getProjectedTotal(myObject) + "";

        String valueText = code;
        //Filter on Title
        if (valueText != null && valueText.toLowerCase().contains(constraint)) {
            return true;
        }
        //Filter on Subtitle
        valueText = subtotal;
        return valueText != null && valueText.toLowerCase().contains(constraint);
    }

    private void userLearnedSelection() {
        //TODO: Save the boolean into Settings!
        //   DatabaseService.userLearnedSelection = true;
        //  mItems.remove(0);
        // notifyItemRemoved(0);
    }


    @Override
    public String toString() {
        return mItems.toString();
    }

}