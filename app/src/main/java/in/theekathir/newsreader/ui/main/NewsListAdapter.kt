package `in`.theekathir.newsreader.ui.main

import `in`.theekathir.newsreader.R
import `in`.theekathir.newsreader.data.model.Articles
import `in`.theekathir.newsreader.databinding.LayoutExpandedNewsItemViewBinding
import `in`.theekathir.newsreader.databinding.LayoutSimpleNewsItemViewBinding
import `in`.theekathir.newsreader.utils.AppConstant
import `in`.theekathir.newsreader.utils.toPixel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.request.CachePolicy
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import kotlin.properties.Delegates

class NewsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var newsItems: List<Articles> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            2 -> {
                val holderPostBinding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_expanded_news_item_view,
                    parent,
                    false
                )
                return ExpandedNewsItemViewHolder(holderPostBinding)
            }
            else -> {
                val holderPostBinding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_simple_news_item_view,
                    parent,
                    false
                )
                return SimpleNewsItemViewHolder(holderPostBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (newsItems.isNullOrEmpty()) 0 else newsItems.size
    }

    private fun getItem(position: Int): Articles {
        return newsItems[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExpandedNewsItemViewHolder) {
            holder.onBind(getItem(position))
        } else {
            (holder as SimpleNewsItemViewHolder).onBind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return newsItems[position].uiType
    }

    inner class SimpleNewsItemViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun onBind(article: Articles) {
            (viewDataBinding as LayoutSimpleNewsItemViewBinding).article = article
            val imageUrl = AppConstant.MEDIUM_IMAGE_BASE_URL
                .plus("${article.imageRegion}/")
                .plus(article.imageLocation)

            viewDataBinding.ivNewsThumb.load(imageUrl) {
                transformations(RoundedCornersTransformation(4.toPixel(itemView.context)))
                diskCachePolicy(CachePolicy.ENABLED)
            }
        }
    }

    inner class ExpandedNewsItemViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun onBind(article: Articles) {
            (viewDataBinding as LayoutExpandedNewsItemViewBinding).article = article
            val imageUrl = AppConstant.MEDIUM_IMAGE_BASE_URL
                .plus("${article.imageRegion}/")
                .plus(article.imageLocation)

            viewDataBinding.ivNewsThumb.load(imageUrl) {
                scale(Scale.FILL)
                diskCachePolicy(CachePolicy.ENABLED)
            }
        }
    }
}