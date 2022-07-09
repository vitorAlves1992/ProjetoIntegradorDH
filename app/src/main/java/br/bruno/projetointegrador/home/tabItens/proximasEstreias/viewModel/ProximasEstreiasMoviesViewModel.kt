package br.bruno.projetointegrador.home.tabItens.proximasEstreias.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.bruno.projetointegrador.home.tabItens.proximasEstreias.data.ProximasEstreiasRepository
import br.bruno.projetointegrador.home.tabItens.proximasEstreias.vo.ProximasEstreiasMoviesVO
import br.bruno.projetointegrador.utils.Error
import br.bruno.projetointegrador.utils.Result
import br.bruno.projetointegrador.utils.Success
import kotlinx.coroutines.launch
import retrofit2.HttpException


class ProximasEstreiasMoviesViewModel : ViewModel() {
private val repository: ProximasEstreiasRepository = ProximasEstreiasRepository()

private val _movieList: MutableLiveData<Result<List<ProximasEstreiasMoviesVO>>> = MutableLiveData()
val movieList: LiveData<Result<List<ProximasEstreiasMoviesVO>>> = _movieList

fun fetchMovieList() {
    viewModelScope.launch {
        try {
            val response = repository.fetchMovieList()
            val vo = response.moviesList.map {
                ProximasEstreiasMoviesVO(
                    title = it.title,
                    vote_average = it.vote_average,
                    overview = it.overview,
                    poster_path = it.poster_path,
                    id = it.id
                )
            }
            _movieList.value = Success(vo)
        } catch (ex: HttpException) {
            _movieList.value = Error()
        }
    }
}
}
