package com.hkm.staffvend.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.staffvend.R;
import com.hkm.staffvend.event.ApplicationConstant;
import com.hkm.staffvend.event.BS;
import com.hkm.staffvend.event.Utils;
import com.hkm.staffvend.compon.usage.SimpleDividerItemDecoration;
import com.hkm.staffvend.compon.adapter.TableAdapter;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Bill.BillContainer;
import com.marshalchen.ultimaterecyclerview.animators.SlideInRightAnimator;
import com.squareup.otto.Subscribe;

import eu.davidea.flexibleadapter.FlexibleAdapter;

import static com.hkm.staffvend.event.ApplicationConstant.*;

/**
 * Created by hesk on 27/1/16.
 */
public class SecBillCollection extends AppCompatActivity implements
        ActionMode.Callback,
        TableAdapter.OnItemClickListener,
        SearchView.OnQueryTextListener,
        FlexibleAdapter.OnDeleteCompleteListener {


    public static final String TAG = SecBillCollection.class.getSimpleName();

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The current activated item position.
     */
    private static final int INVALID_POSITION = -1;
    private int mActivatedPosition = INVALID_POSITION;

    /**
     * RecyclerView and related objects
     */
    private RecyclerView mRecyclerView;
    private TableAdapter mAdapter;
    private ActionMode mActionMode;
    private Snackbar mSnackBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final Handler mSwipeHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0: //Stop
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(true);
                    return true;
                case 1: //1 Start
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                    return true;
                default:
                    return false;
            }
        }
    });
    /**
     * FAB
     */
    private int intent_function;
    private BillContainer instance;

    private boolean loadIntentArguements() {
        Bundle b = getIntent().getExtras();
        int h = b.getInt(INTENT_TABLE_FUNCTION, -1);
        if (h == -1) return false;
        intent_function = h;
        instance = RestaurantPOS.getInstance(getApplication()).getBillContainer();
        return true;
    }

    private ProgressBar mbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.as_content_bill_list);
        Log.d(TAG, "onCreate");
        if (!loadIntentArguements()) return;
        //Adapter & RecyclerView
        Bundle data = getIntent().getExtras();


        String[] config = data.getStringArray(ApplicationConstant.INTENT_TABLE_FILTER);
        int r = config[1].equalsIgnoreCase("paid") ? R.layout.item_bill_revisit : R.layout.item_bill_preview;

        mAdapter = new TableAdapter(this, instance, r);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true); //Size of views will not change as the data changes
        mRecyclerView.setItemAnimator(new SlideInRightAnimator());
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, null)));

        mbar = (ProgressBar) findViewById(R.id.lylib_ui_loading_circle);
        //Add FastScroll to the RecyclerView
        // FastScroller fastScroller = (FastScroller) findViewById(R.id.fast_scroller);
        //  fastScroller.setRecyclerView(mRecyclerView);
        // fastScroller.setViewsToUse(R.layout.fast_scroller, R.id.fast_scroller_bubble, R.id.fast_scroller_handle);
        //FAB
        //Update EmptyView (by default EmptyView is visible)

        //SwipeToRefresh
        initializeSwipeToRefresh();

        //Restore previous state
        if (savedInstanceState != null) {
            //Selection
            mAdapter.onRestoreInstanceState(savedInstanceState);
            if (mAdapter.getSelectedItemCount() > 0) {
                // mActionMode = startSupportActionMode(this);
                // setContextTitle(mAdapter.getSelectedItemCount());
            }
            //Previously serialized activated item position
            if (savedInstanceState.containsKey(STATE_ACTIVATED_POSITION))
                setSelection(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        mAdapter.loadList(instance.getByBundle(data));
        updateEmptyView();
    }

    private void initializeSwipeToRefresh() {
        //Swipe down to force synchronize
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setDistanceToTriggerSync(390);
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_purple, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  mAdapter.updateDataSet();
//				mAdapter.updateDataSetAsync("example parameter for List1");
                refresh();
                mSwipeRefreshLayout.setEnabled(false);
                mSwipeHandler.sendEmptyMessageDelayed(0, 2000L);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState start!");
        mAdapter.onSaveInstanceState(outState);
        if (mActivatedPosition != AdapterView.INVALID_POSITION) {
            //Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
            Log.d(TAG, STATE_ACTIVATED_POSITION + "=" + mActivatedPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu called!");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        initSearchView(menu);
        return true;
    }

    private void initSearchView(final Menu menu) {
        //Associate searchable configuration with the SearchView
        Log.d(TAG, "onCreateOptionsMenu setup SearchView!");
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));
        searchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.findItem(R.id.action_about).setVisible(false);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                menu.findItem(R.id.action_about).setVisible(true);
                return false;
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.v(TAG, "onPrepareOptionsMenu called!");
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //Has searchText?
        if (!mAdapter.hasSearchText()) {
            Log.d(TAG, "onPrepareOptionsMenu Clearing SearchView!");
            searchView.setIconified(true);// This also clears the text in SearchView widget
        } else {
            searchView.setQuery(mAdapter.getSearchText(), false);
            searchView.setIconified(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!mAdapter.hasSearchText()
                || !mAdapter.getSearchText().equalsIgnoreCase(newText)) {
            Log.d(TAG, "onQueryTextChange newText: " + newText);
            mAdapter.setSearchText(newText);
            //Filter the items and notify the change!
            mAdapter.updateDataSet();
        }

     /*   if (mAdapter.hasSearchText()) {
            //mFab.setVisibility(View.GONE);
            ViewCompat.animate(mFab)
                    .scaleX(0f)
                    .scaleY(0f)
                    .alpha(0f)
                    .setDuration(100)
                    .start();
        } else {
            //mFab.setVisibility(View.VISIBLE);
            ViewCompat.animate(mFab)
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(100)
                    .start();
        }
*/
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.v(TAG, "onQueryTextSubmit called!");
        return onQueryTextChange(query);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
        /*    MessageDialog.newInstance(
                    R.drawable.ic_info_grey600_24dp,
                    getString(R.string.about_title),
                    getString(R.string.about_body,
                            Utils.getVersionName(this),
                            Utils.getVersionCode(this)))
                    .show(getFragmentManager(), MessageDialog.TAG);*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handling RecyclerView when empty.
     * Note: The order how the 3 Views (RecyclerView, EmptyView, FastScroller)
     * are placed in the Layout is important!
     */
    private void updateEmptyView() {
        // FastScroller fastScroller = (FastScroller) findViewById(R.id.fast_scroller);
        TextView emptyView = (TextView) findViewById(R.id.empty);
        RelativeLayout rlemptyview = (RelativeLayout) findViewById(R.id.empty_view_holder);
        emptyView.setText(getString(R.string.no_items));
        if (mAdapter.isEmpty()) {
            //  fastScroller.setVisibility(View.GONE);
            rlemptyview.setVisibility(View.VISIBLE);
            //showProgressBar(true);
        } else {
            //   fastScroller.setVisibility(View.VISIBLE);
            rlemptyview.setVisibility(View.GONE);
            // showProgressBar(false);
        }

        showProgressBar(false);
    }

    protected void showProgressBar(boolean b) {
        if (b) {
            mbar.setVisibility(View.VISIBLE);
            mbar.setAlpha(0f);
            mbar.animate().alpha(1f);
        } else {
            mbar.animate().alpha(0f).withEndAction(new Runnable() {
                @Override
                public void run() {
                    mbar.setVisibility(View.GONE);
                }
            });

        }
    }

    public void setSelection(final int position) {
        Log.v(TAG, "setSelection called!");
        setActivatedPosition(position);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(position);
            }
        }, 1000L);
    }

    private void setActivatedPosition(int position) {
        Log.d(TAG, "ItemList New mActivatedPosition=" + position);
        mActivatedPosition = position;
    }

