package me.firdaus1453.footballmatchschedulekt.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.model.playermodel.PlayerItem
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class PlayerAdapter(private val teams: List<PlayerItem>, private val listener: (PlayerItem) -> Unit) : RecyclerView.Adapter<PlayerAdapter.TeamViewHolder>() {
    override fun onBindViewHolder(p0: PlayerAdapter.TeamViewHolder, p1: Int) = p0.bindItem(teams[p1], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(TeamUIPlayer().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_name_player: TextView = view.findViewById(R.id.tv_name_player)
        private val tv_position_player: TextView = view.findViewById(R.id.tv_position_player)
        private val img_player: ImageView = view.findViewById(R.id.img_player)

        fun bindItem(teams: PlayerItem, listener: (PlayerItem) -> Unit) {

            tv_name_player.text = teams.strPlayer
            tv_position_player.text = teams.strPosition

            Glide.with(itemView)
                .load(teams.strCutout)
                .into(img_player)

            itemView.setOnClickListener {
                listener(teams)
            }
        }

    }

}

class TeamUIPlayer : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {lparams(matchParent, wrapContent){
                setMargins(dip(3),dip(5),dip(3),dip(0))
            }
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                    }

                    imageView {
                        id = R.id.img_player
                    }.lparams(matchParent, wrapContent, 1f){
                        height = dip(60)
                        width = dip(60)
                    }

                    textView {
                        id = R.id.tv_name_player
                        textSize = 16f
                        gravity = Gravity.CENTER
                    }.lparams(wrapContent, wrapContent, 2f){
                        margin = dip(15)
                    }

                    textView {
                        id = R.id.tv_position_player
                        textSize = 16f
                        gravity = Gravity.RIGHT
                    }.lparams(wrapContent, wrapContent, 1f){
                        margin = dip(15)
                    }


                }
            }
        }
    }
}


