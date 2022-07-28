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

class ProductAdapter(): ListAdapter<Product, ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

class ProductViewHolder(private val viewBinding: ItemProductBinding): RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(product: Product) {
        viewBinding.txtProductTitle.text = product.title
        viewBinding.txtProductPrice.text = "${product.price} dollar"
        viewBinding.txtProductRating.text = "Rate: ${product.rating}"
        viewBinding.imgProductThumbnail.load(product.thumbnail) {
            crossfade(true)
            placeholder(R.drawable.placeholder)
        }
    }
}
