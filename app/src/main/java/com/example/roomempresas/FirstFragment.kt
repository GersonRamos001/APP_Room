package com.example.roomempresas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dblib.model.entity.Empresa
import com.example.roomempresas.app.EmpresaApplication
import com.example.roomempresas.databinding.FragmentFirstBinding
import com.example.roomempresas.viewmodel.EmpresaViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val viewModel: EmpresaViewModel by viewModels {
        EmpresaViewModel.EmpresaViewModelFactory((this@FirstFragment.requireActivity().application as EmpresaApplication).repository)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonList.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_empresaFragment)
        }

        binding.buttonAdd.setOnClickListener {
            insertEmpresaToDB()
        }
    }

    private fun insertEmpresaToDB() {
        val name: String = binding.inputLayoutNombre.editText?.text.toString()
        val address: String = binding.inputLayoutDireccion.editText?.text.toString()
        val email: String = binding.inputLayoutEmail.editText?.text.toString()
        val phone: String = binding.inputLayoutTelefono.editText?.text.toString()
        val urlFoto: String = binding.inputLayoutUrl.editText?.text.toString()

        if (inputValidation(name, address, email, phone, urlFoto)) {
            val empresa = Empresa(0, name, address, email, phone, urlFoto)
            viewModel.insert(empresa)
            Snackbar.make(binding.root, "Company ${name} added sucessfully!", Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(binding.root, "Please complete all the fields", Snackbar.LENGTH_LONG).show()
        }

    }

    fun inputValidation(nombre: String, direccion: String, email: String, telefono: String, urlFoto: String): Boolean {
        return nombre != "" && direccion != "" && email != "" && telefono != "" && urlFoto != ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}