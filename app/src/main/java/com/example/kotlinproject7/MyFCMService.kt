package com.example.kotlinproject7

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.renderscript.ScriptIntrinsicBLAS.UNIT
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        createNotificationChannel()

        val type = message.data["type"]
            ?.let{NotificationType.valueOf(it)}
        val title = message.data["title"]
        val content = message.data["message"]

        type ?:return



        NotificationManagerCompat.from(this)
            .notify(type.id,createNotification(title,content,type))
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESCRIPTION
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun createNotification(title:String?, message:String?, type:NotificationType) : Notification
    {
        val intent = Intent(this,MainActivity::class.java)
            .apply{
                putExtra("notificationType", "${type.title} 타입")
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) //가장 위에 같은 액티비티는 단 하나(갱신 형태)
            }
        //푸시 알림을 클릭했을 때 Action
        val pendingIntent = PendingIntent.getActivity(this, type.id, intent, FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        when(type){
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE ->{
                notificationBuilder.setStyle(NotificationCompat.BigTextStyle()
                    .bigText("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+"zzzzzzzzzzzzzzzzzzzzajflkjsfjkals"+
                            "fjlkafjlkasjkflasjflksajlkfasjlkf"+"ajslkfjkalsfjkalajkslfjakslfjklasjf"+"lajlkfsajlkfjksalfjklasjfklsajlkf")
                )
            }
        }

        return notificationBuilder.build()
    }

    companion object{
        private const val CHANNEL_NAME = "Emoji Party"
        private const val CHANNEL_DESCRIPTION = "Emoji Party 를 위한 채널"
        private const val CHANNEL_ID = "Channel ID"
    }
}