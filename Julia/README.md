## Contents
### `02.2021`<br/>
├─» [IEEE754_Standard_Restrictions](#IEEE754_Standard_Restrictions) `Scientific calculations`<br/>
├─» [Condition_Number_Of_Function](#Condition_Number_Of_Function) `Scientific calculations`<br/>
├─» [Roots_Of_Polynomials](#Roots_Of_Polynomials) `Scientific calculations`<br/>
├─» [Interpolation_Polynomial](#Interpolation_Polynomial) `Scientific calculations`<br/>
└─» [Systems_Of_Equations](#Systems_Of_Equations) `Scientific calculations`<br/>

## Running
Programs written for jupyter notebook.
```
jupyter notebook file.ipynb
```

## Topics
### IEEE754_Standard_Restrictions
Calculating restrictions related to `IEEE754` standard, including [machine epsilon](https://en.wikipedia.org/wiki/Machine_epsilon), [eta](https://docs.julialang.org/en/v1/base/numbers/#Base.nextfloat), floatmin, floatmax. Get to know binary structures of numbers in `IEEE754`.

### Condition_Number_Of_Function
Checking [condition number](https://en.wikipedia.org/wiki/Condition_number) of problems on corner cases. Wilkinson polinomial, Hilbert matrix, etc.

### Roots_Of_Polynomials
Finding roots of a polynomial, using methods: [bisection](https://en.wikipedia.org/wiki/Bisection_method), [Newton](https://en.wikipedia.org/wiki/Newton%27s_method), and [secant](https://en.wikipedia.org/wiki/Newton%27s_method).

### Interpolation_Polynomial
Calculating function interpolation using [Newton interpolation polynomial](https://en.wikipedia.org/wiki/Newton_polynomial) and [difference quotients](https://en.wikipedia.org/wiki/Difference_quotient). Calculating interpolation polynomial value using [generalized Horner algorythm](https://en.wikipedia.org/wiki/Horner%27s_method).

### Systems_Of_Equations
Operations on metrices. Solving systems of equations `A = b x` with metrices `A` and vertexes `b`. Using different verions of Gauss algorythm ([with](https://web.mit.edu/10.001/Web/Course_Notes/GaussElimPivoting.html) or without partial choice, [with](https://www.cl.cam.ac.uk/teaching/1314/NumMethods/supporting/mcmaster-kiruba-ludecomp.pdf) or without LU decomposition).