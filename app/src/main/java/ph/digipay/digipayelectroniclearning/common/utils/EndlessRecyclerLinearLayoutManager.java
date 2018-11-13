package ph.digipay.digipayelectroniclearning.common.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by joedsantiago on 03/02/2016.
 */
public class EndlessRecyclerLinearLayoutManager extends LinearLayoutManager {

    private OnLastItemVisible onLastItemVisible;

    private int lastDy;
    /**
     * Number of items remaining before invoking OnLastItemVisible
     * default value is 5
     */
    private int threshold = 7;
    /**
     * Number of items per load, default value is 20
     */
    private int itemCountPerLoad = 20;
    /**
     * If all of the remaining items has been displayed
     */
    private boolean hasNoRemainingItems = false;
    private boolean isLoadingMore = false;

    private Handler handler = new Handler(Looper.getMainLooper());
    private static final String TAG = EndlessRecyclerLinearLayoutManager.class.getSimpleName();

    public void setOnLastItemVisible(OnLastItemVisible onLastItemVisible) {
        this.onLastItemVisible = onLastItemVisible;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void reset() {
        hasNoRemainingItems = false;
        isLoadingMore = false;
    }

    public void onAllContentsLoaded() {
        hasNoRemainingItems = true;
        if (onLastItemVisible != null) {
            onLastItemVisible.onLoadMoreComplete();
        }
    }


    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    /**
     * Call this once you've received the response from server
     */
    public void resetLoadMoreState() {
        isLoadingMore = false;
        if (onLastItemVisible != null) {
            onLastItemVisible.onLoadMoreComplete();
        }
    }

    public void setItemCountPerLoad(int itemCountPerLoad) {
        this.itemCountPerLoad = itemCountPerLoad;
    }


    public EndlessRecyclerLinearLayoutManager(Context context) {
        super(context);
    }

    public EndlessRecyclerLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public EndlessRecyclerLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private boolean isScrollingDown(int currentDy){
        return currentDy>lastDy;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        if (shouldInvokeLastItemVisible() && !isLoadingMore && !hasNoRemainingItems && isScrollingDown(dy)) {
            invokeOnLastItemVisible();
        }
        try {
            return super.scrollVerticallyBy(dy, recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return dy;
        } finally {
            lastDy = dy;
        }

    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (shouldInvokeLastItemVisible() && !isLoadingMore) {
            invokeOnLastItemVisible();
        }
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    public void invokeOnLastItemVisible() {
        if (!isLoadingMore && onLastItemVisible != null) {
            Log.d(TAG, "Invoking onLastItemVisible(), first item visible  " + findFirstVisibleItemPosition()
                    + " itemCount - findFirstItemVisible == " + (getItemCount() - findFirstVisibleItemPosition()));
            isLoadingMore = true;
            onLastItemVisible.onLastItemVisible();
        }
    }

    public void onLoadMoreComplete() {
        isLoadingMore = false;
        if (onLastItemVisible != null) {
            onLastItemVisible.onLoadMoreComplete();
        }
    }


    private boolean internalShouldInvokeLastItem() {
        if (getItemCount() >= itemCountPerLoad * 2) {
            return (findLastVisibleItemPosition() % itemCountPerLoad == 0);
        } else {
            return ((findLastVisibleItemPosition() + 1) >= getItemCount());
        }
    }

    public boolean shouldInvokeLastItemVisible() {
        if ((getItemCount() - findFirstVisibleItemPosition()) <=threshold) {
            return true;
        }
        return false;
    }
}
