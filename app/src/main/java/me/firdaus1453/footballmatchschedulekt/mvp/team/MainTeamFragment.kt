package me.firdaus1453.footballmatchschedulekt.mvp.team


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.Utils.invisible
import me.firdaus1453.footballmatchschedulekt.Utils.visible
import me.firdaus1453.footballmatchschedulekt.adapter.TeamAdapter
import me.firdaus1453.footballmatchschedulekt.model.leaguemodel.LeagueResponse
import me.firdaus1453.footballmatchschedulekt.model.leaguemodel.LeaguesItem
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem
import me.firdaus1453.footballmatchschedulekt.mvp.match.search.SearchMatchActivity
import me.firdaus1453.footballmatchschedulekt.mvp.team.detailteam.DetailTeamActivity
import me.firdaus1453.footballmatchschedulekt.mvp.team.detailteam.KEY_DETAIL_TEAM
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainTeamFragment : Fragment(), TeamsView {
    override fun showSearchTeam(data: List<TeamsItem>) {
        teams.clear()
        teams.addAll(data)
        listTeam.adapter?.notifyDataSetChanged()
    }

    private lateinit var league: LeaguesItem

    companion object {
        private lateinit var spinner: Spinner
        private lateinit var spinnerLayout: LinearLayout
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout
        lateinit var listTeam: RecyclerView
        lateinit var adapter: TeamAdapter
        lateinit var presenter: TeamsPresenter
        var teams: MutableList<TeamsItem> = mutableListOf()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        var request = ApiRepository()
        val gson = Gson()
        presenter =
                TeamsPresenter(this, request, gson)

        adapter = TeamAdapter(teams) {
            startActivity<DetailTeamActivity>(KEY_DETAIL_TEAM to it.idTeam)
        }

        presenter.getLeague()

        listTeam.adapter =
                adapter

        swipeRefresh.onRefresh {
            presenter.getAllTeam(
                league.idLeague
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as android.support.v7.widget.SearchView

        searchView.queryHint = "Search Team..."

        searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                SearchMatchActivity.keyword = p0.toString()
                presenter.getSearchTeam(SearchMatchActivity.keyword)
                return false
            }
        })

    }

    override fun showAllTeam(data: List<TeamsItem>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLeagueList(data: LeagueResponse?) {
        spinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, data?.countrys)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesItem

                presenter.getAllTeam(league.idLeague!!)
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
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

