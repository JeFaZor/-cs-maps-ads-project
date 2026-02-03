package com.example.adssdk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class AdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var adManager: AdManager? = null
    private var currentAd: Ad? = null

    private val containerLayout: LinearLayout
    private val adImageView: ImageView
    private val adTitleText: TextView
    private val adDescriptionText: TextView

    init {
        // Create container
        containerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 0, 0, 0)
            setBackgroundColor(0x00000000) // Transparent
        }

        // Create image view with proper height
        adImageView = ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(150) // Normal height
            ).apply {
                setMargins(0, 0, 0, 12)
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
            adjustViewBounds = true
        }

        // Create title
        adTitleText = TextView(context).apply {
            textSize = 15f
            setTextColor(0xFFFFFFFF.toInt())
            gravity = Gravity.START
            maxLines = 2
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 5)
            }
        }

        // Create description
        adDescriptionText = TextView(context).apply {
            textSize = 13f
            setTextColor(0xFFAAAAAA.toInt())
            gravity = Gravity.START
            maxLines = 2
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Add views to container
        containerLayout.addView(adImageView)
        containerLayout.addView(adTitleText)
        containerLayout.addView(adDescriptionText)

        // Add container to AdView
        addView(containerLayout)

        // Set click listener
        setOnClickListener {
            currentAd?.let { ad ->
                adManager?.trackClick(ad.ad_id)
                openAdLink(ad.link_url)
            }
        }
    }

    // Initialize with AdManager
    fun initialize(adManager: AdManager) {
        this.adManager = adManager
    }

    // Load ad
    fun loadAd() {
        adManager?.fetchRandomAd(
            onSuccess = { ad ->
                currentAd = ad
                displayAd(ad)
                adManager?.trackImpression(ad.ad_id)
            },
            onError = { error ->
                showError(error)
            }
        ) ?: run {
            showError("AdManager not initialized")
        }
    }

    // Display ad with improved image loading
    private fun displayAd(ad: Ad) {
        adTitleText.text = ad.title
        adDescriptionText.text = ad.description

        // Load image with Glide - improved settings
        Glide.with(context)
            .load(ad.image_url)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.holo_red_dark)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter() // Fit entire image in view
            .into(adImageView)
    }

    // Open ad link
    private fun openAdLink(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to open link", Toast.LENGTH_SHORT).show()
        }
    }

    // Show error
    private fun showError(message: String) {
        adTitleText.text = "Ad Error"
        adDescriptionText.text = message
        adImageView.setImageResource(android.R.color.darker_gray)
    }

    // Convert dp to pixels
    private fun dpToPx(dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}