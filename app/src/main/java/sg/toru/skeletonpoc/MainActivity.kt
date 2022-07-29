package sg.toru.skeletonpoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import sg.toru.skeleton.product.ui.ProductBaseFragment
import sg.toru.skeleton.product.ui.ProductFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.mainFragmentContainer,
            ProductBaseFragment.newInstance(),
        ).commit()
    }
}