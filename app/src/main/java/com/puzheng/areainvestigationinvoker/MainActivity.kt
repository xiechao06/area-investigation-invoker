package com.puzheng.areainvestigationinvoker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A login screen that offers login via email/password.
 */
class MainActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        org_code.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        sign_in_button.setOnClickListener({ attemptLogin() })
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        // Reset errors.


        // Store values at the time of the login attempt.

        var cancel = false
        var focusView: View? = null


        listOf(username, org_name, org_code).forEach {
            view ->
            view.error = null
            if (TextUtils.isEmpty(view.text)) {
                view.error = getString(R.string.error_field_required)
                focusView = username
                cancel = true
            }
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            val intent = Intent(Intent.ACTION_SEND, Uri.parse("region-investigation://"))
            intent.putExtra("USERNAME", username.text.toString())
            intent.putExtra("ORG_NAME", org_name.text.toString())
            intent.putExtra("ORG_CODE", org_code.text.toString())
            Log.v("Area Investigate Invoker", intent.extras.toString())
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }


    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}

