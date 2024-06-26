package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.app.databinding.ActivityMainWithoutLoginBinding
import com.example.app.home.HomeFragment
import com.example.app.map.MapFragment
import com.example.app.qrcode.QrcodeFragment
import com.example.app.ticket.TicketFragment
import com.example.app.wallet.WalletFragment
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import java.util.*
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.app.accountinfo.AccountInfoFragment
import com.example.app.databinding.ActivityMainWithLoginBinding
import com.example.app.login.LoginFragment
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.replace
import com.example.app.buyTicket.BuyTicketFragment
import com.example.app.cartWithTicket.CartWithTicketFragment
import com.example.app.cartWithoutTicket.CartWithoutTicketsFragment
import com.example.app.databinding.ActivityMainInfoPageBinding
import com.example.app.databinding.ActivityMainWithCartBinding
import com.example.app.homelogin.HomeLoginFragment
import com.example.app.ticketWithTickets.TicketWithTicketsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var notLoginBinding: ActivityMainWithoutLoginBinding
    private lateinit var loginBinding: ActivityMainWithLoginBinding
    private lateinit var shopBinding: ActivityMainWithCartBinding
    private lateinit var infoBinding: ActivityMainInfoPageBinding
    private lateinit var toolbar: Toolbar
    private var cartItemCount = 0
    var isLogged: Boolean = false
    var isButtonClicked = false
    var buyedTicket = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNotLoginBinding()
    }

    fun setupNotLoginBinding() {
        notLoginBinding = ActivityMainWithoutLoginBinding.inflate(layoutInflater)
        setContentView(notLoginBinding.root)
        replaceFragment(HomeFragment())

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        notLoginBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.ticket -> setupInfoBinding()
                R.id.map -> setupInfoBinding()
                R.id.qrcode -> setupInfoBinding()
                R.id.wallet -> setupInfoBinding()
            }
            true
        }

        val btnLogin = findViewById<ImageButton>(R.id.logo_for_person_without_logo)
        btnLogin.setOnClickListener {
            // Substitui o fragmento atual pelo fragmento de login
            setupInfoBinding()
        }
    }

    fun setupLoginBinding() {
        loginBinding = ActivityMainWithLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        replaceFragment(HomeLoginFragment())

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        if (!buyedTicket) {
            loginBinding.bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> replaceFragment(HomeLoginFragment())
                    R.id.ticket -> replaceFragment(TicketFragment())
                    R.id.map -> replaceFragment(MapFragment())
                    R.id.qrcode -> replaceFragment(QrcodeFragment())
                    R.id.wallet -> replaceFragment(WalletFragment())
                }
                true
            }
        } else {
            loginBinding.bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> replaceFragment(HomeLoginFragment())
                    R.id.ticket -> replaceFragment(TicketWithTicketsFragment())
                    R.id.map -> replaceFragment(MapFragment())
                    R.id.qrcode -> replaceFragment(QrcodeFragment())
                    R.id.wallet -> replaceFragment(WalletFragment())
                }
                true
            }
        }

        val btnLogout = findViewById<ImageButton>(R.id.logo_for_person_with_logo)
        btnLogout.setOnClickListener {
            // Substitui o fragmento atual pelo fragmento de login
            setupInfoBinding()
        }

    }

    fun setupShopBinding() {
        shopBinding = ActivityMainWithCartBinding.inflate(layoutInflater)
        setContentView(shopBinding.root)
        replaceFragment(BuyTicketFragment())

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        shopBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setupLoginBinding()
                R.id.ticket -> {
                    setupLoginBinding()
                    replaceFragment(TicketFragment(), R.id.ticket)
                }

                R.id.map -> {
                    setupLoginBinding()
                    replaceFragment(MapFragment(), R.id.map)
                }

                R.id.qrcode -> {
                    setupLoginBinding()
                    replaceFragment(QrcodeFragment(), R.id.qrcode)
                }

                R.id.wallet -> {
                    setupLoginBinding()
                    replaceFragment(WalletFragment(), R.id.wallet)
                }
            }
            true
        }

        val btnLogout = findViewById<ImageButton>(R.id.logo_for_person_with_logo)
        btnLogout.setOnClickListener {
            // Substitui o fragmento atual pelo fragmento de login
            setupInfoBinding()
        }

            val btnCart = findViewById<ImageButton>(R.id.logo_for_cart_buy_ticket)
            btnCart.setOnClickListener {
                if (!isButtonClicked) {
                    replaceFragment(CartWithoutTicketsFragment())
                } else {
                    replaceFragment(CartWithTicketFragment())
                }

            }
    }

    private fun setupInfoBinding() {
        infoBinding = ActivityMainInfoPageBinding.inflate(layoutInflater)
        setContentView(infoBinding.root)
        if (!isLogged) {
            replaceFragment(LoginFragment())

            toolbar = findViewById(R.id.toolbar)
            toolbar.title = ""
            setSupportActionBar(toolbar)

            val btnClose = findViewById<ImageButton>(R.id.close_icon)
            btnClose.setOnClickListener {
                setupNotLoginBinding()
            }
        } else {
            replaceFragment(AccountInfoFragment())

            toolbar = findViewById(R.id.toolbar)
            toolbar.title = ""
            setSupportActionBar(toolbar)

            val btnClose = findViewById<ImageButton>(R.id.close_icon)
            btnClose.setOnClickListener {
                setupLoginBinding()
            }

        }
    }

        fun replaceFragment(fragment: Fragment, menuItemId: Int? = null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, fragment)
            fragmentTransaction.commit()

            // If a menu item ID is provided, set the selected item in the bottom navigation view
            if (!isLogged) {
                menuItemId?.let {
                    loginBinding.bottomNavigationView.selectedItemId = it
                }
            } else {
                menuItemId?.let {
                    loginBinding.bottomNavigationView.selectedItemId = it
                }
            }
        }

        fun exibirDatePicker(view: View) {
            val textbox = view as EditText
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                textbox.context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val currentDate = sdf.format(Date())
                    if (selectedDate >= currentDate &&
                        (textbox.id == R.id.returnBox || selectedDate >= (findViewById<EditText>(R.id.departureBox).text.toString()))
                    ) {
                        textbox.setText(selectedDate)
                    } else {
                        Toast.makeText(
                            textbox.context,
                            "Escolha uma data válida",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                },
                year,
                month,
                day
            )

            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            if (textbox.id == R.id.returnBox) {
                val departureBox = findViewById<EditText>(R.id.departureBox)
                // Definir a data mínima como a data selecionada na DepartureBox
                val departureDate = departureBox.text.toString()
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val minDate = sdf.parse(departureDate).time
                datePickerDialog.datePicker.minDate = minDate
            }
            datePickerDialog.show()
        }

        //para a caixa de texto do Return
        fun onClickReturnBox(view: View) {
            if (findViewById<EditText>(R.id.departureBox).text.toString() == "") {
                Toast.makeText(
                    view.context,
                    "Please select a Departure date first.",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val returnBox = findViewById<EditText>(R.id.returnBox)
            exibirDatePicker(returnBox)
        }

        //para a caixa de texto do Departure
        fun onClickDepartureBox(view: View) {
            val departureBox = findViewById<EditText>(R.id.departureBox)
            exibirDatePicker(departureBox)
        }

        fun limparCaixasTexto(view: View) {
            val fromBox = findViewById<EditText>(R.id.fromBox)
            val toBox = findViewById<EditText>(R.id.toBox)
            val departureBox = findViewById<EditText>(R.id.departureBox)
            val returnBox = findViewById<EditText>(R.id.returnBox)

            // Limpar o texto em todas as caixas de texto
            fromBox.setText("")
            toBox.setText("")
            departureBox.setText("")
            returnBox.setText("")
        }

    fun incrementCartItemCount() {
        cartItemCount++
        val cartItemCountTextView = findViewById<TextView>(R.id.cartItemCountTextView)
        cartItemCountTextView.setText(Integer.toString(cartItemCount))
    }


    fun atualizarCarteira(view: View, moneyToAdd: EditText) {
            val walletBox = findViewById<TextView>(R.id.walletBox)
            val walletValue = walletBox.text.toString().replace("€", "").replace(",", ".").toDoubleOrNull()
            val strMoney = moneyToAdd.text.toString().toDoubleOrNull()

            if (walletValue != null && strMoney != null) {
                val newWalletValue = walletValue + strMoney
                val newWalletValueStr = String.format("%.2f€", newWalletValue)
                walletBox.text = newWalletValueStr
                moneyToAdd.setText("")

                // Save the new wallet value to shared preferences
                val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("walletValue", newWalletValueStr)
                editor.apply()
            }
        }

        fun adicionarDinheiroMBway(view: View) {
            val moneyToAdd = findViewById<EditText>(R.id.moneyInputBox)
            atualizarCarteira(view, moneyToAdd)
        }

        fun adicionarDinheiroMultibanco(view: View) {
            val moneyToAdd = findViewById<EditText>(R.id.moneyInputBox2)
            atualizarCarteira(view, moneyToAdd)
        }

}