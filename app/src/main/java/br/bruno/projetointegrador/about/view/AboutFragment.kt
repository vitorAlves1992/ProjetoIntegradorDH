package br.bruno.projetointegrador.about.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.bruno.projetointegrador.R


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        return inflater.inflate(R.layout.about_fragments, container, false)
    }

}