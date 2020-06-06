package com.anil.tailwebstask.welcomeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.annotation.StringRes

import com.anil.tailwebstask.R

class SliderItemFragment : Fragment() {

    companion object {
        private const val ARG_POSITION = "slider-position"

        // prepare all title ids arrays
        @StringRes
        private val PAGE_TITLES =
            intArrayOf(R.string.be_part, R.string.perfect_app, R.string.pg_open)

        // prepare all subtitle ids arrays
        @StringRes
        private val PAGE_TEXT = intArrayOf(
            R.string.be_part_text, R.string.perfect_app_text, R.string.pg_open_text
        )

        // prepare all background images arrays
        @StringRes
        private val BG_IMAGE = intArrayOf(
            R.drawable.ic_bg_red, R.drawable.ic_bg_blue, R.drawable.ic_bg_purple
        )
        fun newInstance(position: Int): SliderItemFragment {
            val fragment = SliderItemFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = arguments!!.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_welcome_screen_slider, container, false)
        return v
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set page background
        view.setBackground(
            requireActivity().getDrawable(
                BG_IMAGE[position]
            )
        )
        val title: TextView = view.findViewById(R.id.slider_heading_textView)
        val titleText: TextView = view.findViewById(R.id.slider_description_textView)
        // set page title
        title.setText(PAGE_TITLES[position])
        // set page sub title text
        titleText.setText(PAGE_TEXT[position])
        // set page image
    }

}
