package me.firdaus1453.footballmatchschedulekt.mvp.detail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.R.id.menu_favorites
import me.firdaus1453.footballmatchschedulekt.Utils.DateTime
import me.firdaus1453.footballmatchschedulekt.Utils.invisible
import me.firdaus1453.footballmatchschedulekt.Utils.visible
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.snackbar

const val KEY_DETAIL = "KEY_DETAIL"

class
DetailActivity : AppCompatActivity(), DetailView {
    private lateinit var idEvent: String

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var presenter: DetailPresenter
    private var teams: MutableList<EventsItem> = mutableListOf()
    lateinit var progressBar: ProgressBar
    private lateinit var dataView: ScrollView
    private lateinit var tvHomeName: TextView
    private lateinit var tvAwayName: TextView
    private lateinit var tvHomeScore: TextView
    private lateinit var tvAwayScore: TextView
    private lateinit var date: TextView
    private lateinit var logoHome: ImageView
    private lateinit var logoAway: ImageView

    // Section 2
    private lateinit var tvHomeGoal: TextView
    private lateinit var tvAwayGoal: TextView
    private lateinit var tvHomeShot: TextView
    private lateinit var tvAwayShot: TextView

    //Section 3
    private lateinit var tvHomeGoalKeeper: TextView
    private lateinit var tvAwayGoalKeeper: TextView
    private lateinit var tvHomeDefense: TextView
    private lateinit var tvAwayDefense: TextView

    private lateinit var tvHomeMidfield: TextView
    private lateinit var tvAwayMidfield: TextView
    private lateinit var tvHomeForward: TextView
    private lateinit var tvAwayForward: TextView

    private lateinit var tvHomeSubstitute: TextView
    private lateinit var tvAwaySubstitute: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        idEvent = intent.getStringExtra(KEY_DETAIL)
        var request = ApiRepository()
        val gson = Gson()
        setupLayout()

        presenter = DetailPresenter(this, request, gson)
        presenter.getDetailMatch(idEvent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuFavorites = menu
//        isFavorite = presenter.addToFavorite(ctx, teams)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            menu_favorites -> {
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

    override fun showDetailTeam(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>) {
        Log.i("debug dataHome", dataHomeTeam.toString())
        Log.i("debug dataAway", dataAwayTeam.toString())

        Glide.with(this)
            .load(dataHomeTeam[0].strTeamBadge)
            .into(logoHome)

        Glide.with(this)
            .load(dataAwayTeam[0].strTeamBadge)
            .into(logoAway)
    }

    override fun showMatchDetail(data: List<EventsItem>) {
        teams.clear()
        teams.addAll(data)
        Log.i("debug idHome", teams[0].idHomeTeam.toString())
        Log.i("debug idAway", teams[0].idAwayTeam.toString())

        // Mengambil data detail team
        presenter.getTeamDetail(teams[0].idHomeTeam, teams[0].idAwayTeam)
        isFavorite = presenter.isFavorite(this, data)
        Log.i("debug favo", isFavorite.toString())
        setFavorite()
        showData()
    }

    private fun showData() {
        date.text = "${DateTime.getLongDate(teams[0].dateEvent)} \n ${DateTime.timeFormat(teams[0].strTime)}"
        tvHomeName.text = teams[0].strHomeTeam
        tvAwayName.text = teams[0].strAwayTeam
        if (teams[0].intHomeScore != null || teams[0].intAwayScore != null) {
            Log.i("case null", "Masuk tidak null")
            tvHomeScore.text = teams[0].intHomeScore.toString()
            tvAwayScore.text = teams[0].intAwayScore.toString()
            tvAwayGoal.text = teams[0].strAwayGoalDetails.toString()
            tvHomeGoal.text = teams[0].strHomeGoalDetails.toString()
            tvAwayShot.text = teams[0].intAwayShots.toString()
            tvHomeShot.text = teams[0].intHomeShots.toString()
            tvAwayGoalKeeper.text = teams[0].strAwayLineupGoalkeeper.toString()
            tvAwayDefense.text = teams[0].strAwayLineupDefense.toString()
            tvAwayMidfield.text = teams[0].strAwayLineupMidfield.toString()
            tvAwayForward.text = teams[0].strAwayLineupForward.toString()
            tvAwaySubstitute.text = teams[0].strAwayLineupSubstitutes.toString()

            tvHomeGoalKeeper.text = teams[0].strHomeLineupGoalkeeper.toString()
            tvHomeDefense.text = teams[0].strHomeLineupDefense.toString()
            tvHomeMidfield.text = teams[0].strHomeLineupMidfield.toString()
            tvHomeForward.text = teams[0].strHomeLineupForward.toString()
            tvHomeSubstitute.text = teams[0].strHomeLineupSubstitutes.toString()
        } else {
            Log.i("case null", "Masuk null")
            tvHomeScore.text = "0"
            tvAwayScore.text = "0"
        }
    }

    private fun setupLayout() {
        relativeLayout {
            dataView = scrollView {
                cardView {
                    lparams(matchParent, wrapContent)
                    progressBar = progressBar {
                    }
                    linearLayout {
                        lparams(matchParent, wrapContent) {
                            orientation = LinearLayout.VERTICAL
                        }
                        // Date
                        date = textView {
                            padding = dip(5)
                            text = "Selasa, 28-09-2018"
                            gravity = Gravity.CENTER
                        }

                        // Konten score
                        linearLayout {
                            lparams(matchParent, wrapContent)
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            // Konten home team
                            linearLayout {
                                lparams(matchParent, wrapContent, 1f)
                                orientation = LinearLayout.VERTICAL

                                logoHome = imageView {
                                    id = R.id.img_home_logo
                                    scaleType = ImageView.ScaleType.FIT_CENTER
                                }.lparams(dip(80), dip(80)) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }


                                tvHomeName = textView {
                                    id = R.id.tv_home_name
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    text = "Home Team Name"
                                }


                            }

                            // Konten Score vs Score
                            tvHomeScore = textView {
                                id = R.id.tv_detail_home_score
                                gravity = Gravity.CENTER_VERTICAL
                                padding = dip(10)
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = "VS"
                            }

                            tvAwayScore = textView {
                                id = R.id.tv_detail_away_score
                                gravity = Gravity.CENTER_VERTICAL
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                                padding = dip(10)
                            }

                            // Konten away team
                            linearLayout {
                                lparams(matchParent, wrapContent, 1f)
                                orientation = LinearLayout.VERTICAL

                                logoAway = imageView {

                                    id = R.id.img_away_logo
                                    scaleType = ImageView.ScaleType.FIT_CENTER
                                }.lparams(dip(80), dip(80)) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }

                                tvAwayName = textView {
                                    id = R.id.tv_away_name
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    text = "Away Team Name"
                                }
                            }
                        }

                        // Pembatas score
                        view {
                            backgroundColor = Color.LTGRAY
                        }.lparams(matchParent, dip(1)) {
                            margin = dip(10)
                        }

                        // Konten goal
                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            // Konten Score vs Score
                            tvHomeGoal = textView {
                                id = R.id.tv_home_goal
                                padding = dip(10)
                                text = "0"
                                gravity = Gravity.RIGHT
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                text = "Goals"
                            }

                            tvAwayGoal = textView {
                                id = R.id.tv_away_goal
                                text = "0"
                                padding = dip(10)
                                gravity = Gravity.LEFT
                            }.lparams(matchParent, wrapContent, 1f)
                        }.lparams(matchParent, wrapContent, 1f)

                        // Konten shot
                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            // Konten goal dan shot
                            tvHomeShot = textView {
                                id = R.id.tv_home_shot
                                padding = dip(10)
                                text = "0"
                                gravity = Gravity.RIGHT
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                text = "Shots"
                            }

                            tvAwayShot = textView {
                                id = R.id.tv_away_shot
                                text = "0"
                                padding = dip(10)
                                gravity = Gravity.LEFT
                            }.lparams(matchParent, wrapContent, 1f)
                        }.lparams(matchParent, wrapContent, 1f)

                        // Pembatas Lineup
                        view {
                            backgroundColor = Color.LTGRAY
                        }.lparams(matchParent, dip(1)) {
                            margin = dip(10)
                        }

                        // Lineup
                        textView {
                            padding = dip(5)
                            text = "Lineups"
                            setTypeface(null, Typeface.BOLD)
                            gravity = Gravity.CENTER
                        }

                        // Konten Goal Keeper
                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            tvHomeGoalKeeper = textView {
                                id = R.id.tv_home_goal_keeper
                                padding = dip(10)
                                text = " "
                                gravity = Gravity.RIGHT
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                text = "Goal Keeper"
                            }

                            tvAwayGoalKeeper = textView {
                                id = R.id.tv_away_goal_keeper
                                text = " "
                                padding = dip(10)
                                gravity = Gravity.LEFT
                            }.lparams(matchParent, wrapContent, 1f)
                        }.lparams(matchParent, wrapContent, 1f)

                        // Konten Defense
                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            tvHomeDefense = textView {
                                id = R.id.tv_home_defense
                                padding = dip(10)
                                text = " "
                                gravity = Gravity.RIGHT
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                text = "Defense"
                            }

                            tvAwayDefense = textView {
                                id = R.id.tv_away_defense
                                text = " "
                                padding = dip(10)
                                gravity = Gravity.LEFT
                            }.lparams(matchParent, wrapContent, 1f)
                        }.lparams(matchParent, wrapContent, 1f)

                        // Konten Midfield
                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            tvHomeMidfield = textView {
                                id = R.id.tv_home_midfield
                                padding = dip(10)
                                text = " "
                                gravity = Gravity.RIGHT
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                text = "Midfield"
                            }

                            tvAwayMidfield = textView {
                                id = R.id.tv_away_midfield
                                text = " "
                                padding = dip(10)
                                gravity = Gravity.LEFT
                            }.lparams(matchParent, wrapContent, 1f)
                        }.lparams(matchParent, wrapContent, 1f)

                        // Konten Forward
                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            tvHomeForward = textView {
                                id = R.id.tv_home_forward
                                padding = dip(10)
                                text = " "
                                gravity = Gravity.RIGHT
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                text = "Forward"
                            }

                            tvAwayForward = textView {
                                id = R.id.tv_away_forward
                                text = " "
                                padding = dip(10)
                                gravity = Gravity.LEFT
                            }.lparams(matchParent, wrapContent, 1f)
                        }.lparams(matchParent, wrapContent, 1f)

                        // Konten Substitutes
                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            // Konten Substitutes
                            tvHomeSubstitute = textView {
                                id = R.id.tv_home_substitutes
                                padding = dip(10)
                                text = " "
                                gravity = Gravity.RIGHT
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                gravity = Gravity.CENTER
                                text = "Substitutes"
                            }

                            tvAwaySubstitute = textView {
                                id = R.id.tv_away_substitutes
                                text = " "
                                padding = dip(10)
                                gravity = Gravity.LEFT
                            }.lparams(matchParent, wrapContent, 1f)
                        }.lparams(matchParent, wrapContent, 1f)

                    }
                }
            }.lparams(matchParent, matchParent) {
                margin = dip(10)
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }


    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_white)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_black)
        }
    }
}

