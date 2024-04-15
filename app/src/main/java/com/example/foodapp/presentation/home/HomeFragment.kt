package com.example.foodapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.data.datasource.category.CategoryApiDataSource
import com.example.foodapp.data.datasource.menu.MenuApiDataSource
import com.example.foodapp.data.model.Category
import com.example.foodapp.data.model.Menu
import com.example.foodapp.data.repository.CategoryRepository
import com.example.foodapp.data.repository.CategoryRepositoryImpl
import com.example.foodapp.data.repository.MenuRepository
import com.example.foodapp.data.repository.MenuRepositoryImpl
import com.example.foodapp.data.source.local.preference.UserPreferenceImpl
import com.example.foodapp.data.source.network.services.FoodAppApiService
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.presentation.detailproduct.DetailMenuActivity
import com.example.foodapp.presentation.home.adapter.CategoryListAdapter
import com.example.foodapp.presentation.home.adapter.MenuListAdapter
import com.example.foodapp.presentation.home.adapter.OnItemClickedListener
import com.example.foodapp.utils.GenericViewModelFactory
import com.example.foodapp.utils.proceedWhen


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = FoodAppApiService.invoke()
        val userPreference = UserPreferenceImpl(requireContext())
        val menuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(
            HomeViewModel(
                categoryRepository,
                menuRepository,
                userPreference
            )
        )
    }
    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            getMenuData(it.name)
        }
    }
    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(viewModel.getListMode()) {
            navigateToDetail(it)
        }
    }

    private fun getMenuData(name: String? = null) {
        viewModel.getMenu(name).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
                }
            )
        }
    }
    private fun setupMenu() {
        binding.rvMenu.apply {
            adapter = menuAdapter
        }
    }
    private fun getCategoryData() {
        viewModel.getCategory().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                }
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
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
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
            viewModel.changeListMode()
        }
    }


    private fun changeBtnIcon(usingGridMode: Boolean) {
        binding.ivIconList.setImageResource(if (usingGridMode) R.drawable.ic_list_view else R.drawable.ic_grid_view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    private fun bindMenuList(data : List<Menu>) {
        menuAdapter.submitData(data)
    }

    private fun navigateToDetail(item: Menu) {
        val navController = findNavController()
        val bundle = bundleOf(Pair(DetailMenuActivity.EXTRA_MENU, item))
        navController.navigate(R.id.action_menuFragment_to_detailActivity, bundle)
    }
}