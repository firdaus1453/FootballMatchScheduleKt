package me.firdaus1453.footballmatchschedulekt.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteTeam
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class FavoriteTeamAdapter(private val teams: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit) : RecyclerView.Adapter<FavoriteTeamAdapter.TeamViewHolder>() {
    override fun onBindViewHolder(p0: FavoriteTeamAdapter.TeamViewHolder, p1: Int) = p0.bindItem(teams[p1], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(TeamUIFavoriteTeam().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNameTeam: TextView = view.findViewById(R.id.tv_name_team)
        private val imgTeam: ImageView = view.findViewById(R.id.img_team)

        fun bindItem(teams: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {

            tvNameTeam.text = teams.teamName
            Glide.with(itemView)
                .load(teams.teamBadge)
                .into(imgTeam)

            itemView.setOnClickListener {
                listener(teams)
            }
        }

    }

}

class TeamUIFavoriteTeam : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {lparams(matchParent, wrapContent){
                setMargins(dip(3),dip(5),dip(3),dip(0))
            }

                linearLayout {
                    lparams(matchParent, wrapContent)
                    orientation = LinearLayout.HORIZONTAL

                    imageView {
                        id = R.id.img_team
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }.lparams{
                        height = dip(80)
                        width = dip(80)
                        margin = dip(15)
                    }

                    textView {
                        id = R.id.tv_name_team
                        setTypeface(null, Typeface.BOLD)
                        textSize = 24f
                        text = "Team Name"
                    }.lparams{
                        margin = dip(15)
                    }

                }
            }
        }
    }
}


