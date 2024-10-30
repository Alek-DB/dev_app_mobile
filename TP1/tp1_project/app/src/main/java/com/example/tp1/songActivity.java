package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.Tracks;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

public class songActivity extends AppCompatActivity {


    private Playlist playlist;
    private TextView nom_artiste, nom_music, time_stamp;
    private SeekBar seekB;
    private ExoPlayer exoP;
    private int song_index = 0;
    private long song_position = 0;

    private boolean quitting = false;
    private boolean restarting = false;
    private boolean replay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_song);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        nom_artiste = findViewById(R.id.nom_artiste);
        nom_music = findViewById(R.id.nom_music);
        seekB = findViewById(R.id.seekBar);
        time_stamp = findViewById(R.id.time_stamp);

        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    long newPosition = (long) (progress * exoP.getDuration() / 100);
                    exoP.seekTo(newPosition);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        exoP = new ExoPlayer.Builder(this).build();

        ImageButton back = findViewById(R.id.back);
        ImageButton back_seconds = findViewById(R.id.back_seconds);
        ImageButton pause = findViewById(R.id.pause);
        ImageButton forward_seconds = findViewById(R.id.forward_seconds);
        ImageButton forward = findViewById(R.id.forward);

        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            deleteFile("fichier.ser");
            song_index = 0;
            song_position = 0;
            quitting = true;

//            setResult(RESULT_OK);
            finish();
        });

        back.setOnClickListener(v -> {
            if(song_index != 0) exoP.seekToPreviousMediaItem();
        });
        back_seconds.setOnClickListener(v -> {
            exoP.seekTo(exoP.getCurrentPosition() - 10000);
        });
        pause.setOnClickListener(v -> {
            if(exoP.isPlaying()){
                exoP.pause();
                pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play));
            }
            else {
                exoP.play();
                pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pause));
            }

        });
        forward_seconds.setOnClickListener(v -> {
            exoP.seekTo(exoP.getCurrentPosition() + 10000);
        });
        forward.setOnClickListener(v -> {
            if(song_index != playlist.music.size()-1)exoP.seekToNextMediaItem();
        });



        Handler handler = new Handler();
        Runnable updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (exoP != null && exoP.isPlaying()) {
                    int currentPosition = (int) exoP.getCurrentPosition();
                    int duration = (int) exoP.getDuration();

                    seekB.setProgress(currentPosition * 100 / duration);

                    time_stamp.setText(formatTime(currentPosition) + " / " + formatTime(duration));
                }
                handler.postDelayed(this, 1000);
            }
        };

        exoP.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY && exoP.getPlayWhenReady()) {
                    handler.post(updateSeekBar);
                } else {
                    handler.removeCallbacks(updateSeekBar);
                }
                if (state == Player.STATE_ENDED) {
                    if (exoP.hasNextMediaItem()) {
                        exoP.seekToNextMediaItem();
                        exoP.play();
                    } else if(replay){
                        exoP.seekTo(0,0);
                        System.out.println("Fin de la playlist.");
                    }

                }
            }

            @Override
            public void onTracksChanged(Tracks tracks) {
                Player.Listener.super.onTracksChanged(tracks);

                nom_artiste.setText(playlist.music.get(exoP.getCurrentMediaItemIndex()).getArtist());
                nom_music.setText(playlist.music.get(exoP.getCurrentMediaItemIndex()).getTitle());

            }
        });

        PlayerView vue = findViewById(R.id.playerView);
        vue.setPlayer(exoP);
        vue.setUseController(false);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("restart");

        restarting = true;
        exoP.seekTo(song_index, song_position);
        exoP.play();
    }

    @Override
    protected void onStart() {
        super.onStart();
        quitting = false;

        if(restarting){
            restarting = false;
            return;
        }

        System.out.println("start");
        Intent intent = getIntent();
        song_index = intent.getIntExtra("index",0);
        song_position = intent.getLongExtra("time",0);
        playlist = new Playlist();
        playlist.setMusic((ArrayList<Track>) intent.getSerializableExtra("playlist"));
        replay = intent.getBooleanExtra("replay", false);


        for(Track m: playlist.music){
            exoP.addMediaItem(MediaItem.fromUri(m.getSource()));
        }

        exoP.prepare();
        exoP.seekTo(song_index, song_position);
        exoP.play();
    }

    private String formatTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }



    @Override
    protected void onStop() {
        super.onStop();

        try {
            FileOutputStream fos = openFileOutput("fichier.ser", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            if(quitting){
                oos.write(0);
                oos.writeLong(0);
                System.out.println("delete save");
            }
            else{
                oos.write(exoP.getCurrentMediaItemIndex());
                oos.writeLong(exoP.getCurrentPosition());
                System.out.println("save");
            }

            oos.close();
            fos.close();
        } catch (IOException ignored) {
            System.out.println("crashed in onStop");
        }
        exoP.pause();

    }


    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("pause");
        if(!quitting){
            song_index = exoP.getCurrentMediaItemIndex();
            song_position = exoP.getCurrentPosition();
        }
    }
}