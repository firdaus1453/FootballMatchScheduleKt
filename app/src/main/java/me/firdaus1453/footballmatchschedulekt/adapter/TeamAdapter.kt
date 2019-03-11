package me.firdaus1453.footballmatchschedulekt.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class TeamAdapter(private val teams: List<TeamsItem>, private val listener: (TeamsItem) -> Unit) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {
    override fun onBindViewHolder(p0: TeamAdapter.TeamViewHolder, p1: Int) = p0.bindItem(teams[p1], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(TeamUITeam().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_name_team: TextView = view.findViewById(R.id.tv_name_team)
        private val img_team: ImageView = view.findViewById(R.id.img_team)

        fun bindItem(teams: TeamsItem, listener: (TeamsItem) -> Unit) {

            tv_name_team.text = teams.strTeam
            Glide.with(itemView)
                .load(teams.strTeamBadge)
                .into(img_team)

            itemView.setOnClickListener {
                listener(teams)
            }
        }

    }

}

class TeamUITeam : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {lparams(matchParent, wrapContent){
                setMargins(dip(3),dip(5),dip(3),dip(0))
            }

                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    imageView {
                        id = R.id.img_team
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }.lparams(dip(80), dip(80)){
                        margin = dip(10)
                    }

                    textView {
                        id = R.id.tv_name_team
                        setTypeface(null, Typeface.BOLD)
                        textSize = 24f
                        gravity = Gravity.CENTER
                        text = "Team Name"
                    }.lparams{
                        margin = dip(15)
                    }

                }
            }
        }
    }
}


