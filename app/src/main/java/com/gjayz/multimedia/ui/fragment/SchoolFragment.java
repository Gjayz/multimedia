package com.gjayz.multimedia.ui.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.School;
import com.gjayz.multimedia.ui.adapter.SchoolAdapter;
import com.gjayz.multimedia.ui.fragment.v4.BaseFragment;

import java.util.List;

import butterknife.BindView;

public class SchoolFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private SchoolAsnctask mSchoolAsnctask;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_school;
    }

    @Override
    public void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mSchoolAsnctask = new SchoolAsnctask();
        mSchoolAsnctask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    class SchoolAsnctask extends AsyncTask<Void, Void, List<School>> {

        @Override
        protected List<School> doInBackground(Void... voids) {
            return MusicManager.getInstance(mContext).getSchoolList();
        }

        @Override
        protected void onPostExecute(List<School> schools) {
            SchoolAdapter schoolAdapter = new SchoolAdapter(mContext, schools);
            mRecyclerView.setAdapter(schoolAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        if (mSchoolAsnctask != null) {
            if (!mSchoolAsnctask.isCancelled()) {
                mSchoolAsnctask.cancel(true);
            }
            mSchoolAsnctask = null;
        }
        super.onDestroyView();
    }
}
