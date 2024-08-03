package com.example.newtoolsmon.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newtoolsmon.R;

import java.util.ArrayList;
import java.util.List;

public class MainDashBoardFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static MainDashBoardFragment newInstance() {
        return new MainDashBoardFragment();
    }
    RecyclerView mRecyclerView;
    private List<DashBoardMainArrayList> dabListItem;
    private ApdaterMainDashBoard mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.main_dashboard, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        final SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search App");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        dabListItem = new ArrayList<>();

        dabListItem = DashBoardMainArrayList.getData();

        mAdapter = new ApdaterMainDashBoard(getActivity(), dabListItem);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        final List<DashBoardMainArrayList> filteredModelList = mAdapter.filter(dabListItem, query);
        mAdapter.setItems(filteredModelList);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        final List<DashBoardMainArrayList> filteredModelList = mAdapter.filter(dabListItem, newText);
        mAdapter.setItems(filteredModelList);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);

        return true;
    }

}






