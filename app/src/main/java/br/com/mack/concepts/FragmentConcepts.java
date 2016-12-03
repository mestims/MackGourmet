package br.com.mack.concepts;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.mack.App;
import br.com.mack.R;
import br.com.mack.databinding.FragmentConceptsBinding;
import br.com.mack.valueobjects.Concept;

/**
 * Created by Amor on 02/12/2016.
 */

public class FragmentConcepts extends Fragment implements ConceptContract.View {
    ArrayAdapter<String> adapter;
    FragmentConceptsBinding binding;

    @Inject
    ConceptPresenter conceptPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerConceptComponent.builder()
                .netComponent(((App)getActivity().getApplicationContext()).getmNetComponent())
                .conceptModule(new ConceptModule(this))
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConceptsBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        conceptPresenter.loadConcepts();
    }

    @Override
    public void showConcepts(List<Concept> concepts) {
        List<String> teste = new ArrayList<>();
        for (Concept item: concepts) {
            teste.add(item.getName());
        }
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, teste);
        binding.conceptsList.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete() {
        Toast.makeText(getActivity(), "Complete", Toast.LENGTH_SHORT).show();
    }
}
