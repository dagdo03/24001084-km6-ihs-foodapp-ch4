package com.example.kokomputer.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kokomputer.R
import com.example.kokomputer.data.datasource.category.CategoryDataSourceImpl
import com.example.kokomputer.data.datasource.menu.MenuDataSource
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
import com.example.kokomputer.presentation.home.adapter.OnItemClickedListener
import com.example.kokomputer.utils.GenericViewModelFactory


class HomeFragment : Fragment() {
    private var menuAdapter: MenuListAdapter? = null
    private var isUsingGridMode: Boolean = true
    private val dataSource: MenuDataSource by lazy {
        MenuDataSourceImpl()
    }
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
    private fun setClickAction() {
        binding.ivIconList.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            changeBtnIcon()
            bindMenuList(isUsingGridMode)
            setClickAction()
        }
    }


    private fun changeBtnIcon() {
        binding.ivIconList.setImageResource(if (isUsingGridMode) R.drawable.ic_list_view else R.drawable.ic_grid_view)
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
        bindMenuList(true)
        setClickAction()
    }

    private fun bindCategoryList(data: List<Category>) {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter.submitData(data)
    }

    private fun bindMenuList(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid) MenuListAdapter.MODE_GRID else MenuListAdapter.MODE_LIST
        menuAdapter =
            MenuListAdapter(
                listMode = listMode,
                listener = object : OnItemClickedListener<Menu> {
                    override fun onItemClicked(item: Menu) {
                        navigateToDetail(item)
                    }
                },
            )
        binding.rvMenu.apply {
            adapter = this@HomeFragment.menuAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid) 2 else 1)
        }
        menuAdapter?.submitData(dataSource.getMenuData())
    }

    private fun navigateToDetail(item: Menu) {
        val navController = findNavController()
        val bundle = bundleOf(Pair(DetailMenuActivity.EXTRA_MENU, item))
        navController.navigate(R.id.action_menuFragment_to_detailActivity, bundle)
    }
}