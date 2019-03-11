package me.firdaus1453.footballmatchschedulekt.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import kotlinx.android.synthetic.main.fragment_main_match.*
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.adapter.MatchPagerAdapter
import me.firdaus1453.footballmatchschedulekt.mvp.match.search.SearchMatchActivity
import org.jetbrains.anko.startActivity

class MainMatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_main_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentAdapter = MatchPagerAdapter(childFragmentManager)
        viewpager_match.adapter = fragmentAdapter

        tabs_match.setupWithViewPager(viewpager_match)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                true
            }
            R.id.btn_search -> {
                context?.startActivity<SearchMatchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.btn_search, menu)
    }


}
