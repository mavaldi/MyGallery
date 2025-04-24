package com.example.mygallery.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mygallery.ui.screens.DetailScreen
import com.example.mygallery.ui.screens.GalleryScreen
import com.example.mygallery.ui.screens.UploadScreen
import com.example.mygallery.viewmodel.GalleryViewModel

@Composable
fun NavGraph(
    viewModel: GalleryViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val actions = remember(navController) { NavActions(navController) }

    NavHost(
        navController = navController,
        startDestination = "gallery"
    ) {
        composable("gallery") {
            GalleryScreen(
                onImageClick = { image ->
                    actions.navigateToDetail(image.id)
                },
                onUploadClick = actions.navigateToUpload,
                viewModel = viewModel
            )
        }

        composable(
            route = "detail/{imageId}",
            arguments = listOf(navArgument("imageId") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageId = backStackEntry.arguments?.getString("imageId") ?: ""
            val image = viewModel.images.value.firstOrNull { it.id == imageId }

            if (image != null) {
                DetailScreen(
                    image = image,
                    onBack = actions.navigateBack
                )
            } else {
                actions.navigateBack()
            }
        }

        composable("upload") {
            UploadScreen(
                onUploadComplete = actions.navigateBack,
                onCancel = actions.navigateBack,
                viewModel = viewModel
            )
        }
    }
}

class NavActions(private val navController: NavHostController) {
    fun navigateToDetail(imageId: String) {
        navController.navigate("detail/$imageId")
    }

    fun navigateToUpload() {
        navController.navigate("upload")
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}