/*

    @Override
    public void onTitleModified(int position, String newTitle) {
        Item item = mAdapter.getItem(position);
        item.setTitle(newTitle);
        mAdapter.updateItem(position, item);
    }

    @Override
    public boolean onListItemClick(int position) {
        if (mActionMode != null && position != INVALID_POSITION) {
            toggleSelection(position);
            return true;
        } else {
            //Notify the active callbacks interface (the activity, if the
            //fragment is attached to one) that an item has been selected.
            if (mAdapter.getItemCount() > 0) {
                if (position != mActivatedPosition) setActivatedPosition(position);
                Item item = mAdapter.getItem(position);
                //TODO: call your custom Callback, for example mCallback.onItemSelected(item.getId());
                EditItemDialog.newInstance(item, position).show(getFragmentManager(), EditItemDialog.TAG);
            }
            return false;
        }
    }

    @Override
    public void onListItemLongClick(int position) {
        Log.d(TAG, "onListItemLongClick on position " + position);
        if (mActionMode == null) {
            Log.d(TAG, "onListItemLongClick actionMode activated!");
            mActionMode = startSupportActionMode(this);
        }
        Toast.makeText(this, "ImageClick or LongClick on " + mAdapter.getItem(position).getTitle(), Toast.LENGTH_SHORT).show();
        toggleSelection(position);
    }
*/

    /**
     * Toggle the selection state of an item.<br/><br/>
     * If the item was the last one in the selection and is unselected, the selection is stopped.
     * Note that the selection must already be started (actionMode must not be null).
     *
     * @param position Position of the item to toggle the selection state
     */
    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position, false);

        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            Log.d(TAG, "toggleSelection finish the actionMode");
            mActionMode.finish();
        } else {
            Log.d(TAG, "toggleSelection update title after selection count=" + count);
            setContextTitle(count);
            mActionMode.invalidate();
        }
    }

    private void setContextTitle(int count) {
        mActionMode.setTitle(String.valueOf(count) + " " + (count == 1 ?
                getString(R.string.action_selected_one) :
                getString(R.string.action_selected_many)));
    }

    @Override
    public void onDeleteConfirmed() {
        for (Bill item : mAdapter.getDeletedItems()) {
            // Remove items from your Database. Example:
            // Log.d(TAG, "Confirm removed " + item.getTitle());
            // DatabaseService.getInstance().removeItem(item);

            instance.removeBill(item);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        //Inflate the correct Menu
        int menuId = R.menu.menu_item_list_context;
        mode.getMenuInflater().inflate(menuId, menu);
        //Activate the ActionMode Multi
        mAdapter.setMode(TableAdapter.MODE_MULTI);
        if (Utils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        } else {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light));
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }


    private String getBillN(Bill item) {
        return "#" + item.getBill_number_code() + "";
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_all:
                mAdapter.selectAll();
                setContextTitle(mAdapter.getSelectedItemCount());
                return true;
            case R.id.action_delete:
                //Build message before delete, for the Snackbar
                StringBuilder message = new StringBuilder();
                for (Integer pos : mAdapter.getSelectedItems()) {
                    message.append(getBillN(mAdapter.getItem(pos)));
                    message.append(", ");
                }
                message.append(" ").append(getString(R.string.action_deleted));

                //Remove selected items from Adapter list after message is built
                mAdapter.removeItems(mAdapter.getSelectedItems());

                //Snackbar for Undo
                //noinspection ResourceType
                mSnackBar = Snackbar.make(findViewById(R.id.main_view), message, 7000)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAdapter.restoreDeletedItems();
                                mSwipeHandler.sendEmptyMessage(0);
                            }
                        });
                mSnackBar.show();
                mAdapter.startUndoTimer(7000L + 200L, this);//+200: Using Snackbar, user can still click on the action button while bar is dismissing for a fraction of time
                mSwipeHandler.sendEmptyMessage(1);
                mSwipeHandler.sendEmptyMessageDelayed(0, 7000L);
                mActionMode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        Log.v(TAG, "onDestroyActionMode called!");
        mAdapter.setMode(TableAdapter.MODE_SINGLE);
        mAdapter.clearSelection();
        mActionMode = null;
        if (Utils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark_light, this.getTheme()));
        } else {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark_light));
        }
    }

    /**
     * Utility method called from MainActivity on BackPressed
     *
     * @return true if ActionMode was active (in case it is also terminated), false otherwise
     */
    public boolean destroyActionModeIfNeeded() {
        if (mActionMode != null) {
            mActionMode.finish();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //If ActionMode is active, back key closes it
        if (destroyActionModeIfNeeded()) return;

        //Close the App
        //DatabaseService.onDestroy();
        super.onBackPressed();
    }

    /**
     * Delegate the click event to the listener and check if selection MULTI enabled.<br/>
     * If yes, call toggleActivation.
     *
     * @param position position for starting up
     * @return true if MULTI selection is enabled, false for SINGLE selection
     */
    @Override
    public boolean onListItemClick(int position) {
        return false;
    }

    /**
     * This always calls toggleActivation after listener event is consumed.
     *
     * @param position position for starting up
     */
    @Override
    public void onListItemLongClick(int position) {

    }

    private void refresh() {
        if (mAdapter != null) {
            mAdapter.removeAll();
            mAdapter.loadList(instance.getByBundle(getIntent().getExtras()));
            updateEmptyView();
        }
    }

    @Subscribe
    public void evt(BS.BillFnc ev) {
        if (ev.function_bs == BS_SET_CURRENT) {
            instance.setFocusOnBill(ev.embed);
            setResult(Activity.RESULT_OK);
            finish();
        }
        if (ev.function_bs == BS_REFRESH_LIST) {
            refresh();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BS.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BS.getInstance().unregister(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BS.onResultFromPrevious(requestCode, resultCode, data, null, null);
    }
}
