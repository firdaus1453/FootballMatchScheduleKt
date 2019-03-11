package me.firdaus1453.footballmatchschedulekt.mvp.match.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_match.*
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.Utils.invisible
import me.firdaus1453.footballmatchschedulekt.Utils.visible
import me.firdaus1453.footballmatchschedulekt.adapter.SearchAdapter
import me.firdaus1453.footballmatchschedulekt.model.searchmodel.EventItem
import me.firdaus1453.footballmatchschedulekt.mvp.detail.DetailActivity
import me.firdaus1453.footballmatchschedulekt.mvp.detail.KEY_DETAIL
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import org.jetbrains.anko.startActivity

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private lateinit var presenter: SearchPresenter
    var teams: MutableList<EventItem> = mutableListOf()
    lateinit var adapter: SearchAdapter

    companion object {
        lateinit var keyword: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        setTitle("Search")

        var request = ApiRepository()
        val gson = Gson()
        presenter =
                SearchPresenter(this, request, gson)

        rv_match_search.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(teams) {
            startActivity<DetailActivity>(KEY_DETAIL to it.idEvent)
        }

        rv_match_search.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.search)
        searchItem?.expandActionView()
        val searchView = searchItem?.actionView as android.support.v7.widget.SearchView

        searchView.queryHint = "Search Match..."


        searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                keyword = p0.toString()
                presenter.getSearchMatch(keyword)
                return false
            }
        })

        return true
    }


    override fun showSearchMatch(data: List<EventItem>) {
//                adapter = SearchAdapter(data){
//                    startActivity<DetailActivity>(KEY_DETAIL to it.idEvent)
//                }

            teams.clear()
            teams.addAll(data)
            rv_match_search.adapter?.notifyDataSetChanged()

    }

    override fun showLoading() {
        progressbar_search_match.visible()
    }

    override fun hideLoading() {
        progressbar_search_match.invisible()

    }
}
