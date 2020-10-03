package br.com.andretortolano.numbertrivia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.andretortolano.numbers_trivia.ui.NumbersTriviaActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(NumbersTriviaActivity.getIntent(baseContext))
    }
}