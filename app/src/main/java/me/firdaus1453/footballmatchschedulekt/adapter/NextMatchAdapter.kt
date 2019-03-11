package me.firdaus1453.footballmatchschedulekt.adapter

import android.content.Intent
import android.graphics.Typeface
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.Utils.DateTime
import me.firdaus1453.footballmatchschedulekt.Utils.dateTimeToFormat
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.concurrent.TimeUnit

class NextMatchAdapter(private val teams: List<EventsItem>, private val listener: (EventsItem) -> Unit) : RecyclerView.Adapter<NextMatchAdapter.TeamViewHolder>() {
    override fun onBindViewHolder(p0: NextMatchAdapter.TeamViewHolder, p1: Int) = p0.bindItem(teams[p1], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(TeamUINextMatch().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val matchDate: TextView = view.findViewById(R.id.tv_date)
        private val nameHomeTeam: TextView = view.findViewById(R.id.tv_home_team)
        private val nameAwayteam: TextView = view.findViewById(R.id.tv_away_team)
        private val scoreHomeTeam: TextView = view.findViewById(R.id.tv_home_score)
        private val scoreAwayTeam: TextView = view.findViewById(R.id.tv_away_score)
        private val btnNotify: ImageView = view.findViewById(R.id.btnNotify)

        fun bindItem(teams: EventsItem, listener: (EventsItem) -> Unit) {
            matchDate.text = "${DateTime.getLongDate(teams.dateEvent)} \n ${DateTime.timeFormat(teams.strTime)}"
            nameHomeTeam.text = teams.strHomeTeam
            nameAwayteam.text = teams.strAwayTeam
            Glide.with(itemView).load(R.drawable.ic_notify).into(btnNotify)

            if (teams.intHomeScore != null || teams.intAwayScore != null) {
                scoreHomeTeam.text = teams.intHomeScore.toString()
                scoreAwayTeam.text = teams.intAwayScore.toString()
            }

            btnNotify.onClick {
                val intent = Intent(Intent.ACTION_INSERT)
                intent.type = "vnd.android.cursor.item/event"

                val dateTime = teams.dateEvent + " " + teams.strTime
                val startTime = dateTime.dateTimeToFormat()
                val endTime = startTime + TimeUnit.MINUTES.toMillis(90)

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, TimeUnit.MINUTES.toMillis(90))

                intent.putExtra(
                    CalendarContract.Events.TITLE,
                    "${teams.strHomeTeam} vs ${teams.strAwayTeam}")

                intent.putExtra(
                    CalendarContract.Events.DESCRIPTION,
                    "Reminder ${teams.strHomeTeam} vs ${teams.strAwayTeam} from Football Match Schedule App")
                itemView.context.startActivity(intent)
            }

            itemView.setOnClickListener {
                listener(teams)
            }
        }

    }

}

class TeamUINextMatch : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {
                lparams(matchParent, wrapContent){
                setMargins(dip(3),dip(5),dip(3),dip(0))
            }

                linearLayout {
                    lparams(matchParent, wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    linearLayout {
                        lparams(matchParent, wrapContent,1f) {
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
                    imageView {
                        id = R.id.btnNotify
                    }.lparams(matchParent, wrapContent) {
                        gravity = Gravity.RIGHT
                        height = dip(30)
                        width = dip(30)
                        topMargin = dip(10)
                        rightMargin = dip(10)
                    }
                }
            }
        }
    }
}


