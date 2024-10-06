package com.eminesa.beinconnectclone.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.eminesa.beinconnectclone.R
import com.eminesa.beinconnectclone.databinding.ToolbarCustomBinding

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ToolbarCustomBinding =
        ToolbarCustomBinding.inflate(LayoutInflater.from(context), this, true)

    private var toolbarTitle: String? = null
    private var toolbarBackgroundColor: Int = context.getColor(R.color.orange)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, 0, 0).apply {
            try {
                toolbarTitle = getString(R.styleable.CustomToolbar_ct_title) ?: "Default Title"

                // Apply attributes to the view
                binding.txtTitle.text = toolbarTitle
                binding.root.setBackgroundColor(toolbarBackgroundColor)

                // Listener for the back button (optional)
                binding.imgBack.setOnClickListener {
                    // Handle back button click
                }

                // Listener for the search button (optional)
                binding.imgSearch.setOnClickListener {
                    // Handle search button click
                }
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        binding.txtTitle.text = title
    }

    override fun setBackgroundColor(color: Int) {
        binding.root.setBackgroundColor(color)
    }
}
