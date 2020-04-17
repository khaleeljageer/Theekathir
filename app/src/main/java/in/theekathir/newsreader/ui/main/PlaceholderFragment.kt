package `in`.theekathir.newsreader.ui.main

import `in`.theekathir.newsreader.databinding.FragmentNewsTabBinding
import `in`.theekathir.newsreader.utils.AppConstant
import `in`.theekathir.newsreader.utils.EventBus
import `in`.theekathir.newsreader.utils.hasNetwork
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class PlaceholderFragment : Fragment(), CoroutineScope {

    private var tabIndex: Int = -1
    val titleArray = AppConstant.CATEGORIES
    private lateinit var mContext: Context
    private val articleViewModel: ArticleListViewModel by viewModel()
    private val newsAdapter by lazy {
        NewsListAdapter()
    }

    private lateinit var bindView: FragmentNewsTabBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabIndex = arguments?.getInt(ARG_SECTION_NUMBER) ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindView = FragmentNewsTabBinding.inflate(inflater, container, false)

        return bindView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(bindView.rvNewsListView) {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        if (tabIndex > -1) {
            if (mContext.hasNetwork()) {
                articleViewModel.loadPosts(titleArray[tabIndex])
            } else {
//                Snackbar.make(bindView.rootView, "No internet Connection", Snackbar.LENGTH_LONG).show()
                launch {
                    EventBus.send("No internet Connection")
                }
            }
        }
        observeChanges()
    }

    private fun observeChanges() {
        articleViewModel.articlesResponse.observe(this, Observer {
            it?.let {
                newsAdapter.newsItems = it.articles
            }
        })
        articleViewModel.messageData.observe(this, Observer {

        })

        articleViewModel.showProgressbar.observe(this, Observer { isVisible ->

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}