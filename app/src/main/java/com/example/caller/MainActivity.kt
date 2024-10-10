package com.example.caller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class MainActivity : AppCompatActivity() {
    private lateinit var userIdTextField:EditText
    private lateinit var button: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userIdTextField = findViewById(R.id.user_id_text_field)
        button = findViewById(R.id.button_next)

        button.setOnClickListener {
            val userId = userIdTextField.text.toString()
            if (userId.isNotEmpty()) {
                val intent = Intent(this@MainActivity, VideoCallActivity::class.java)
                intent.putExtra("userID", userId)
                startActivity(intent)

                videoCallServices(userId)
            }
        }
    }
    private fun videoCallServices(userID: String) {
        val appID: Long = 1605690294 // Replace with your actual App ID
        val appSign = "6e386fa4ad9765398173bab2c150101fa418019afd6febfcbebd703db4163142" // Replace with your actual App Sign
        val application = application

        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()

        // Configure notification settings for the call invitation
        val notificationConfig = ZegoNotificationConfig().apply {
            sound = "zego_uikit_sound_call"
            channelID = "CallInvitation"
            channelName = "CallInvitation"
        }
        callInvitationConfig.notificationConfig = notificationConfig

        ZegoUIKitPrebuiltCallInvitationService.init(
            application,
            appID,
            appSign,
            userID,  // userID
            userID,  // Replace with the actual userName if needed
            callInvitationConfig
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        ZegoUIKitPrebuiltCallInvitationService.unInit()
    }
}