package com.computer.inu.readit_appjam.Activity

//import com.computer.inu.readit_appjam.Data.PostSigninResponse
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutSignOutResponse
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_settings_page.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsPageActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_page)
        tv_setting_page_premium.setOnClickListener {
            startActivity<UpreadePremiumActivity>()
        }
        iv_setting_back.setOnClickListener {
            finish()
        }

        tv_setting_change_password.setOnClickListener {
            startActivity<ChangePasswordActivity>()
        }
        tv_mypage_setting_logout_btn.setOnClickListener {

            SignoutPost()
        }
        tv_setting_page_guide.setOnClickListener {
            startActivity<WalkThrough_Setting_Guide_Activity>()
        }
    }

    private fun SignoutPost() {

        val postSignOutResponse: Call<PutSignOutResponse> =
            networkService.putSignoutResponse("application/json", SharedPreferenceController.getAccessToken(this))
        postSignOutResponse.enqueue(object : Callback<PutSignOutResponse> {
            override fun onFailure(call: Call<PutSignOutResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PutSignOutResponse>, response: Response<PutSignOutResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()!!.message!!
                    SharedPreferenceController.clearAccessToken(this@SettingsPageActivity)

                    ActivityCompat.finishAffinity(this@SettingsPageActivity)
                    startActivity<LoginActivity>()
                }
            }
        })

    }
}
