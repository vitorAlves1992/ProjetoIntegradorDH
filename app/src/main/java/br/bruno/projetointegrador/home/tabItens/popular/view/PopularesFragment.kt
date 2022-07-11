package br.bruno.projetointegrador.home.tabItens.popular.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.bruno.projetointegrador.R
import br.bruno.projetointegrador.home.MoviesFragmentDirections
import br.bruno.projetointegrador.home.tabItens.popular.viewModel.PopularMoviesViewModel
import br.bruno.projetointegrador.utils.Error
import br.bruno.projetointegrador.utils.Loading
import br.bruno.projetointegrador.utils.Success
import okhttp3.internal.notify


class PopularesFragment : Fragment(R.layout.fragments_populares) {


    private var page = 1
    private val viewModel: PopularMoviesViewModel by viewModels()
    private val totalPage by lazy {
        viewModel.totalPages
    }
    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter() {
            val direction = MoviesFragmentDirections.actionGlobalMovieDetailsFragment(it.id)
            findNavController().navigate(direction)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preperReycclerView(view)
        setupObserver()
        setupView()

    }

    private fun setupView() {
        viewModel.fetchMovieList(page)
    }

    private fun setupObserver() {
        viewModel.movieList.observe(viewLifecycleOwner) {

            when (it) {
                is Loading -> notify()
                is Success -> adapter.addData(it.data)
                is Error -> Toast.makeText(
                    requireContext(),
                    Error<String>().msg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun preperReycclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.MoviesRV).apply {
            this.adapter = this@PopularesFragment.adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {


                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val target = recyclerView.layoutManager as LinearLayoutManager?
                    val totalItemCount = target!!.itemCount
                    val lastVisible = target.findLastVisibleItemPosition()
                    val lastItem = lastVisible + 1 >= totalItemCount

                    if (totalItemCount > 0 && lastItem) {
                        page++
                        if (page <= totalPage)
                            viewModel.fetchMovieList(page)
                    }
                }

            })
        }

    }


}