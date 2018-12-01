package me.firdaus1453.footballclubmodul6.adapter

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import me.firdaus1453.footballclubmodul6.DateTime
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteModel
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class FavoriteAdapter(private val teams: List<FavoriteModel>, private val listener: (FavoriteModel) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.TeamViewHolder>() {
    override fun onBindViewHolder(p0: FavoriteAdapter.TeamViewHolder, p1: Int) = p0.bindItem(teams[p1], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(FavoriteUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val matchDate: TextView = view.findViewById(R.id.tv_date)
        private val nameHomeTeam: TextView = view.findViewById(R.id.tv_home_team)
        private val nameAwayteam: TextView = view.findViewById(R.id.tv_away_team)
        private val scoreHomeTeam: TextView = view.findViewById(R.id.tv_home_score)
        private val scoreAwayTeam: TextView = view.findViewById(R.id.tv_away_score)

        fun bindItem(teams: FavoriteModel, listener: (FavoriteModel) -> Unit) {
            matchDate.text = DateTime.getLongDate(teams.eventDate)
            nameHomeTeam.text = teams.teamHome
            nameAwayteam.text = teams.teamAway

            if (teams.scoreHome != null || teams.scoreAway!= null) {
                scoreHomeTeam.text = teams.scoreHome.toString()
                scoreAwayTeam.text = teams.scoreAway.toString()
            }
            itemView.setOnClickListener {
                listener(teams)
            }
        }

    }

}

class FavoriteUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {lparams(matchParent, wrapContent){
                setMargins(dip(3),dip(5),dip(3),dip(0))
            }

                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.VERTICAL
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        textView {
                            setTypeface(null, Typeface.BOLD)
                            id = R.id.tv_date
                            gravity = Gravity.CENTER
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                id = R.id.tv_home_team
                                gravity = Gravity.CENTER
                                text = "home"
                            }.lparams(matchParent, wrapContent, 1f)

                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                textView {
                                    id = R.id.tv_home_score
                                    padding = dip(10)
                                    setTypeface(null, Typeface.BOLD)
                                    text = "0"
                                }

                                textView {
                                    text = "vs"
                                }

                                textView {
                                    id = R.id.tv_away_score
                                    padding = dip(10)
                                    setTypeface(null, Typeface.BOLD)
                                    text = "0"
                                }
                            }

                            textView {
                                id = R.id.tv_away_team
                                gravity = Gravity.CENTER
                                text = "away"
                            }.lparams(matchParent, wrapContent, 1f)
                        }
                    }.lparams(matchParent, matchParent) {
                        margin = dip(15)
                    }

                }
            }
        }
    }
}


