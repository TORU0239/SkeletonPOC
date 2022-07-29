package sg.toru.skeleton.product.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.toru0239.skeletoncore.repository.impl.ProductRepositoryImpl
import io.github.toru0239.skeletoncore.usecase.ProductUseCase
import io.github.toru0239.skeletoncore.usecase.impl.ProductUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sg.toru.skeleton.product.R
import sg.toru.skeleton.product.databinding.FragmentProductBinding

class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val useCase: ProductUseCase by lazy {
        ProductUseCaseImpl(
            ProductRepositoryImpl()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcvProducts.run {
            adapter = ProductAdapter{
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                    )
                    .replace(R.id.productContainerFragment, ProductDetailFragment.newInstance())
                    .addToBackStack(ProductFragment::class.simpleName)
                    .commit()
            }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
            )
        }

        callApi()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun callApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val products = useCase.getProductList()
            CoroutineScope(Dispatchers.Main).launch {
                (binding.rcvProducts.adapter as ProductAdapter).submitList(products.products)
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}