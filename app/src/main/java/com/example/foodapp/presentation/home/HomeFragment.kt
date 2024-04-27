package com.example.foodapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.data.model.Category
import com.example.foodapp.data.model.Menu
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.presentation.detailproduct.DetailMenuActivity
import com.example.foodapp.presentation.home.adapter.CategoryListAdapter
import com.example.foodapp.presentation.home.adapter.MenuListAdapter
import com.example.foodapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            getMenuData(it.name)
        }
    }
    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(homeViewModel.getListMode()) {
            navigateToDetail(it)
        }
    }

    private fun getMenuData(name: String? = null) {
        homeViewModel.getMenu(name).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
                },
            )
        }
    }

    private fun setupMenu() {
        binding.rvMenu.apply {
            adapter = menuAdapter
        }
    }

    private fun getCategoryData() {
        homeViewModel.getCategory().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                },
            )
        }
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter.submitData(data)
    }

    private fun setupCategory() {
        binding.rvCategory.apply {
            adapter = categoryAdapter
        }
    }

    private fun observeGridMode() {
        homeViewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            changeBtnIcon(isUsingGridMode)
            changeLayout(isUsingGridMode)
        }
    }

    private fun changeLayout(usingGridMode: Boolean) {
        val listMode = if (usingGridMode) MenuListAdapter.MODE_GRID else MenuListAdapter.MODE_LIST
        menuAdapter.updateListMode(listMode)
        setupMenu()
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), if (usingGridMode) 2 else 1)
        }
    }

    private fun setClickAction() {
        binding.ivIconList.setOnClickListener {
            homeViewModel.changeListMode()
        }
    }

    private fun changeBtnIcon(usingGridMode: Boolean) {
        binding.ivIconList.setImageResource(if (usingGridMode) R.drawable.ic_list_view else R.drawable.ic_grid_view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupCategory()
        setupMenu()
        observeGridMode()
        getCategoryData()
        getMenuData(null)
        setClickAction()
    }

    private fun bindCategoryList(data: List<Category>) {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter.submitData(data)
    }

    private fun bindMenuList(data: List<Menu>) {
        menuAdapter.submitData(data)
    }

    private fun navigateToDetail(item: Menu) {
        val navController = findNavController()
        val bundle = bundleOf(Pair(DetailMenuActivity.EXTRA_MENU, item))
        navController.navigate(R.id.action_menuFragment_to_detailActivity, bundle)
    }
}
