window.onload = init;

var turtle;

function init() {
    // get canvas and context
    canvas = document.getElementById('canvas');
    gl = canvas.getContext('webgl');
    if (gl === null) {
        alert('Nie da się zainicjalizować WebGL w tej przeglądarce');
        throw new Error('Nie da się zainicjalizować WebGL w tej przeglądarce');
    }
    gl.canvas.width = gl.canvas.clientWidth;
    gl.canvas.height = gl.canvas.clientHeight;
    gl.viewport(0, 0, gl.canvas.width, gl.canvas.height);
    gl.clearColor(1.0, 1.0, 1.0, 1.0);
    gl.clear(gl.COLOR_BUFFER_BIT);

    const vsSource = `
        attribute vec2 aVertexPosition;

        void main() {
            gl_Position = vec4(aVertexPosition, 0.0, 1.0);
            gl_PointSize = 5.0;
        }`;

    const fsSource = `
        #ifdef GL_ES
            precision highp float;
        #endif

        void main() {
            gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);
        }`;

    // initialize a shader program
    const vertexShader = gl.createShader(gl.VERTEX_SHADER);
    gl.shaderSource(vertexShader, vsSource);
    gl.compileShader(vertexShader);

    const fragmentShader = gl.createShader(gl.FRAGMENT_SHADER);
    gl.shaderSource(fragmentShader, fsSource);
    gl.compileShader(fragmentShader);

    const shaderProgram = gl.createProgram();
    gl.attachShader(shaderProgram, vertexShader);
    gl.attachShader(shaderProgram, fragmentShader);
    gl.linkProgram(shaderProgram);

    gl.useProgram(shaderProgram);

    // collect all the info needed to use the shader program
    positionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, 8 * 200, gl.DYNAMIC_DRAW);
    shaderProgram.aVertexPosition = gl.getAttribLocation(shaderProgram, 'aVertexPosition')
    gl.enableVertexAttribArray(shaderProgram.aVertexPosition);
    gl.vertexAttribPointer(
        shaderProgram.aVertexPosition,
        2,          // number of values per iteration
        gl.FLOAT,   // data type
        false,      // normalization
        0,          // how many bytes to get from one set of values to the next
        0);         // how many bytes inside the buffer to start from

    turtle = new Turtle(gl, positionBuffer);
    turtle.run()
}


class Turtle {
    constructor(gl, positions) {
        this.gl = gl;
        this.positions = positions;
        // direction 0 - 360 deg, 0 is right, going counter-clockwise
        this.STARTING_DIRECTION = 0;
        this.last_direction = this.STARTING_DIRECTION;
        // board size
        this.MIN_X = -100, this.MAX_X = 100;
        this.MIN_Y = -100, this.MAX_Y = 100;
        console.assert(this.MIN_X < this.MAX_X, 'Max X boundry have to be greater than min X boundry');
        console.assert(this.MIN_Y < this.MAX_Y, 'Max Y boundry have to be greater than min Y boundry');
        // player starts at point (0, 0)
        this.points = [];
        this.__addPoint(
            -1.0 + (Math.abs(this.MIN_X / (this.MAX_X - this.MIN_X)) * 2),
            -1.0 + (Math.abs(this.MIN_Y / (this.MAX_Y - this.MIN_Y)) * 2)
        );
        this.points.shift();
    }

    __addPoint(x, y) {
        var pts = [x, y];
        this.points.push(pts);
        this.points.push(pts);
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.positions);
        this.gl.bufferSubData(this.gl.ARRAY_BUFFER, 8 * (this.points.length - 2), new Float32Array(pts));
        this.gl.bufferSubData(this.gl.ARRAY_BUFFER, 8 * (this.points.length - 1), new Float32Array(pts));
    }

    __forward(distance) {
        // previous point
        var [start_x, start_y] = this.points.slice(-1)[0];
        // direction in radians
        var dir_rad = (this.last_direction % 360) * Math.PI / 180;
        // scaled distance
        var dx = Math.cos(dir_rad) * distance / (Math.abs(this.MAX_X - this.MIN_X) / 2);
        var dy = Math.sin(dir_rad) * distance / (Math.abs(this.MAX_Y - this.MIN_Y) / 2);
        this.__addPoint(start_x + dx, start_y + dy);
    }

    __left(deg) {
        this.last_direction += deg;
    }

    __right(deg) {
        this.last_direction -= deg;
    }

    __polygon(n, size) {
        const deg = 360 / n;
        for (var i = 0; i < n; i++) {
            this.__forward(size);
            this.__left(deg);
        }
    }

    __renderAll() {
        this.gl.clear(gl.COLOR_BUFFER_BIT);
        this.gl.drawArrays(gl.LINE_STRIP, 0, this.points.length);
        this.gl.drawArrays(gl.POINT, 0, this.points.length);
    }

    __renderElement(func, args) {
        const start = this.points.length;
        func(...args);
        const count = this.points.length - start;
        this.gl.drawArrays(gl.LINES, start-1, count);
        this.gl.drawArrays(gl.POINT, start-1, count);
    }

    run() {
        this.gl.clear(gl.COLOR_BUFFER_BIT);
        this.__renderElement(this.__polygon.bind(this), [5, 40]);
        this.__right(120);
        this.__forward(20);
        this.__renderElement(this.__polygon.bind(this), [3, 30]);
        this.__right(120);
        this.__forward(20);
        this.__forward(20);
        this.__renderElement(this.__polygon.bind(this), [6, 20]);

        // this.__renderAll();
    }


}