package com.example.app.registration

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.R
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.app.MainActivity
import com.example.app.login.LoginFragment

class RegistrationFragment : Fragment() {

    private var selecionadoMasculino = false
    private var selecionadoFeminino = false

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        val botaoMasculino = view.findViewById<Button>(R.id.male_button)
        val botaoFeminino = view.findViewById<Button>(R.id.female_button)

        botaoMasculino.setOnClickListener {
            if (!selecionadoMasculino) {
                botaoMasculino.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.my_primary))
                selecionadoMasculino = true
                botaoFeminino.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                selecionadoFeminino = false
            }
        }

        botaoFeminino.setOnClickListener {
            if (!selecionadoFeminino) {
                botaoFeminino.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.my_primary))
                selecionadoFeminino = true
                botaoMasculino.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                selecionadoMasculino = false
            }
        }

        val registerButton = view.findViewById<Button>(R.id.register_button_in_registration_page)
        registerButton.setOnClickListener {
            (activity as MainActivity).isLogged = true
            (activity as MainActivity).setupLoginBinding()
        }

        val loginText = view.findViewById<TextView>(R.id.login_text_in_change_password_page)
        loginText.setOnClickListener {
            (activity as MainActivity).replaceFragment(LoginFragment())
        }

        return view
    }
}