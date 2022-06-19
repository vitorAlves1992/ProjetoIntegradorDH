package br.bruno.projetointegrador.movieDetails.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.bruno.projetointegrador.R
import br.bruno.projetointegrador.favorites.data.FavMovieVo
import br.bruno.projetointegrador.movieDetails.viewModel.MovieDetailsViewModel
import br.bruno.projetointegrador.movieDetails.vo.MoviesDetailsVo
import br.bruno.projetointegrador.utils.*
import kotlin.Error


class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var synopsis: TextView
    private lateinit var voteAverage: TextView
    private lateinit var realeseDate: TextView
    private lateinit var addToFavBtn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        poster = view.findViewById(R.id.header_detailsImage)
        title = view.findViewById(R.id.tittle_Details)
        synopsis = view.findViewById(R.id.movie_synopsis)
        voteAverage = view.findViewById(R.id.voteAverage)
        realeseDate = view.findViewById(R.id.releaseDate)
        addToFavBtn = view.findViewById(R.id.Add_to_Fav_btn)


        setupClickers()
        fetchMovieById(args.id)
        setupObserver()
    }

    private fun setupClickers() {
        addToFavBtn.setOnClickListener {
            viewModel.addToFav(args.id)
        }
    }

    private fun fetchMovieById(id: Int) {
        viewModel.fecthMovieById(id)
    }

    private fun setupObserver() {
        viewModel.movieDatail.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is Success -> setupView(movie.data)
                is Error -> Toast.makeText(
                    requireContext(),
                    Error<String>().msg,
                    Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(
                    requireContext(),
                    "Algo alem do erro ocorreu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.favMovie.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is Success -> sendToFavList(movie.data)
                else -> Toast.makeText(
                    requireContext(),
                    "Algo alem do erro ocorreu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun sendToFavList(movie: FavMovieVo) {
        val directions =
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentToFavoritosFragment(
                movie.tittle,
                movie.poster_path,
                movie.id.toInt()
            )
        findNavController().navigate(directions)
    }

    private fun setupView(movie: MoviesDetailsVo) {
        title.text = movie.tittle
        synopsis.text = movie.movie_synopsis
        voteAverage.text = "votos: " + movie.vote_average.toString()
        realeseDate.text = "Lançamento: " + movie.release_date
        MyGlide().build(
            requireContext(),
            IMAGE_URL,
            movie.backdrop_path,
            poster,
            poster.width,
            poster.height
        )
    }

}