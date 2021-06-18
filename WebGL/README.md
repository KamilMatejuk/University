## Contents
### `06.2021`<br/>
├─» [3D_Graph](#3d-graph) `3D Objects` `Lighting` `Animation` `ColorMask`<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [3D_Graph_Lighting](#3d-graph)<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [3D_Graph_Animation](#3d-graph)<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [3D_Graph_Animation_Odd](#3d-graph)<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─» [3D_Graph_Anaglyph](#3d-graph)<br/>
└─» [Textured_Cube](#textured-cube) `3D Textures` `Skybox` `Camera`<br/>
### `05.2021`<br/>
├─» [Koch_Snowflake](#koch-snowflake) `Fractal`<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [Koch_Snowflake_SVG](#koch-snowflake)<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [Koch_Snowflake_WebGL](#koch-snowflake)<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [Koch_Snowflake_Layered](#koch-snowflake)<br/>
├─» [Sierpinsky_Triangle](#sierpinsky-triangle) `Fractal`<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [Sierpinsky_Triangle_SVG](#sierpinsky-triangle)<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [Sierpinsky_Triangle_WebGL](#sierpinsky-triangle)<br/>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─» [Sierpinsky_Triangle_Layered](#sierpinsky-triangle)<br/>
├─» [Pong](#pong) `Animations`<br/>
├─» [Pong_Textured](#pong-textured) `Textures`<br/>
└─» [Render_Types](#render-types) `Uniforms`<br/>
### `04.2021`<br/>
├─» [Maze](#maze) `3D Objects`<br/>
└─» [Turtle](#turtle) `2D Logo`<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [Turtle_1](#turtle)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─» [Turtle_2](#turtle)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─» [Turtle_3D](#turtle)<br/>
## Topics
### 3D_Graph
Draw a `R^2 -> R` graph, and allow user to change camera orientation, as well as choosing drawn function.
* Basic version `3D_Graph.html` uses ambient and diffuse light.
* Version with lighting `3D_Graph_Lighting.html` adds directional light, to blending distant parts of graph into background.
* Version animated `3D_Graph_Animation.html` shows two different graphs, rotating in opposite directions, blending with each other using opacity (alpha channel).
* Version animated `3D_Graph_Animation_Odd.html`, instead of opacity, shows one graph on pixels where `x + y` is odd, and another where it's even.
* Version `3D_Graph_Anaglyph.html` uses `ColorMask` to show graph as two, slightly distant graphs - one red and one blue - to create a 3D-like look of [anaglyph](https://en.wikipedia.org/wiki/Anaglyph_3D).

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

### Textured_Cube
Load textures into cube, as well as skybox. The user can change minfication / magnification filters, and change perspective of camera using arrows on keyboard.

### Turtle
Implement procedure of *Turtle Graphics*, also known as [Logo programming language](https://en.wikipedia.org/wiki/Logo_(programming_language)). Using commands `forward`, `left` and `right`, draw some shapes in different colours.
In version 2, get coanvas scaled boundries form user, as well as drawing color, and give user option to input commands (`forward`, `left`, `right`) to draw path in specified color. 3D version allows user to move in 3 dimentions, using commands `forward`, `left`, `right`, `up`, `down`.
