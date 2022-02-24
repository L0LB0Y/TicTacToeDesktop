import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadSvgResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import viewmodel.HomeViewModel.Companion.backgroundColorState
import viewmodel.HomeViewModel.Companion.currentState
import viewmodel.HomeViewModel.Companion.numberList
import viewmodel.HomeViewModel.Companion.oID
import viewmodel.HomeViewModel.Companion.playerMoveSaver
import viewmodel.HomeViewModel.Companion.playerOneScore
import viewmodel.HomeViewModel.Companion.playerTwoScore
import viewmodel.HomeViewModel.Companion.restTheGame
import viewmodel.HomeViewModel.Companion.startChangingColor
import viewmodel.HomeViewModel.Companion.statusList
import viewmodel.HomeViewModel.Companion.xID
import java.io.File
import java.io.FileInputStream

@Composable
fun HomeScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color(0xFFf6f9f8))
            .padding(4.dp)
    ) {
        Header()
        Body()
        Footer()
    }
}


@Composable
fun ColumnScope.Header() {
    Box(modifier = Modifier.weight(0.2f), contentAlignment = Alignment.BottomCenter) {
        Row(
            Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Player One",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3073f8),
                modifier = Modifier.padding(end = 20.dp)
            )
            Text(
                text = "$playerOneScore-$playerTwoScore",
                color = Color(0xFF455563),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp, textAlign = TextAlign.Center,
                modifier = Modifier
                    .shadow(shape = CircleShape, elevation = 8.dp, clip = true)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xFFf9f9fc))
                    .padding(start = 10.dp, end = 10.dp)
            )
            Text(
                text = "Player Two",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFf7873f),
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}

@Composable
fun ColumnScope.Body() {
    Box(
        Modifier
            .weight(0.6f), contentAlignment = Alignment.Center
    ) {
        DrawTheGamePatch()
        DrawTheNet()
    }
}

@Composable
fun DrawTheGamePatch() {
    LazyGridLayout(list = numberList, gridSpan = 3) { item ->
        val index = numberList.indexOf(item)
        GamePatchCard(index) {
            if (!startChangingColor)
                if (numberList.contains(statusList[index])) {
                    statusList[index] = currentState
                    playerMoveSaver(numberList[index])
                    currentState = if (currentState == oID) xID else oID
                }
        }
    }
}

@Composable
fun DrawTheNet() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.Transparent)
    ) {
        Row(
            Modifier
                .fillMaxSize()

        ) {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFf2f4f5))
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFf2f4f5))
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFf2f4f5))
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFf2f4f5))
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun RowScope.GamePatchCard(index: Int, onClickAction: () -> Unit) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(
                if (backgroundColorState[index]) Color(0xFFf2f4f5) else Color.Transparent
            )
            .clickable {

                onClickAction()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val text = if (statusList[index] == oID) "O" else if (statusList[index] == xID) "X" else ""
        Text(
            text = text,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = if (text == "X") Color(0xFF2d71f4) else Color(0xFFf87d39)
        )
    }
}

@Composable
fun ColumnScope.Footer() {
    Box(
        modifier = Modifier
            .weight(0.2f)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        val file = File("C:\\Users\\patoa\\IdeaProjects\\TicTacToe\\src\\main\\resources\\restart.svg")
        val input = FileInputStream(file)
        Image(
            painter = loadSvgResource(input, density = Density(0f,0f)),
            contentDescription = "",
            modifier = Modifier
                .size(55.dp)
                .shadow(elevation = 14.dp, shape = CircleShape, clip = true)
                .clip(CircleShape)
                .clickable {
                    restTheGame()
                }
                .background(color = Color(0xFFf9fdfe))
                .padding(5.dp)
        )
    }
}

/** This Function Will Draw Lazy Vertical Grid Layout*/
@Composable
fun <T> LazyGridLayout(
    list: List<T>,
    gridSpan: Int,
    content: @Composable RowScope.(item: T) -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        scrollState.animateScrollTo(list.size)
    }
    var listDropped = list
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        elevation = 20.dp, shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFFfdfdfd)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(scrollState),
        ) {
            for (i in 0..listDropped.lastIndex step gridSpan)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    listDropped.take(gridSpan).forEach { content(it) }
                    listDropped = listDropped.drop(gridSpan)
                }
        }

    }
}