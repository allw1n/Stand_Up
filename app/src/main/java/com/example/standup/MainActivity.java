package com.example.standup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.standup.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;

    private static final String PRIMARY_CHANNEL_ID = "PRIMARY_NOTIFICATION_CHANNEL";
    private static final String PRIMARY_CHANNEL_NAME = "Stand up notifications";
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ToggleButton alarmToggle = binding.alarmToggle;

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                String message;

                if (checked) {
                    message = getResources().getString(R.string.alarm_turned_on);
                }
                else message = getResources().getString(R.string.alarm_turned_off);

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        createNotificationChannel();
    }

    private void createNotificationChannel() {

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = new NotificationChannel(
                PRIMARY_CHANNEL_ID,
                PRIMARY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.setDescription(getResources().getString(R.string.stand_up_alert));
        notificationManager.createNotificationChannel(notificationChannel);
    }

    private void deliverNotification() {

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(getString(R.string.stand_up_alert))
                .setContentText(getString(R.string.you_should_get_up_and_walk))
                .setContentIntent(pendingContentIntent)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
    }
}