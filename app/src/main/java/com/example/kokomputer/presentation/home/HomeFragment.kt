package com.example.kokomputer.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kokomputer.data.datasource.catalog.CategoryDataSourceImpl
import com.example.kokomputer.data.datasource.menu.MenuDataSourceImpl
import com.example.kokomputer.data.model.Category
import com.example.kokomputer.data.model.Menu
import com.example.kokomputer.data.repository.CategoryRepository
import com.example.kokomputer.data.repository.CategoryRepositoryImpl
import com.example.kokomputer.data.repository.MenuRepository
import com.example.kokomputer.data.repository.MenuRepositoryImpl
import com.example.kokomputer.databinding.FragmentHomeBinding
import com.example.kokomputer.presentation.detailproduct.DetailMenuActivity
import com.example.kokomputer.presentation.home.adapter.CategoryListAdapter
import com.example.kokomputer.presentation.home.adapter.MenuListAdapter
import com.example.kokomputer.utils.GenericViewModelFactory


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val menuDataSource = MenuDataSourceImpl()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = CategoryDataSourceImpl()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }
    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {

        }
    }
    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter {
            DetailMenuActivity.startActivity(requireContext(), it)
        }
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
        bindCategoryList(viewModel.getCategories())
        bindMenuList(viewModel.getMenus())
    }

    private fun bindCategoryList(data: List<Category>) {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter.submitData(data)
    }

    private fun bindMenuList(data: List<Menu>) {
        binding.rvMenu.apply {
            adapter = this@HomeFragment.menuAdapter
        }
        menuAdapter.submitData(data)
    }

}