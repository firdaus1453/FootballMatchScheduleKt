package me.firdaus1453.footballmatchschedulekt.mvp.team.teamplayer


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.Utils.invisible
import me.firdaus1453.footballmatchschedulekt.Utils.visible
import me.firdaus1453.footballmatchschedulekt.adapter.PlayerAdapter
import me.firdaus1453.footballmatchschedulekt.model.playermodel.PlayerItem
import me.firdaus1453.footballmatchschedulekt.mvp.team.detailplayer.DetailPlayerActivity
import me.firdaus1453.footballmatchschedulekt.mvp.team.detailplayer.KEY_DETAIL_PLAYER
import me.firdaus1453.footballmatchschedulekt.mvp.team.detailteam.KEY_DETAIL_TEAM
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class PlayerFragment : Fragment(), PlayerView {

    private lateinit var idTeam: String

    companion object {
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout
        lateinit var listTeam: RecyclerView
        lateinit var adapter: PlayerAdapter
        lateinit var presenter: PlayerPresenter
        var teams: MutableList<PlayerItem> = mutableListOf()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var request = ApiRepository()
        val gson = Gson()
        idTeam = activity?.intent?.getStringExtra(KEY_DETAIL_TEAM).toString()

        presenter =
                PlayerPresenter(this, request, gson)

        adapter = PlayerAdapter(teams) {
            startActivity<DetailPlayerActivity>(KEY_DETAIL_PLAYER to it.idPlayer)
            activity?.finish()
        }

        presenter.getAllPlayer(idTeam)

        listTeam.adapter =
                adapter

        swipeRefresh.onRefresh {
            presenter.getAllPlayer(
                idTeam
            )
        }

    }

    override fun showAllPlayer(data: List<PlayerItem>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        listTeam.adapter?.notifyDataSetChanged()
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
                orientation = LinearLayout.HORIZONTAL

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> {
                activity?.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}

