package miller.caden.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.os.Bundle
import android.view.View
import android.widget.TextView

const val EXTRA_MESSAGE = "miller.caden.tictactoe.MESSAGE"

class MainActivity : AppCompatActivity () {

    lateinit var category: TextView
    lateinit var easyMode: Button
    lateinit var mediumMode: Button
    lateinit var hardMode: Button

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_main)

        category = findViewById(R.id.category)
        category.text = getString(R.string.select_an_option)
        easyMode = findViewById(R.id.easyMode)
        easyMode.text = getString(R.string.easy)
        mediumMode = findViewById(R.id.mediumMode)
        mediumMode.text = getString(R.string.medium)
        hardMode = findViewById(R.id.hardMode)
        hardMode.text = getString(R.string.hard)

        easyMode.setOnClickListener {
            startGame(easyMode.findViewById(R.id.easyMode))
        }

        mediumMode.setOnClickListener {
            startGame(mediumMode.findViewById(R.id.mediumMode))
        }

        hardMode.setOnClickListener {
            startGame(hardMode.findViewById(R.id.hardMode))
        }
    }

    private fun startGame (view: View) {
        val intent = Intent (this, GameActivity::class.java).apply {
            if (view.id == R.id.easyMode) {
                putExtra(EXTRA_MESSAGE, "Easy")
            }
            else if (view.id == R.id.mediumMode) {
                putExtra(EXTRA_MESSAGE, "Medium")
            }
            else if (view.id == R.id.hardMode) {
                putExtra(EXTRA_MESSAGE, "Hard")
            }
        }
        startActivity(intent)
    }
}
