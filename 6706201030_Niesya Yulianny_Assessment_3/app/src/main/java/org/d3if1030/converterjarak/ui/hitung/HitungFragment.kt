package org.d3if1030.converterjarak.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if1030.converterjarak.R
import org.d3if1030.converterjarak.databinding.FragmentHitungBinding
import org.d3if1030.converterjarak.db.DataDb
import org.d3if1030.converterjarak.model.Hasil

class HitungFragment : Fragment() {

    private lateinit var binding: FragmentHitungBinding

    private val viewModel: HitungViewModel by lazy {
        val db = DataDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.convert.setOnClickListener { convert() }
        binding.shareButton.setOnClickListener { shareData() }
        viewModel.getHasil().observe(requireActivity(), { showResult(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment
                )
                return true
            }
            R.id.menu_jarak_kota -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_jarakKotaFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun convert() {

          val input = binding.inputEditTextInput.text.toString()
          if (TextUtils.isEmpty(input)) {
              Toast.makeText(context, R.string.input_invalid, Toast.LENGTH_LONG).show()
              return
          }

         viewModel.hitung(
            input.toFloat()
         )
    }


    private fun showResult(result: Hasil?) {
        if (result == null) return
        binding.hasil.text = result.hasil.toString()
    }

    private fun shareData() {
        val message = "Gunakan Converter Jarak!"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }
}