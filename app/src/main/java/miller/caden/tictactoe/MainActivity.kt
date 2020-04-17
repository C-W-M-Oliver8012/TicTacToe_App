package miller.caden.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.os.Bundle
import android.view.View
import android.widget.TextView

const val EXTRA_MESSAGE = "miller.caden.tictactoe.MESSAGE"

class MainActivity : AppCompatActivity () {

    lateinit var hardMode: Button
    lateinit var easyMode: Button
    lateinit var mediumMode: Button
    lateinit var category: TextView

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_main)

        hardMode = findViewById(R.id.hardMode)
        hardMode.text = "Hard"
        easyMode = findViewById(R.id.easyMode)
        easyMode.text = "Easy"
        mediumMode = findViewById(R.id.mediumMode)
        mediumMode.text = "Medium"

        category = findViewById(R.id.category)
        category.text = "Select an Option"
    }

    fun startGameRandom (view: View) {
        val intent = Intent (this, GameActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, "Easy")
        }
        startActivity(intent)
    }

    fun startGameMedium (view: View) {
        val intent = Intent (this, GameActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, "Medium")
        }
        startActivity(intent)
    }

    fun startGameMinimax (view: View) {
        val intent = Intent (this, GameActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, "Hard")
        }
        startActivity(intent)
    }
}
