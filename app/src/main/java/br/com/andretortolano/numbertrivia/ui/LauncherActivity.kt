package br.com.andretortolano.numbertrivia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.andretortolano.numbers_trivia.ui.NumbersTriviaActivity
import br.com.andretortolano.numbertrivia.R

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(NumbersTriviaActivity.getIntent(baseContext))
    }
}