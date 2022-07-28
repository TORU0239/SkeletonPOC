package sg.toru.skeleton.product.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.github.toru0239.skeletoncore.product.Product
import sg.toru.skeleton.product.R
import sg.toru.skeleton.product.databinding.ItemProductBinding

class ProductAdapter(private val clickCallback:((Product)->Unit)? = null): ListAdapter<Product, ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            viewBinding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickCallback = clickCallback
        )
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}
class ProductDiffCallback :DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ) = (oldItem.id == newItem.id)
}

class ProductViewHolder(
    private val viewBinding: ItemProductBinding,
    private val clickCallback: ((Product) -> Unit)? = null
): RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(product: Product) {
        viewBinding.run {
            txtProductTitle.text = product.title
            txtProductPrice.text = String.format(root.context.getString(R.string.product_price), product.price)
            txtProductRating.text = String.format(root.context.getString(R.string.product_rating), product.rating)
            imgProductThumbnail.load(product.thumbnail) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
            }
            root.setOnClickListener {
                clickCallback?.invoke(product)
            }
        }
    }
}
