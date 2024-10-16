package com.example.tp1;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    private Playlist playlist;

    private ExoPlayer exoP;
    private PlayerView vue;

    private Button back;
    private Button back_seconds;
    private Button pause;
    private Button forward_seconds;
    private Button forward;

    private int song_index = 0;
    private long song_position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        back = findViewById(R.id.back);
        back_seconds = findViewById(R.id.back_seconds);
        pause = findViewById(R.id.pause);
        forward_seconds = findViewById(R.id.forward_seconds);
        forward = findViewById(R.id.forward);


        back.setOnClickListener(v -> {
            if(song_index != 0) play_song(--song_index, 0);
        });
        back_seconds.setOnClickListener(v -> {
            exoP.seekTo(exoP.getCurrentPosition() - 10000);
        });
        pause.setOnClickListener(v -> {
            if(exoP.isPlaying())exoP.pause();
            else exoP.play();
        });
        forward_seconds.setOnClickListener(v -> {
            exoP.seekTo(exoP.getCurrentPosition() + 10000);
        });
        forward.setOnClickListener(v -> {
            if(song_index != playlist.music.size()-1) play_song(++song_index, 0);
            System.out.println("hell");
            System.out.println(playlist.music.size());
        });



        exoP = new ExoPlayer.Builder(this).build();
        exoP.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    if (exoP.hasNextMediaItem()) {
                        exoP.seekToNextMediaItem();
                        exoP.play();
                    } else {

                        System.out.println("Fin de la playlist.");
                    }
                }
            }
        });


        vue = findViewById(R.id.playerView);
        vue.setPlayer(exoP);
        vue.setUseController(false);

        get_songs();
    }


    private void play_song(int which, long where){
        exoP.seekTo(which, where);
        exoP.play();
    }

    private void get_songs(){
        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "https://api.jsonbin.io/v3/b/661ab8b1acd3cb34a837f284?meta=false";
        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                playlist = gson.fromJson(response, Playlist.class) ;

                try {
                    FileInputStream fis = openFileInput("fichier.ser");
                    ObjectInputStream ois = new ObjectInputStream(fis);

                    song_index = ois.read();
                    song_position = ois.readLong();

                    ois.close();
                    fis.close();
                } catch (IOException ignored) {
                    System.out.println("crashed in get_songs");
                }

                System.out.println("got songs");

                for(Track m: playlist.music){
                    exoP.addMediaItem(MediaItem.fromUri(m.getSource()));
                }

                exoP.prepare();

                play_song(song_index, song_position);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Crashed");
            }
        });
        rq.add(sr);
    }


    @Override
    protected void onStop() {
        super.onStop();

        exoP.pause();
        try {
            FileOutputStream fos = openFileOutput("fichier.ser", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.write(song_index);
            System.out.println("pos" + exoP.getCurrentPosition());
            oos.writeLong(exoP.getCurrentPosition());

            oos.close();
            fos.close();
        } catch (IOException ignored) {
            System.out.println("crashed in onStop");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        play_song(song_index, song_position);
    }
}