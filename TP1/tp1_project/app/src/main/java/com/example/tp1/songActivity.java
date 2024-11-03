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
        exoP = new ExoPlayer.Builder(this).build();

        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {    //l'utilisateur peux bouger la seekbar pour avancer ou reculer la musique
                if (fromUser) {
                    long newPosition = (progress * exoP.getDuration() / 100);
                    exoP.seekTo(newPosition);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            song_index = 0;
            song_position = 0;
            quitting = true;
            finish();
        });

        findViewById(R.id.back).setOnClickListener(v -> {
            if(exoP.getCurrentMediaItemIndex() != 0) exoP.seekToPreviousMediaItem();      //si on est pas au debut, on peut reculer
            else if(replay) exoP.seekTo(playlist.music.size()-1,0);                       //sinon juste si replay
        });
        findViewById(R.id.back_seconds).setOnClickListener(v -> {   //reculer de 10secondes
            exoP.seekTo(exoP.getCurrentPosition() - 10000);
        });
        ImageButton pause = findViewById(R.id.pause);
        pause.setOnClickListener(v -> {         //changer l'image du button selon le state
            if(exoP.isPlaying()){
                exoP.pause();
                pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play));
            }
            else {
                exoP.play();
                pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pause));
            }

        });
        findViewById(R.id.forward_seconds).setOnClickListener(v -> {    //avancer de 10secondes
            exoP.seekTo(exoP.getCurrentPosition() + 10000);
        });
        findViewById(R.id.forward).setOnClickListener(v -> {        //si on est pas a la fin on avance, sinon juste si on est en mode replay
            if(exoP.getCurrentMediaItemIndex() != playlist.music.size()-1) exoP.seekToNextMediaItem();
            else if(replay) exoP.seekTo(0,0);
        });

        Handler handler = new Handler();
        Runnable updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (exoP != null && exoP.isPlaying()) {
                    int currentPosition = (int) exoP.getCurrentPosition();
                    int duration = (int) exoP.getDuration();

                    seekB.setProgress(currentPosition * 100 / duration);    //place la seekbar selon le temps passé

                    time_stamp.setText(formatTime(currentPosition) + " / " + formatTime(duration));
                }
                handler.postDelayed(this, 1000);
            }
        };

        exoP.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY && exoP.getPlayWhenReady()) {   //si la musique est prete a jouer, pars le seekbar
                    handler.post(updateSeekBar);
                } else {
                    handler.removeCallbacks(updateSeekBar);
                }
                if (state == Player.STATE_ENDED) {  //si la musique est fini, passe à la prochaine
                    if (exoP.hasNextMediaItem()) {
                        exoP.seekToNextMediaItem();
                        exoP.play();
                    } else if(replay)  //continue apres la derniere juste si en mode replay
                        exoP.seekTo(0,0);
                }
            }

            @Override
            public void onTracksChanged(Tracks tracks) {
                Player.Listener.super.onTracksChanged(tracks);
                nom_artiste.setText(playlist.music.get(exoP.getCurrentMediaItemIndex()).getArtist());   //change le texte selon la musique
                nom_music.setText(playlist.music.get(exoP.getCurrentMediaItemIndex()).getTitle());

            }
        });

        PlayerView vue = findViewById(R.id.playerView);
        vue.setPlayer(exoP);
        vue.setUseController(false);
    }

    private String formatTime(int milliseconds) {   //fonction pour formatter le temps de la seekbar
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        restarting = true;  //pour pas refaire le onStart si on restart
        exoP.seekTo(song_index, song_position); //variables sauvegardé dans le onPause
        exoP.play();
    }

    @Override
    protected void onStart() {
        super.onStart();
        quitting = false;

        if(restarting){
            exoP = new ExoPlayer.Builder(this).build();
            restarting = false;
            return;
        }

        Intent intent = getIntent();
        song_index = intent.getIntExtra("index",0);
        song_position = intent.getLongExtra("time",0);
        playlist = new Playlist();
        playlist.setMusic((ArrayList<Track>) intent.getSerializableExtra("playlist"));
        replay = intent.getBooleanExtra("replay", false);


        //mets toutes les musiques de la playlist dans le exoPlayer
        for(Track m: playlist.music)
            exoP.addMediaItem(MediaItem.fromUri(m.getSource()));

        exoP.prepare();
        exoP.seekTo(song_index, song_position);
        exoP.play();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(quitting)
            deleteFile("fichier.ser");
        else{
            try {
                FileOutputStream fos = openFileOutput("fichier.ser", MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.write(exoP.getCurrentMediaItemIndex());
                oos.writeLong(exoP.getCurrentPosition());

                oos.close();
                fos.close();
            } catch (IOException ignored) {
                System.out.println("crashed in onStop");
            }
        }
        if (exoP != null) {
            exoP.stop();          // Arrête la lecture
            exoP.release();       // Libère les ressources
            exoP = null;          // Remet la référence à null
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!quitting){      //si je quitte pas, enregistre ou je suis
            song_index = exoP.getCurrentMediaItemIndex();
            song_position = exoP.getCurrentPosition();
        }
    }
}