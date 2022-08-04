package sg.toru.skeleton.product.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sg.toru.skeleton.product.R
import sg.toru.skeleton.product.databinding.FragmentProductBaseBinding

class ProductBaseFragment : Fragment() {
    private var _binding:FragmentProductBaseBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.productContainerFragment, ProductFragment.newInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductBaseFragment()
    }
}
