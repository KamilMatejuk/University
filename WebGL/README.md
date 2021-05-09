## Contents
* `Fractal` [Koch_Snowflake](#koch-snowflake)
* `Fractal` [Sierpinsky_Triangle](#sierpinsky-triangle)
* `3D Objects` [Maze](#maze)
* `Animations` [Pong](#pong)
* `Textures` [Pong_Textured](#pong-textured)
* `Uniforms` [Render_Types](#render-types)
* `2D Logo` [Turtle_1](#turtle-1)
* `2D Logo` [Turtle_2](#turtle-2)
* `3D Logo` [Turtle_3D](#turtle-3d)

## Topics
### Koch Snowflake
Draw a 2D fractal of [Koch Snowflake](https://en.wikipedia.org/wiki/Koch_snowflake) of degree specified by user.
There are 3 programs, one uses simple `SVG`, others `WebGL`, the `_layered` version adds multiple layers of Koch Snowflake and camera movement.

### Sierpinsky Triangle
Draw a 2D fractal of [Sierpinsky Triangle](https://en.wikipedia.org/wiki/Sierpi%C5%84ski_triangle) of degree specified by user.
There are 3 programs, one uses simple `SVG`, others `WebGL`, the `_layered` version adds multiple layers of Sierpinski Triangle and camera movement.

### Maze
Implement simple camera movement (without rotation), and generate random cuboids on the path to avoid.

### Pong
Simple pong game implemented in `WebGL` to learn animations.

### Pong Textured
Simple pong game in `WebGL` with added multiple differnet textures.

### Render Types
Show differences between WebGL render types. The user can add points in canvas by clicking. Then user can see different renders of added points, based on type of render (`gl.POINTS`, `gl.LINE_STRIP`, `gl.LINE_LOOP`, `gl.LINES`, `gl.TRIANGLE_STRIP`, `gl.TRIANGLE_FAN`, `gl.TRIANGLES`). Additionally program used `uniform` variables, so user can change color at every time.

### Turtle 1
Implement procedure of *Turtle Graphics*, also known as [Logo programming language](https://en.wikipedia.org/wiki/Logo_(programming_language)). Using commands `forward`, `left` and `right`, draw some shapes in different colours.

### Turtle 2
Implement procedure of *Turtle Graphics*, also known as [Logo programming language](https://en.wikipedia.org/wiki/Logo_(programming_language)). Get coanvas scaled boundries form user, as well as drawing color. Give user option to input commands (`forward`, `left`, `right`) to draw path in specified color.

### Turtle 3D
Implement procedure of *Turtle Graphics*, also known as [Logo programming language](https://en.wikipedia.org/wiki/Logo_(programming_language)). Now the user can move in 3 dimentions, using commands `forward`, `left`, `right`, `up`, `down`.
