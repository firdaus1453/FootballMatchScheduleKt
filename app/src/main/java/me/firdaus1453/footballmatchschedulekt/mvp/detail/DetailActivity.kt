package me.firdaus1453.footballmatchschedulekt.mvp.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.ScrollView
import com.google.gson.Gson
import me.firdaus1453.footballclubmodul6.adapter.MainAdapter
import me.firdaus1453.footballclubmodul6.invisible
import me.firdaus1453.footballclubmodul6.network.ApiRepository
import me.firdaus1453.footballclubmodul6.visible
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchFragment

const val KEY_DETAIL = "KEY_DETAIL"

class DetailActivity : AppCompatActivity(), DetailView {
    override fun showDetailTeam(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>) {
//       dataHomeTeam.get(0).strTeamBadge
//        dataAwayTeam.get(0).strTeamBadge
    }

    private lateinit var idEvent: String

    private lateinit var presenter: DetailPresenter
    var teams: MutableList<EventsItem> = mutableListOf()
    private lateinit var adapter: MainAdapter
    lateinit var progressBar: ProgressBar
    private lateinit var dataView: ScrollView

    override fun showMatchDetail(data: List<EventsItem>) {
        MatchFragment.swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        setupLayout(teams)
        presenter.getTeamDetail(teams.get(0).idHomeTeam, teams.get(0).idAwayTeam)
    }

    private fun setupLayout(teams: MutableList<EventsItem>) {
//        relativeLayout {
//            dataView = scrollView {
//                linearLayout {
//                    orientation = LinearLayout.VERTICAL
//                    padding = dip(16)
//
//                    textView(DateTime.getLongDate(teams.get(0).dateEvent))
//
//                    linearLayout {
//                        gravity = Gravity.CENTER
//
//                        textTitle(data.intHomeScore)
//                        textTitle("vs")
//                        textTitle(data.intAwayScore)
//                    }
//
//                    // team
//                    linearLayout {
//                        layoutTeamBadge(R.id.home_badge, data.strHomeTeam, data.strHomeFormation)
//                            .lparams(matchParent, wrapContent, 1f)
//
//                        layoutTeamBadge(R.id.away_badge, data.strAwayTeam, data.strAwayFormation)
//                            .lparams(matchParent, wrapContent, 1f)
//                    }
//
//                    line()
//
//                    layoutDetailItem("Goals", data.strHomeGoalDetails, data.strAwayGoalDetails)
//                    layoutDetailItem("Shots", data.intHomeShots, data.intAwayShots)
//
//                    line()
//
//                    // lineups
//                    textSubTitle("Lineups")
//
//                    layoutDetailItem("Goal Keeper", data.strHomeLineupGoalkeeper, data.strAwayLineupGoalkeeper)
//                    layoutDetailItem("Defense", data.strHomeLineupDefense, data.strAwayLineupDefense)
//                    layoutDetailItem("Midfield", data.strHomeLineupMidfield, data.strAwayLineupMidfield)
//                    layoutDetailItem("Forward", data.strHomeLineupForward, data.strAwayLineupForward)
//                    layoutDetailItem("Substitutes", data.strHomeLineupSubstitutes, data.strAwayLineupSubstitutes)
//                }
//            }
//
//            progressBar(R.id.progress_bar).lparams {
//                centerInParent()
//            }
//        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
       progressBar.invisible()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idEvent = intent.getStringExtra(KEY_DETAIL)
        var request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        presenter.getDetailMatch(idEvent)
    }
}
