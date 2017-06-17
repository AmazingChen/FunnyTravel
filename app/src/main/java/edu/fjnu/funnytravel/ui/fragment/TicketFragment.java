package edu.sqchen.iubao.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.TicketListAdapter;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.model.entity.Ticket;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketFragment extends Fragment {


    @BindView(R.id.ticket_list_view)
    ListView mTicketListView;

    private TicketListAdapter mTicketListAdapter;

    private List<Ticket.ResultBean> mTicketList;

    Unbinder unbinder;

    private Intent mIntent;

    public TicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        getTicketData();

        return view;
    }

    private void init() {
        mIntent = getActivity().getIntent();
        mTicketList = new ArrayList<>();
    }

    private void getTicketData() {
        AttractionService attractionService = NetManager.getInstance().create(AttractionService.class);
        RxManager.getInstance()
                .doUnifySubscribe(
                        attractionService.getTicketInfo(
                                mIntent.getStringExtra("selectedSId"),
                                "bc9350399df04805b88acb49a07e45e2")
                        , new RxSubscriber<Ticket>() {
                            @Override
                            protected void _onError(Throwable e) {

                            }

                            @Override
                            protected void _onNext(Ticket ticket) {
                                mTicketList = ticket.getResult();
                                mTicketListAdapter = new TicketListAdapter(getContext(),mTicketList);
                                mTicketListView.setAdapter(mTicketListAdapter);
                                Log.d("ticket","get ticket data finished");
                            }
                        });
    }

    public ListView getListView() {
        return this.mTicketListView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
