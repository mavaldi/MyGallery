package com.example.mygallery.repository

import android.content.Context
import com.example.mygallery.model.GalleryImage
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date


class ImageRepository {
    fun getDummyImages(): List<GalleryImage> {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return listOf(
            GalleryImage(
                id = "1",
                title = "Sunset Beach",
                imageUrl = "https://picsum.photos/id/10/800/600",
                date = formatter.format(Date(System.currentTimeMillis() - 86400000 * 3))
            ),
            GalleryImage(
                id = "2",
                title = "Mountain View",
                imageUrl = "https://picsum.photos/id/11/800/600",
                date = formatter.format(Date(System.currentTimeMillis() - 86400000 * 2))
            ),
            GalleryImage(
                id = "3",
                title = "Forest Path",
                imageUrl = "https://picsum.photos/id/12/800/600",
                date = formatter.format(Date(System.currentTimeMillis() - 86400000))
            ),
            GalleryImage(
                id = "4",
                title = "City Skyline",
                imageUrl = "https://picsum.photos/id/13/800/600",
                date = formatter.format(Date())
            ),
            GalleryImage(
                id = "5",
                title = "Waterfall",
                imageUrl = "https://picsum.photos/id/14/800/600",
                date = formatter.format(Date())
            ),
            GalleryImage(
                id = "6",
                title = "Desert Landscape",
                imageUrl = "https://picsum.photos/id/15/800/600",
                date = formatter.format(Date())
            )
        )
    }

    fun getLocalImages(context: Context): List<GalleryImage> {
        // Implementasi nyata akan menggunakan ContentResolver untuk mengakses media
        return emptyList()
    }
}