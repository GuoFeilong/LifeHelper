package com.lifehelper.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifehelper.PlanNodeTable;
import com.lifehelper.R;
import com.lifehelper.RouteLineNodeTable;
import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.MyPlanNodeTable;
import com.lifehelper.entity.MyRouteLineTable;
import com.lifehelper.presenter.impl.GreenDaoPresenterImpl;
import com.lifehelper.tools.T;
import com.lifehelper.tools.ViewUtils;
import com.lifehelper.view.GreenDaoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jsion on 16/3/17.
 */
public class RouteLineLocationFragment extends BaseFragment implements GreenDaoView {
    @Bind(R.id.iv_switch_location)
    ImageView mSwitchLocation;
    @Bind(R.id.et_start_location)
    EditText mStartAddress;
    @Bind(R.id.et_end_location)
    EditText mTargetAddress;
    @Bind(R.id.rlv_route_line_history)
    RecyclerView mRouteLineHistory;
    private RouteLineHistoryAdapter routeLineHistoryAdapter;
    private GreenDaoPresenterImpl mGreenDaoPresenter;
    private List<MyRouteLineTable> mRouteLines;

    @OnClick(R.id.et_start_location)
    void startAddGetFocus() {
        mStartAddress.setFocusable(true);
        mStartAddress.setFocusableInTouchMode(true);
        mStartAddress.requestFocus();

        mTargetAddress.setFocusable(false);
        mTargetAddress.setFocusableInTouchMode(false);
        ViewUtils.showSoftInput(getActivity(), mStartAddress);

    }

    @OnClick(R.id.et_end_location)
    void endAddGetFocus() {
        mStartAddress.setFocusable(false);
        mStartAddress.setFocusableInTouchMode(false);

        mTargetAddress.setFocusable(true);
        mTargetAddress.setFocusableInTouchMode(true);
        mTargetAddress.requestFocus();
        ViewUtils.showSoftInput(getActivity(), mTargetAddress);
    }

    @OnClick(R.id.iv_switch_location)
    void switchStartAndTarget() {
        T.show(getActivity(), "调换出发地和目的地", 0);
    }

    @Override
    public void bindRoutePlanNodes(List<RouteLineNodeTable> routeLineNodeTable) {
    }


    @Override
    public void bindPlanNode(List<PlanNodeTable> planNodeTable) {
        List<PlanNodeTable> startPlanNodeTable = new ArrayList<>();
        List<PlanNodeTable> targetPlanNodeTable = new ArrayList<>();

        for (int i = 0; i < planNodeTable.size(); i++) {
            if (i % 2 == 0) {
                startPlanNodeTable.add(planNodeTable.get(i));
            } else {
                targetPlanNodeTable.add(planNodeTable.get(i));
            }
        }

        for (int j = 0; j < startPlanNodeTable.size(); j++) {
            MyRouteLineTable myRouteLineTable = new MyRouteLineTable();
            MyPlanNodeTable star = new MyPlanNodeTable();
            MyPlanNodeTable target = new MyPlanNodeTable();

            star.setNodeAddress(startPlanNodeTable.get(j).getNodeAddress());
            target.setNodeAddress(targetPlanNodeTable.get(j).getNodeAddress());

            myRouteLineTable.setStartPlanNode(star);
            myRouteLineTable.setTargetPlanNode(target);
            mRouteLines.add(myRouteLineTable);
        }
        Collections.reverse(mRouteLines);
        routeLineHistoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void insertPlanNodes(PlanNodeTable planNodeTable) {

    }

    @Override
    public void insertRoutePlanNodes(RouteLineNodeTable routeLineNodeTable) {

    }

    @Override
    public void clearTable() {
        mRouteLines.clear();
    }

    public interface OnGetFragmentValueListener {
        void startAddChanged(String startAdd);

        void targetAddChanged(String targetAdd);
    }

    private OnGetFragmentValueListener onGetFragmentValueListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_line_location, container, false);
        init(view);
        return view;
    }

    @Override
    public void initData() {
        mGreenDaoPresenter = new GreenDaoPresenterImpl(this, getActivity());
        mRouteLines = new ArrayList<MyRouteLineTable>();
        routeLineHistoryAdapter = new RouteLineHistoryAdapter();
    }

    @Override
    public void initEvent() {
        mTargetAddress.setTag(ET_TAG._TARGET);
        mStartAddress.setTag(ET_TAG._START);

        mTargetAddress.addTextChangedListener(new MyTextWatcher(mTargetAddress));
        mStartAddress.addTextChangedListener(new MyTextWatcher(mStartAddress));

        Bundle args = getArguments();
        if (args != null) {
            String desc = args.getString(MyConstance.BOTTOM_SHEET_DESC);
            if (!TextUtils.isEmpty(desc)) {
                mTargetAddress.setText(desc);
            }
        }

        mRouteLineHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRouteLineHistory.setAdapter(routeLineHistoryAdapter);
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnGetFragmentValueListener) {
            onGetFragmentValueListener = (OnGetFragmentValueListener) activity;
        } else {
            throw new IllegalStateException("Your activity must impl the callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onGetFragmentValueListener = null;
    }

    class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch ((int) editText.getTag()) {
                case ET_TAG._START:
                    onGetFragmentValueListener.startAddChanged(s.toString());
                    break;
                case ET_TAG._TARGET:
                    onGetFragmentValueListener.targetAddChanged(s.toString());
                    break;
            }
        }
    }

    public static class ET_TAG {
        public static final int _START = 44;
        public static final int _TARGET = 45;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGreenDaoPresenter != null) {
            mGreenDaoPresenter.queryRoutePlanNodes();
            mGreenDaoPresenter.queryPlanNodes();
        }
    }

    class RouteLineHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = viewType == VIEW_TYPE._CLEAR_ITEM
                    ? new ClearItemVH(LayoutInflater.from(getActivity()).inflate(R.layout.item_route_line_clear_history, parent, false))
                    : new CommonHistoryItemVH(LayoutInflater.from(getActivity()).inflate(R.layout.item_route_line_history, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position != mRouteLines.size()) {
                MyRouteLineTable routeLineNodeTable = mRouteLines.get(position);
                CommonHistoryItemVH commonHistoryItemVH = (CommonHistoryItemVH) holder;
                commonHistoryItemVH.history.setText(routeLineNodeTable.getStartPlanNode().getNodeAddress() + "　－　" + routeLineNodeTable.getTargetPlanNode().getNodeAddress());
            } else {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGreenDaoPresenter.clearRoutePlanNodes();
                        mGreenDaoPresenter.clearPlanNode();
                        mGreenDaoPresenter.queryRoutePlanNodes();
                        mGreenDaoPresenter.queryPlanNodes();
                    }
                });
            }

            if (mRouteLines.size() == 0) {
                holder.itemView.setVisibility(View.GONE);
            } else {
                holder.itemView.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public int getItemCount() {
            return mRouteLines.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            return position == mRouteLines.size() ? VIEW_TYPE._CLEAR_ITEM : VIEW_TYPE._COMMON_ITEM;
        }

    }

    static final class VIEW_TYPE {
        public static final int _COMMON_ITEM = 12;
        public static final int _CLEAR_ITEM = 13;
    }

    class CommonHistoryItemVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_route_line_histotry)
        TextView history;

        public CommonHistoryItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ClearItemVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_Item_route_line_clear)
        TextView clearHistory;

        public ClearItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

