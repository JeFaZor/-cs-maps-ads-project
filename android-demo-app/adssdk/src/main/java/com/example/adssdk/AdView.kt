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
            setPadding(16, 16, 16, 16)
            setBackgroundColor(0xFF2d2d2d.toInt())
        }

        // Create image view
        adImageView = ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                400
            ).apply {
                setMargins(0, 0, 0, 16)
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        // Create title
        adTitleText = TextView(context).apply {
            textSize = 18f
            setTextColor(0xFFFFFFFF.toInt())
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 8)
            }
        }

        // Create description
        adDescriptionText = TextView(context).apply {
            textSize = 14f
            setTextColor(0xFFAAAAAA.toInt())
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

    // Display ad
    private fun displayAd(ad: Ad) {
        adTitleText.text = ad.title
        adDescriptionText.text = ad.description

        // Load image with Glide
        Glide.with(context)
            .load(ad.image_url)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.holo_red_dark)
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
}