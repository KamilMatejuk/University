let currentImageId = 'img1';
let tiles;
let tileWidth, tileHeight;
let rows, cols;
let emptyRow, emptyCol;


/**
 * Load full sized images in the background
 */
function loadFullImages() {
    imgs = document.getElementsByClassName("imgs")[0].getElementsByTagName('img');
    for (let img of imgs) {
        new Promise((resolve, reject) => {
            let url = './imgs/' + img.src.toString().split('/').slice(-1)[0];
            img.setAttribute("src", url);
            img.onload = function () { resolve(url); };
            img.onerror = function () { reject(url); };
        })
        .then(() => console.log(`Correctly loaded`))
        .catch((e) => console.log(`Couldnt load: ${e}`));
    }
}


/**
 * When image is clicked, changes border on choosen image
 */
function changeImage(event) {
    currentImageId = event.target.id;
    const imgs = document.getElementsByTagName('img');
    for (let i of imgs) {
        if (i != event.target) {
            i.classList.remove('choosen');
        } else {
            i.classList.add('choosen');
        }
    }
}

/**
 * Reads parameters and loads a new game
 */
function load() {
    const rows_field = document.getElementById('rows');
    const cols_field = document.getElementById('cols');
    rows = parseInt(rows_field.value) || 4;
    cols = parseInt(cols_field.value) || 4;
    if (rows <= 1 || cols <= 1) {
        alert('Incorrect number of rows or columns');
    } else {
        drawImage();
    }
}

/**
 * Calculate all constants and draw image at the beginning of the game
 */
function drawImage() {
    const canvas = document.getElementById('canvas');
    // add listeners
    canvas.parentNode.addEventListener("click", clickOnCanvas);
    // set canvas size
    canvas.width = 0;
    canvas.height = 0;
    const space = document.getElementsByClassName('draw')[0].getBoundingClientRect();
    const scale = 1.7777778;
    if (space.height < 10 || space.width < space.height * scale) {
        canvas.width = 0.9 * space.width;
        canvas.height = 0.9 * space.width / scale;
    } else {
        canvas.height = 0.9 * space.height;
        canvas.width = 0.9 * space.height * scale;
    }
    // find image
    let image = document.getElementById(currentImageId);
    // divide into parts
    let sWidth = image.naturalWidth / cols;
    let sHeight = image.naturalHeight / rows;
    let cWidth = tileWidth = canvas.width / cols;
    let cHeight = tileHeight = canvas.height / rows;
    // create array
    tiles = [...Array(rows).fill(0)].map((_) => Array(cols).fill(0));
    let sy = 0;
    let cy = 0;
    for (let i = 0; i < rows; i++) {
        let sx = 0;
        let cx = 0;
        for (let j = 0; j < cols; j++) {
            tiles[i][j] = {
                'correctI': i,      // prawidłowa wspołrzędna (wiersz)
                'correctJ': j,      // prawidłowa współrzędna (kolumna)
                'currentI': i,      // aktualna współrzędna (wiersz)
                'currentJ': j,      // aktualna współrzędna (kolumna)
                'sx': sx,           // przesunięcie od górnego lewego rogu źródła w poziomie
                'sy': sy,           // przesunięcie od górnego lewego rogu źródła w pionie
                'sWidth': sWidth,   // szerokość elementu ze źrodła
                'sHeight': sHeight, // wysokość eleemntu ze źródła
                'cx': cx,           // przesunięcie od górnego lewego rogu canvas w poziomie
                'cy': cy,           // przesunięcie od górnego lewego rogu canvas w pionie
                'cWidth': cWidth,   // szerokość elementu na canvas
                'cHeight': cHeight, // wysokość eleemntu na canvas
            };
            sx += sWidth;
            cx += cWidth;
        }
        sy += sHeight;
        cy += cHeight;
    }
    emptyRow = randInt(0, rows);
    emptyCol = randInt(0, cols);
    while (checkCorrect()) {
        shuffle(rows * cols * 3);
    }
    drawUpdated();
}

/**
 * @param {number} min minimal value (including)
 * @param {number} max maximal value (excluding)
 * @returns random integer in range
 */
function randInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min;
}

/**
 * Swap positions of two tiles
 * @param {*} x1 column of first tile
 * @param {*} y1 row of first tile
 * @param {*} x2 column of second tile
 * @param {*} y2 row of second tile
 */
function swap(x1, y1, x2, y2) {
    try {
        // sx
        var temp = tiles[y1][x1].sx;
        tiles[y1][x1].sx = tiles[y2][x2].sx;
        tiles[y2][x2].sx = temp;
        // sy 
        temp = tiles[y1][x1].sy;
        tiles[y1][x1].sy = tiles[y2][x2].sy;
        tiles[y2][x2].sy = temp;
        // correct i
        temp = tiles[y1][x1].correctI;
        tiles[y1][x1].correctI = tiles[y2][x2].correctI;
        tiles[y2][x2].correctI = temp;
        // correct j
        temp = tiles[y1][x1].correctJ;
        tiles[y1][x1].correctJ = tiles[y2][x2].correctJ;
        tiles[y2][x2].correctJ = temp;

        if (emptyRow == y1 && emptyCol == x1) {
            emptyRow = y2;
            emptyCol = x2;
        } else if (emptyRow == y2 && emptyCol == x2) {
            emptyRow = y1;
            emptyCol = x1;
        }
    } catch (e) {
        console.log(`Cannot swap (${x1},${y1}) with (${x2},${y2})`);
    }
}

