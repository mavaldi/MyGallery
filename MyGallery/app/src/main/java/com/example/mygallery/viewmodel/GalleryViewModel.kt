package com.example.mygallery.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygallery.model.GalleryImage
import com.example.mygallery.repository.ImageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GalleryViewModel(private val repository: ImageRepository = ImageRepository()) : ViewModel() {
    private val _images = MutableStateFlow<List<GalleryImage>>(emptyList())
    val images: StateFlow<List<GalleryImage>> = _images.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadImages()
    }

    private fun loadImages() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(1000) // Simulasi loading
            _images.value = repository.getDummyImages()
            _isLoading.value = false
        }
    }

    fun uploadImage(title: String, imageUri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000) // Simulasi waktu upload

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val newImage = GalleryImage(
                id = (_images.value.size + 1).toString(),
                title = title,
                imageUrl = imageUri.toString(),
                date = formatter.format(Date()),
                isLocal = true
            )

            _images.value = _images.value + newImage
            _isLoading.value = false
        }
    }
}