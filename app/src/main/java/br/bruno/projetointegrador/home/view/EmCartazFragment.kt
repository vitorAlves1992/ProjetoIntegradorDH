package br.bruno.projetointegrador.home.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.bruno.projetointegrador.R
import br.bruno.projetointegrador.home.emCartaz.view.adapter.EmCartazMoviesAdapter
import br.bruno.projetointegrador.home.viewModels.EmCartazMoviesViewModel
import br.bruno.projetointegrador.home.viewObjets.EmCartazMoviesVO
import br.bruno.projetointegrador.utils.Error
import br.bruno.projetointegrador.utils.Loading
import br.bruno.projetointegrador.utils.Success
import okhttp3.internal.notify


class EmCartazFragment : Fragment(R.layout.fragments_em_cartaz) {

    private val viewModel: EmCartazMoviesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver(view)
        setupView()

    }

    private fun setupView() {
        viewModel.fetchMovieList()
    }

    private fun setupObserver(view: View) {
        viewModel.movieList.observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> TODO()
                is Success -> {
                    preperReycclerView(it.data, view)
                }
                is Error -> {
                    Toast.makeText(requireContext(), Error<String>().msg , Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun preperReycclerView(data: List<EmCartazMoviesVO>, view: View) {
        view.findViewById<RecyclerView>(R.id.MoviesRV).adapter = EmCartazMoviesAdapter(requireContext(),data){
            val direction = MoviesFragmentDirections.actionGlobalMovieDetailsFragment(it.id)
            findNavController().navigate(direction)
        }

    }

}