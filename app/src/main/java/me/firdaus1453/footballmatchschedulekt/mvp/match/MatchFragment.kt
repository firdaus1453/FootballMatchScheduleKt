package me.firdaus1453.footballmatchschedulekt.mvp.match


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import me.firdaus1453.footballclubmodul6.adapter.MainAdapter
import me.firdaus1453.footballclubmodul6.invisible
import me.firdaus1453.footballclubmodul6.network.ApiRepository
import me.firdaus1453.footballclubmodul6.visible
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.mvp.detail.DetailActivity
import me.firdaus1453.footballmatchschedulekt.mvp.detail.KEY_DETAIL
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MatchFragment : Fragment(), MatchView {
    companion object {
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout
        lateinit var listTeam: RecyclerView
        lateinit var adapter: MainAdapter
        lateinit var presenter: MatchPresenter
        var idleague: String = "4328"
        var teams: MutableList<EventsItem> = mutableListOf()
        val KEY_MENU: String = "KEY_MENU"
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
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

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light, android.R.color.holo_red_light
                    )

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MainAdapter(teams) {
            startActivity<DetailActivity>(KEY_DETAIL to it.idEvent)
        }

        listTeam.adapter =
                adapter

        teams.clear()
        var request = ApiRepository()
        val gson = Gson()
        presenter =
                MatchPresenter(this, request, gson)
        var isiMenu = arguments!!.getInt(KEY_MENU)
        swipeRefresh.onRefresh {
            when (isiMenu) {
                1 -> {
                    presenter.getNextMatchList(
                        idleague
                    )
                }
                2 -> {
                    presenter.getPastMatchList(
                        idleague
                    )
                }
                else -> {
                    presenter.getPastMatchList(
                        idleague
                    )
                }
            }
        }
        when (isiMenu) {
            1 -> {
                presenter.getNextMatchList(idleague)
            }
            2 -> {
                presenter.getPastMatchList(idleague)
            }
            else -> {
                presenter.getPastMatchList(idleague)
            }
        }
    }
}

