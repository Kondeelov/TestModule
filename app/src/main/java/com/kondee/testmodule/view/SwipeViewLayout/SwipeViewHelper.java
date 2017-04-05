package com.kondee.testmodule.view.SwipeViewLayout;

import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kondee on 4/5/2017.
 */

public class SwipeViewHelper {

    private static final String TAG = "Kondee";
    private Map<String, SwipeViewLayout> layoutMap = Collections.synchronizedMap(new HashMap<String, SwipeViewLayout>());
    private Map<String, SwipeViewState> stateMap = Collections.synchronizedMap(new HashMap<String, SwipeViewState>());
    private boolean openOnlyOne = false;

    public void bind(String id, SwipeViewLayout layout) {

        layoutMap.values().remove(layout);
        layoutMap.put(id, layout);

        layout.abort();
        layout.setOnStateChangeListener(new SwipeViewLayout.onStateChangeListener() {
            @Override
            public void onStageChange(SwipeViewState state) {
                stateMap.put(id, state);

                if (openOnlyOne) {
                    closeOthers(id, layout);
                }
            }
        });

        if (!stateMap.containsKey(id)) {
            stateMap.put(id, SwipeViewState.STATE_CLOSE);
            layout.close(false);
        } else {
            SwipeViewState state = stateMap.get(id);

            if (state == SwipeViewState.STATE_CLOSE || state == SwipeViewState.STATE_SWIPE) {
                layout.close(false);
            } else {
                layout.open(false);
            }
        }
    }

    private void closeOthers(String id, SwipeViewLayout layout) {

        if (getOpenCount() > 0) {
            for (Map.Entry<String, SwipeViewState> entry : stateMap.entrySet()) {
                if (!id.equals(entry.getKey())) {
                    stateMap.put(id, SwipeViewState.STATE_CLOSE);
                }
            }

            for (SwipeViewLayout swipeViewLayout : layoutMap.values()) {
                if (layout != swipeViewLayout) {
                    swipeViewLayout.close(true);
                }
            }
        }
    }

    private int getOpenCount() {
        int total = 0;
        for (SwipeViewState state : stateMap.values()) {
            if (state == SwipeViewState.STATE_OPEN) {
                total++;
            }
        }
        return total;
    }

    public SwipeViewHelper setOpenOnlyOne(boolean openOnlyOne) {
        this.openOnlyOne = openOnlyOne;
        return this;
    }

    public SwipeViewState getState(String position) {
        return stateMap.get(position);
    }
}
