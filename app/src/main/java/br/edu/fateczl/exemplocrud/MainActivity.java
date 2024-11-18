package br.edu.fateczl.exemplocrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

    private final HashMap<String, Fragment> MAPA = new HashMap<>(Map.ofEntries(
            Map.entry("professor", new ProfessorFragment()),
            Map.entry("disciplina", new DisciplinaFragment())
    ));

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

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            carregaFragment( MAPA.get( bundle.getString("tipo") ) );
        }else{
            carregaFragment(new InicioFragment());
        }
    }

    private void carregaFragment(Fragment alvo){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, alvo);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String tipo = "disciplina";
        if(item.getItemId() == R.id.item_professor){
            tipo = "professor";
        }

        Bundle bundle = new Bundle();
        bundle.putString("tipo", tipo);
        alternar(bundle);
        return true;
    }

    private void alternar(Bundle bundle){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtras(bundle);
        this.startActivity(i);
        this.finish();
    }
}