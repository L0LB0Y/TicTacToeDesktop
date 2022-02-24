package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeViewModel {
    companion object {
        private val scope = CoroutineScope(Dispatchers.Main)
        var playerOneScore by mutableStateOf(0)
        var playerTwoScore by mutableStateOf(0)
        const val xID = 6677
        const val oID = 7766
        var currentState by mutableStateOf(xID)
        var startChangingColor by mutableStateOf(false)
        val numberList = (1..9).toList()
        val statusList = mutableStateListOf<Int>().also {
            it.addAll((1..9).toList())
        }
        val backgroundColorState = mutableStateListOf<Boolean>().also {
            for (i in 1..9)
                it.add(false)
        }
        private val winConditions = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(3, 6, 9),
            listOf(1, 5, 9),
            listOf(3, 5, 7)
        )
        private val playerOne = mutableStateListOf<Int>()
        private val playerTwo = mutableStateListOf<Int>()

        fun playerMoveSaver(position: Int) {
            if (currentState == xID)
                playerOne.add(position)
            else
                playerTwo.add(position)
            checkForWin()
        }

        private fun checkForWin() {
            when {
                winConditions.any { playerOne.containsAll(it) } -> showPlayerOneWins()
                winConditions.any { playerTwo.containsAll(it) } -> showPlayerTwoWins()
                playerOne.size + playerTwo.size == 9 -> showGameIsDraw()
            }
        }

        private fun showGameIsDraw() {
            scope.launch {
                drawColor()
                resetTheVariable()
            }
        }

        private fun showPlayerTwoWins() {
            scope.launch {
                playerTwoScore++
                val subList = winConditions.find { playerTwo.containsAll(it) }
                subList?.let { drawColor(it) }
                resetTheVariable()
            }

        }

        private suspend fun drawColor() {
            startChangingColor = true
            repeat(3) {
                backgroundColorState.replaceAll { !it }
                delay(300)
                backgroundColorState.replaceAll { !it }
                delay(300)
            }
        }

        private suspend fun drawColor(list: List<Int>) {
            startChangingColor = true
            repeat(3) {
                backgroundColorState[list[0] - 1] = true
                backgroundColorState[list[1] - 1] = true
                backgroundColorState[list[2] - 1] = true
                delay(150)
                backgroundColorState[list[0] - 1] = false
                backgroundColorState[list[1] - 1] = false
                backgroundColorState[list[2] - 1] = false
                delay(150)
            }
        }

        private fun showPlayerOneWins() {
            scope.launch {
                playerOneScore++
                val subList = winConditions.find { playerOne.containsAll(it) }
                subList?.let { drawColor(it) }
                resetTheVariable()
            }
        }

        private fun resetTheVariable() {
            startChangingColor = false
            currentState = xID
            playerOne.clear()
            playerTwo.clear()
            statusList.clear()
            statusList.addAll((1..9).toList())
            backgroundColorState.clear()
            backgroundColorState.apply {
                for (i in 1..9)
                    this.add(false)
            }
        }

        fun restTheGame() {
            playerOneScore = 0
            playerTwoScore = 0
            resetTheVariable()
        }
    }
}