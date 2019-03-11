package me.firdaus1453.footballmatchschedulekt.mvp.match.next


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.Utils.invisible
import me.firdaus1453.footballmatchschedulekt.Utils.visible
import me.firdaus1453.footballmatchschedulekt.adapter.FavoriteAdapter
import me.firdaus1453.footballmatchschedulekt.adapter.NextMatchAdapter
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteModel
import me.firdaus1453.footballmatchschedulekt.model.leaguemodel.LeagueResponse
import me.firdaus1453.footballmatchschedulekt.model.leaguemodel.LeaguesItem
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.mvp.detail.DetailActivity
import me.firdaus1453.footballmatchschedulekt.mvp.detail.KEY_DETAIL
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchPresenter
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchView
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class NextMatchFragment : Fragment(), MatchView {
    private lateinit var league: LeaguesItem

    companion object {
        private lateinit var spinner: Spinner
        private lateinit var spinnerLayout: LinearLayout
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout
        lateinit var listTeam: RecyclerView
        lateinit var adapter: NextMatchAdapter
        lateinit var adapterFavorite: FavoriteAdapter
        lateinit var presenter: MatchPresenter
        var teams: MutableList<EventsItem> = mutableListOf()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var request = ApiRepository()
        val gson = Gson()
        presenter =
                MatchPresenter(this, request, gson)

        adapter = NextMatchAdapter(teams) {
            startActivity<DetailActivity>(KEY_DETAIL to it.idEvent)
        }

//        presenter.getNextMatchList(idleague)
        presenter.getLeague()

        listTeam.adapter =
                adapter

        swipeRefresh.onRefresh {
            presenter.getNextMatchList(
                league.idLeague
            )
        }

    }

    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.countrys)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesItem

                presenter.getNextMatchList(league.idLeague)
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showFavoriteList(data: List<FavoriteModel>) {
        swipeRefresh.isRefreshing = false
        Log.i("refresh", "Masuk showFavorite")
        adapterFavorite.notifyDataSetChanged()
    }


    override fun showPastMatchList(data: List<EventsItem>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNextMatchList(data: List<EventsItem>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentUi<Fragment>().createView(
            AnkoContext.create(ctx, this)
        )

    class FragmentUi<T> : AnkoComponent<T> {
        override fun createView(ui: AnkoContext<T>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                spinnerLayout = linearLayout {
                    orientation = LinearLayout.VERTICAL
                    spinner = spinner {
                        id = R.id.spinner
                        padding = dip(2)
                    }
                }

                swipeRefresh = swipeRefreshLayout {
                    id = R.id.swipe_up
                    setColorSchemeResources(
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light, android.R.color.holo_red_light
                    )

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)


                        listTeam = recyclerView {
                            id = R.id.rv_match
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(context)
                        }

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }
    }


}

