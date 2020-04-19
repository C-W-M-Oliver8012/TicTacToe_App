package miller.caden.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var reset: Button
    private lateinit var winner: TextView
    private lateinit var mode: TextView
    private lateinit var turn: String
    private var win: Boolean = false
    private var computerMovesFirst: Boolean = false
    private lateinit var gameType: String
    private var startingDepth: Int = 0

    private fun getBoard (): Array<String> {
        val cell1 = button1.text as String
        val cell2 = button2.text as String
        val cell3 = button3.text as String
        val cell4 = button4.text as String
        val cell5 = button5.text as String
        val cell6 = button6.text as String
        val cell7 = button7.text as String
        val cell8 = button8.text as String
        val cell9 = button9.text as String

        return arrayOf(cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9)
    }

    private fun buttonClick (button: Button) {
        if (button.text == " " && !win && turn == "PlayerOne") {

            button.setTextColor(Color.parseColor("#000000"))
            button.text = "X"

            win = checkWin()
            if (win) {
                winner.text = "You Win!" as String
                winner.setTextColor (Color.parseColor("#007003"))
            }
            else {
                turn = "PlayerTwo"
                var board = getBoard()
                if (!boardIsFull(board)) {
                    computerTurn()
                }
                board = getBoard()
                if (boardIsFull(board)) {
                    winner.text = "Tie" as String
                    winner.setTextColor (Color.parseColor("#000000"))
                }
                win = checkWin()
                if (win) {
                    winner.text = "You Lose!" as String
                    winner.setTextColor (Color.parseColor("#f7362d"))
                }
            }
            turn = "PlayerOne"
        }
    }

    private fun scoreBoard (board: Array<String>): Int {
        val player = "X"
        val computer = "O"

        // checks rows
        for (i in 0 until 7 step 3) {
            if (board[i] == board[i+1] && board[i+1] == board[i+2] && board[i] == player) {
                return -1
            }
            else if (board[i] == board[i+1] && board[i+1] == board[i+2] && board[i] == computer) {
                return 1
            }
        }

        // checks columns
        for (i in 0 until 3) {
            if (board[i] == board[i+3] && board[i+3] == board[i+6] && board[i] == player) {
                return -1
            }
            else if (board[i] == board[i+3] && board[i+3] == board[i+6] && board[i] == computer) {
                return 1
            }
        }

        // checks diagonals
        if (board[0] == board[4] && board[4] == board[8] && board[0] == player) {
            return -1
        }
        else if (board[0] == board[4] && board[4] == board[8] && board[0] == computer) {
            return 1
        }

        if (board[2] == board[4] && board[4] == board[6] && board[2] == player) {
            return -1
        }
        else if (board[2] == board[4] && board[4] == board[6] && board[2] == computer) {
            return 1
        }

        return 0
    }

    private fun max (a: Int, b: Int): Int {
        if (a > b) {
            return a
        }
        else if (b > a) {
            return b
        }
        else {
            return a
        }
    }

    private fun min (a: Int, b: Int): Int {
        if (a < b) {
            return a
        }
        else if (b < a) {
            return b
        }
        else {
            return a
        }
    }

    private fun checkWin (): Boolean {
        val board = getBoard()

        // checks rows
        for (i in 0 until 7 step 3) {
            if (board[i] == board[i+1] && board[i+1] == board[i+2] && board[i] != " ") {
                return true
            }
        }

        // checks columns
        for (i in 0 until 3) {
            if (board[i] == board[i+3] && board[i+3] == board[i+6] && board[i] != " ") {
                return true
            }
        }

        // checks diagonals
        if (board[0] == board[4] && board[4] == board[8] && board[0] != " ") {
            return true
        }
        else if (board[2] == board[4] && board[4] == board[6] && board[2] != " ") {
            return true
        }

        return false
    }

    private fun minimax (board: Array<String>, depth: Int, isMaximizing: Boolean): Int {
        val score: Int = scoreBoard(board)

        if (score == 1) {
            return score
        }
        else if (score == -1) {
            return score
        }
        if (boardIsFull(board)) {
            return score
        }
        if (depth == 0) {
            return score
        }

        if (isMaximizing) {
            var value = -1000
            for (i in 0 until 9) {
                if (board[i] == " ") {
                    board[i] = "O"
                    value = max (value, minimax(board, depth - 1, false))
                    board[i] = " "
                }
            }
            return value
        }
        else {
            var value = 1000
            for (i in 0 until 9) {
                if (board[i] == " ") {
                    board[i] = "X"
                    value = min (value, minimax(board, depth - 1, true))
                    board[i] = " "
                }
            }
            return value
        }
    }

    private fun bestMove (board: Array<String>):Int {
        var best: Int = Double.NEGATIVE_INFINITY.toInt()
        var moveScore: Int
        var move = 0
        var firstScore = 0
        var evalCount = 0
        var sameScores = true
        for (i in 0 until 9) {
            if (board[i] == " ") {
                board[i] = "O"
                moveScore = minimax(board, startingDepth, false)
                board[i] = " "
                evalCount++
                if (evalCount == 1) {
                    firstScore = moveScore
                }
                if (firstScore != moveScore) {
                    sameScores = false
                }
                if (moveScore > best) {
                    best = moveScore
                    move = i
                }
            }
        }

        if (sameScores) {
            move = (0..8).random()
        }
        move++
        return move
    }

    private fun computerTurn () {
        val board = getBoard()
        var useRand = false

        while (true) {
            var r = bestMove(board)
            if (useRand) {
                r = (1..9).random()
            }

            if (r == 1 && button1.text == " ") {
                button1.setTextColor(Color.parseColor("#f7362d"))
                button1.text = "O"
                break
            }
            else if (r == 2 && button2.text == " ") {
                button2.setTextColor(Color.parseColor("#f7362d"))
                button2.text = "O"
                break
            }
            else if (r == 3 && button3.text == " ") {
                button3.setTextColor(Color.parseColor("#f7362d"))
                button3.text = "O"
                break
            }
            else if (r == 4 && button4.text == " ") {
                button4.setTextColor(Color.parseColor("#f7362d"))
                button4.text = "O"
                break
            }
            else if (r == 5 && button5.text == " ") {
                button5.setTextColor(Color.parseColor("#f7362d"))
                button5.text = "O"
                break
            }
            else if (r == 6 && button6.text == " ") {
                button6.setTextColor(Color.parseColor("#f7362d"))
                button6.text = "O"
                break
            }
            else if (r == 7 && button7.text == " ") {
                button7.setTextColor(Color.parseColor("#f7362d"))
                button7.text = "O"
                break
            }
            else if (r == 8 && button8.text == " ") {
                button8.setTextColor(Color.parseColor("#f7362d"))
                button8.text = "O"
                break
            }
            else if (r == 9 && button9.text == " ") {
                button9.setTextColor(Color.parseColor("#f7362d"))
                button9.text = "O"
                break
            }

            useRand = true
        }
    }

    private fun boardIsFull (board: Array<String>): Boolean {
        var match = true

        for (i in 0 until 7 step 3) {
            if (board[i] == " " || board[i+1] == " " || board[i+2] == " ") {
                match = false
            }
        }

        return match
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val extra = intent.getStringExtra(EXTRA_MESSAGE)
        gameType = extra as String

        button1 = findViewById(R.id.button1)
        button1.text = " "
        button2 = findViewById(R.id.button2)
        button2.text = " "
        button3 = findViewById(R.id.button3)
        button3.text = " "
        button4 = findViewById(R.id.button4)
        button4.text = " "
        button5 = findViewById(R.id.button5)
        button5.text = " "
        button6 = findViewById(R.id.button6)
        button6.text = " "
        button7 = findViewById(R.id.button7)
        button7.text = " "
        button8 = findViewById(R.id.button8)
        button8.text = " "
        button9 = findViewById(R.id.button9)
        button9.text = " "
        winner = findViewById(R.id.winner)
        winner.text = "Game in Progress" as String
        winner.setTextColor (Color.parseColor("#000000"))
        reset = findViewById(R.id.resetButton)
        reset.text = "Reset" as String
        mode = findViewById(R.id.mode)
        mode.text = gameType

        turn = "PlayerOne"

        if (gameType == "Easy") {
            startingDepth = 1
        }
        else if (gameType == "Medium") {
            startingDepth = 2
        }
        else if (gameType == "Hard") {
            startingDepth = 100
        }

        button1.setOnClickListener() {
            buttonClick (button1)
        }

        button2.setOnClickListener() {
            buttonClick (button2)
        }

        button3.setOnClickListener() {
            buttonClick (button3)
        }

        button4.setOnClickListener() {
            buttonClick (button4)
        }

        button5.setOnClickListener() {
            buttonClick (button5)
        }

        button6.setOnClickListener() {
            buttonClick (button6)
        }

        button7.setOnClickListener() {
            buttonClick (button7)
        }

        button8.setOnClickListener() {
            buttonClick (button8)
        }

        button9.setOnClickListener() {
            buttonClick (button9)
        }

        reset.setOnClickListener() {
            button1.text = " "
            button2.text = " "
            button3.text = " "
            button4.text = " "
            button5.text = " "
            button6.text = " "
            button7.text = " "
            button8.text = " "
            button9.text = " "
            turn = "PlayerOne"
            win = false
            winner.text = "Game in Progress" as String
            winner.setTextColor (Color.parseColor("#000000"))
            computerMovesFirst = computerMovesFirst == false

            if (computerMovesFirst) {
                computerTurn()
            }
        }
    }
}
