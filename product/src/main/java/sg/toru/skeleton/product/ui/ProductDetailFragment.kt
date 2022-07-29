package sg.toru.skeleton.product.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import coil.load
import io.github.toru0239.skeletoncore.product.Product
import io.github.toru0239.skeletoncore.repository.impl.ProductRepositoryImpl
import io.github.toru0239.skeletoncore.usecase.ProductUseCase
import io.github.toru0239.skeletoncore.usecase.impl.ProductUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sg.toru.skeleton.product.R
import sg.toru.skeleton.product.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val itemId: Int by lazy {
        arguments?.getInt(ITEM_ID)  ?: -1
    }

    private val useCase: ProductUseCase by lazy {
        ProductUseCaseImpl(
            ProductRepositoryImpl()
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object:OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun callApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val product = useCase.getProduct(itemId)
            CoroutineScope(Dispatchers.Main).launch {
                doDrawView(product)
            }
        }
    }

    private fun doDrawView(product: Product) {
        binding.imgMainProduct.load(product.thumbnail) {
            crossfade(true)
        }
    }

    companion object {
        const val ITEM_ID = "ITEM_ID"

        @JvmStatic
        fun newInstance(id: Int) = ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ITEM_ID, id)
            }
        }
    }
}