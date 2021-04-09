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
        precision mediump float;

        attribute vec2 aVertexPosition;
        attribute vec3 aVertexColor;

        varying vec3 fragColor;

        void main() {
            fragColor = aVertexColor;
            gl_Position = vec4(aVertexPosition, 0.0, 1.0);
            gl_PointSize = 5.0;
        }`;

    const fsSource = `
        precision mediump float;

        varying vec3 fragColor;

        void main() {
            gl_FragColor = vec4(fragColor, 1.0);
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
    compoundBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, compoundBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, 20 * 200, gl.DYNAMIC_DRAW);
    shaderProgram.aVertexPosition = gl.getAttribLocation(shaderProgram, 'aVertexPosition')
    shaderProgram.aVertexColor = gl.getAttribLocation(shaderProgram, 'aVertexColor')
    gl.vertexAttribPointer(
        shaderProgram.aVertexPosition,
        2,          // number of values per iteration
        gl.FLOAT,   // data type
        false,      // normalization
        5 * Float32Array.BYTES_PER_ELEMENT,          // how many bytes to get from one set of values to the next
        0);         // how many bytes inside the buffer to start from
    gl.vertexAttribPointer(
        shaderProgram.aVertexColor,
        3,          // number of values per iteration
        gl.FLOAT,   // data type
        false,      // normalization
        5 * Float32Array.BYTES_PER_ELEMENT,          // how many bytes to get from one set of values to the next
        2 * Float32Array.BYTES_PER_ELEMENT);         // how many bytes inside the buffer to start from
    gl.enableVertexAttribArray(shaderProgram.aVertexPosition);
    gl.enableVertexAttribArray(shaderProgram.aVertexColor);

    turtle = new Turtle(gl, compoundBuffer);
    turtle.run()
}


class Turtle {
    constructor(gl, buffer) {
        this.gl = gl;
        this.buffer = buffer;
        // color
        this.STARTING_COLOR = [0.0, 0.0, 0.0];
        // direction (0 - 360 deg), 0 is right, going counter-clockwise
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
            -1.0 + (Math.abs(this.MIN_Y / (this.MAX_Y - this.MIN_Y)) * 2),
            this.STARTING_COLOR
        );
        this.points.shift();
    }

    __addPoint(x, y, color=this.STARTING_COLOR) {
        var pts = [x, y, ...color];
        this.points.push(pts);
        this.points.push(pts);
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.buffer);
        this.gl.bufferSubData(this.gl.ARRAY_BUFFER, 20 * (this.points.length - 2), new Float32Array(pts));
        this.gl.bufferSubData(this.gl.ARRAY_BUFFER, 20 * (this.points.length - 1), new Float32Array(pts));
    }

    __forward(distance, color=this.STARTING_COLOR) {
        console.log(distance, color)
        // previous point
        var [start_x, start_y, r, g, b] = this.points.slice(-1)[0];
        // direction in radians
        var dir_rad = (this.last_direction % 360) * Math.PI / 180;
        // scaled distance
        var dx = Math.cos(dir_rad) * distance / (Math.abs(this.MAX_X - this.MIN_X) / 2);
        var dy = Math.sin(dir_rad) * distance / (Math.abs(this.MAX_Y - this.MIN_Y) / 2);
        this.__addPoint(start_x + dx, start_y + dy, color);
    }

    __left(deg) {
        this.last_direction += deg;
    }

    __right(deg) {
        this.last_direction -= deg;
    }

    __polygon(n, size, color=this.STARTING_COLOR) {
        const deg = 360 / n;
        this.__forward(0, color);
        this.__forward(0, color);
        for (var i = 0; i < n; i++) {
            this.__forward(size, color);
            this.__left(deg);
        }
    }

    __renderAll() {
        this.gl.clear(gl.COLOR_BUFFER_BIT);
        this.gl.drawArrays(gl.LINE_STRIP, 0, this.points.length);
        this.gl.drawArrays(gl.POINT, 0, this.points.length);
    }

    __renderElement(func, args, color=this.STARTING_COLOR) {
        const start = this.points.length;
        func(...args, color);
        const count = this.points.length - start;
        this.gl.drawArrays(gl.LINES, start-1, count);
    }

    run() {
        this.gl.clear(gl.COLOR_BUFFER_BIT);
        this.__renderElement(this.__polygon.bind(this), [5, 40], [1, 0, 0]);
        this.__right(120);
        this.__forward(20);
        this.__renderElement(this.__polygon.bind(this), [3, 30], [0, 1, 0]);
        this.__right(120);
        this.__forward(50);
        this.__renderElement(this.__polygon.bind(this), [6, 20], [0, 0, 1]);

        // this.__renderAll();
    }


}