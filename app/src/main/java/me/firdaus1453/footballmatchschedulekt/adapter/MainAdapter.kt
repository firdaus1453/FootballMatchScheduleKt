package me.firdaus1453.footballmatchschedulekt.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.Utils.DateTime
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class MainAdapter(private val teams: List<EventsItem>, private val listener: (EventsItem) -> Unit) : RecyclerView.Adapter<MainAdapter.TeamViewHolder>() {
    override fun onBindViewHolder(p0: MainAdapter.TeamViewHolder, p1: Int) = p0.bindItem(teams[p1], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val matchDate: TextView = view.findViewById(R.id.tv_date)
        private val nameHomeTeam: TextView = view.findViewById(R.id.tv_home_team)
        private val nameAwayteam: TextView = view.findViewById(R.id.tv_away_team)
        private val scoreHomeTeam: TextView = view.findViewById(R.id.tv_home_score)
        private val scoreAwayTeam: TextView = view.findViewById(R.id.tv_away_score)

        fun bindItem(teams: EventsItem, listener: (EventsItem) -> Unit) {
            matchDate.text = "${DateTime.getLongDate(teams.dateEvent)} \n ${DateTime.timeFormat(teams.strTime)}"
            nameHomeTeam.text = teams.strHomeTeam
            nameAwayteam.text = teams.strAwayTeam

            if (teams.intHomeScore != null || teams.intAwayScore != null) {
                scoreHomeTeam.text = teams.intHomeScore.toString()
                scoreAwayTeam.text = teams.intAwayScore.toString()
            }
            itemView.setOnClickListener {
                listener(teams)
            }
        }

    }

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {
                lparams(matchParent, wrapContent){
                setMargins(dip(3),dip(5),dip(3),dip(0))
            }

                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.VERTICAL
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        textView {
                            id = R.id.tv_date
                            setTypeface(null, Typeface.BOLD)
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