/**
 * Shuffle the board at the beginning of the game,
 * according to rules - to be reevrsable
 * @param {*} number_of_shuffles how many swaps to make
 */
function shuffle(number_of_shuffles) {
    // randomize positions
    for (let i = 0; i < number_of_shuffles; i++) {
        const direction = [
            [0, 1],  // prawy
            [0, -1], // lewy
            [1, 0],  // górny
            [-1, 0]  // dolny
        ];
        let x = -1;
        let y = -1;
        while (x < 0 || x > cols - 1 || y < 0 || y > rows - 1) {
            const n = direction[randInt(0, 4)];
            x = emptyCol + n[0];
            y = emptyRow + n[1];
        }
        // swap
        swap(emptyCol, emptyRow, x, y);
    }

    // return empty place to upper left corner
    // upper row
    for (let i = 0; i <= rows; i++) {
        swap(emptyCol, emptyRow, emptyCol, emptyRow - 1);
    }

    // left column
    for (let i = 0; i <= cols; i++) {
        swap(emptyCol, emptyRow, emptyCol - 1, emptyRow);
    }
}

/**
 * Redraw the game, every time a user makes a move
 */
function drawUpdated() {
    const image = document.getElementById(currentImageId);
    const context = canvas.getContext('2d');
    context.fillStyle = "red";
    context.fillRect(0, 0, canvas.width, canvas.height);
    for (let i = 0; i < tiles.length; i++) {
        for (let j = 0; j < tiles[0].length; j++) {
            if (i != emptyRow || j != emptyCol) {
                const tile = tiles[i][j]
                context.drawImage(image, tile.sx, tile.sy, tile.sWidth, tile.sHeight, tile.cx, tile.cy, tile.cWidth, tile.cHeight)
                context.lineWidth = 3;
                context.strokeStyle = 'white';
                context.stroke();
                context.rect(tile.cx, tile.cy, tile.cWidth, tile.cHeight)
            }
        }
    }
    moveNeighbours();
}

/**
 * Check if current alignment of tiles is correct
 * @returns boolean value
 */
function checkCorrect() {
    for (let i = 0; i < tiles.length; i++) {
        for (let j = 0; j < tiles[0].length; j++) {
            const tile = tiles[i][j]
            if (tile.correctI != tile.currentI || tile.correctJ != tile.currentJ) {
                return false;
            }
        }
    }
    return true;
}

/**
 * Listener for when user clicks on the canvas,
 * checks if selected move is correct and makes it
 * @param {*} event DOM 'onclick' event
 */
function clickOnCanvas(event) {
    const {x, y} = document.getElementById('canvas').getBoundingClientRect();
    const j = parseInt((event.x - x) / tileWidth) // kolumna
    const i = parseInt((event.y - y) / tileHeight) // wiersz
    console.log(i, j)
    if (j >= 0 && j < cols && i >= 0 && i < rows) {
        if (
            (Math.abs(i - emptyRow) == 1 && Math.abs(j - emptyCol) == 0) ||
            (Math.abs(i - emptyRow) == 0 && Math.abs(j - emptyCol) == 1)
        ) {
            swap(j, i, emptyCol, emptyRow)
            drawUpdated()
            setTimeout(() => {
                if (checkCorrect()) {
                    alert('Yeah! You completed the puzzle!')
                }
            }, 300);
        }
    }
}

/**
 * change position of neighbour elements (for highlight when hovered)
 */
function moveNeighbours() {
    const canvas = document.getElementById('canvas');
    let neighbours = canvas.parentNode.getElementsByTagName('div');
    const canvas_top = parseInt(canvas.getBoundingClientRect().top);
    const canvas_left = parseInt(canvas.getBoundingClientRect().left);
    // create elements
    if (neighbours.length !== 4) {
        for (let n of neighbours) {
            n.parentNode.removeChild(n);
        }
        for (let i = 0; i < 4; i++) {
            const div = document.createElement('div');
            canvas.parentNode.appendChild(div);
            div.classList.add('neighbour');
        }
    }
    // move
    const directions = [
        [0, 1],  // prawy
        [0, -1], // lewy
        [1, 0],  // górny
        [-1, 0]  // dolny
    ]
    neighbours = canvas.parentNode.getElementsByTagName('div');
    for (let i = 0; i < 4; i++) {
        neighbours[i].style.width = `${parseInt(tileWidth)}px`;
        neighbours[i].style.height = `${parseInt(tileHeight)}px`;
        neighbours[i].style.top = `${canvas_top + parseInt((emptyRow + directions[i][0]) * tileHeight)}px`;
        neighbours[i].style.left = `${canvas_left + 20 + parseInt((emptyCol + directions[i][1]) * tileWidth)}px`;
    }
}
