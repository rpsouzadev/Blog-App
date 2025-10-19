package com.rpsouza.blogapp.presenter.home.bottomsheet

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rpsouza.blogapp.R
import com.rpsouza.blogapp.databinding.BsMapsChooserBinding
import com.rpsouza.blogapp.domain.model.MapAppOption
import com.rpsouza.blogapp.presenter.home.adapter.MapOptionAdapter
import com.rpsouza.blogapp.utils.findMapApps
import java.net.URLEncoder

class MapsChooserBottomSheet : BottomSheetDialogFragment() {

    interface Listener { fun onOptionClicked(option: MapAppOption) }
    var listener: Listener? = null

    private var _binding: BsMapsChooserBinding? = null
    private val binding get() = _binding!!

    // argumentos que você vai passar via newInstance() / Bundle
    private val addressLabel: String by lazy { requireArguments().getString(ARG_LABEL).orEmpty() }
    private val addressQuery: String by lazy { requireArguments().getString(ARG_QUERY).orEmpty() }
    private val lat: Double? by lazy { if (requireArguments().containsKey(ARG_LAT)) requireArguments().getDouble(ARG_LAT) else null }
    private val lng: Double? by lazy { if (requireArguments().containsKey(ARG_LNG)) requireArguments().getDouble(ARG_LNG) else null }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BsMapsChooserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = getString(R.string.como_chegar_title) // opcional/i18n

        val opts = findMapApps(requireContext(), addressLabel, addressQuery, lat, lng)

        val adapter = MapOptionAdapter(mutableListOf()) { option ->
            listener?.onOptionClicked(option)
            try {
                startActivity(option.launchIntent)
            } catch (_: ActivityNotFoundException) {
                // fallback: navegador
                val url = "https://www.google.com/maps/search/?api=1&query=${
                    URLEncoder.encode(
                        addressQuery,
                        "UTF-8"
                    )
                }"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
            dismiss()
        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
            // separador opcional:
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        adapter.submitList(opts)

        // Ajuste de insets (barra de gesto) se quiser:
        ViewCompat.setOnApplyWindowInsetsListener(binding.recycler) { v, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.updatePadding(bottom = bottom.coerceAtLeast(v.paddingBottom))
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        // Deixa o sheet expandido (opcional)
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_LABEL = "arg_label"
        private const val ARG_QUERY = "arg_query"
        private const val ARG_LAT = "arg_lat"
        private const val ARG_LNG = "arg_lng"

        fun newInstance(
            addressLabel: String,
            addressQuery: String,
            lat: Double? = null,
            lng: Double? = null
        ) = MapsChooserBottomSheet().apply {
            arguments = bundleOf(
                ARG_LABEL to addressLabel,
                ARG_QUERY to addressQuery
            ).also { b ->
                lat?.let { b.putDouble(ARG_LAT, it) }
                lng?.let { b.putDouble(ARG_LNG, it) }
            }
        }
    }
}

