package me.firdaus1453.footballmatchschedulekt.mvp.team.detailteam

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.fragment_team_overview.*
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.Utils.invisible
import me.firdaus1453.footballmatchschedulekt.Utils.visible
import me.firdaus1453.footballmatchschedulekt.adapter.TeamPagerAdapter
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find

const val KEY_DETAIL_TEAM = "KEY_DETAIL"

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailTeam(dataTeam: List<TeamsItem>) {
        teams.clear()
        teams.addAll(dataTeam)

        isFavorite = presenter.isFavorite(this, dataTeam)
        setFavorite()
        Glide.with(this).load(teams[0].strTeamBadge).into(img_team_badge_detail)

        tv_team_name_detail.text = teams[0].strTeam
        tv_team_formed_year.text = teams[0].intFormedYear
        tv_team_stadium.text = teams[0].strStadium
        tv_team_overview.text = teams[0].strDescriptionEN

    }

    companion object {
        lateinit var idTeam: String
    }

    private var isFavorite: Boolean = false
    private var menuFavorites: Menu? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: DetailTeamPresenter
    private var teams: MutableList<TeamsItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressBar = find(R.id.progressbar_team_detail)

        if ((intent.getStringExtra(KEY_DETAIL_TEAM)) != null) {
            idTeam = intent.getStringExtra(KEY_DETAIL_TEAM)
        }
        var request = ApiRepository()
        val gson = Gson()

        presenter = DetailTeamPresenter(this, request, gson)
        presenter.getTeamDetail(idTeam)

        val fragmentAdapter = TeamPagerAdapter(supportFragmentManager)
        viewpager_team.adapter = fragmentAdapter
        tab_team.setupWithViewPager(viewpager_team)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuFavorites = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.menu_favorites -> {
                if (isFavorite) {
                    presenter.removeFavorit(this, teams)
                    progressBar.snackbar("Terhapus")
                } else {
                    presenter.addToFavorite(this, teams)
                    progressBar.snackbar("Tersimpan")

                }

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_white)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_black)
        }
    }
}
