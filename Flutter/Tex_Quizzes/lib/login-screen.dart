import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  void checkUserLoggedIn() async {
    try {
      if (FirebaseAuth.instance.currentUser != null) {
        Navigator.of(context).pushNamed('/account');
      }
      FirebaseAuth.instance.idTokenChanges().listen((User? user) {
        if (user != null) {
          Navigator.of(context).pushNamed('/account');
        } else {
          debugPrint("User not logged in");
        }
      });
    } catch (e) {
      debugPrint(e.toString());
    }
  }

  @override
  void initState() {
    super.initState();
    Future.delayed(Duration.zero, () {
      checkUserLoggedIn();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[200],
      body: Center(
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              // **************************** login ****************************
              Padding(
                padding: EdgeInsets.all(20.0),
                child:
                    Text('Login', style: Theme.of(context).textTheme.headline4),
              ),
              SizedBox(
                width: 400,
                child: Card(
                  child: Padding(
                    padding: EdgeInsets.all(20.0),
                    child: LoginForm(),
                  ),
                ),
              ),

              // *************************** register **************************
              Padding(
                padding: EdgeInsets.all(20.0),
                child: Text('Register',
                    style: Theme.of(context).textTheme.headline4),
              ),
              SizedBox(
                width: 400,
                child: Card(
                  child: Padding(
                    padding: EdgeInsets.all(20.0),
                    child: RegisterForm(),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

// *****************************************************************************
// ******************************** login form *********************************
// *****************************************************************************
class LoginForm extends StatefulWidget {
  @override
  _LoginFormState createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  final _emailTextController = TextEditingController();
  final _passwordTextController = TextEditingController();

  double _formProgress = 0.0;

  String _email = "";
  String _password = "";
  String _error = "";

  void _updateFormProgress() {
    var progress = 0.0;
    final controllers = [
      _emailTextController,
      _passwordTextController,
    ];

    for (final controller in controllers) {
      if (controller.value.text.isNotEmpty) {
        progress += 1 / controllers.length;
      }
    }

    setState(() {
      _formProgress = progress;
    });
  }

  void _login() async {
    var err = "";
    debugPrint("Logging: $_email, $_password");
    try {
      await FirebaseAuth.instance.signInWithEmailAndPassword(
        email: _email,
        password: _password,
      );
    } on FirebaseAuthException catch (e) {
      err = e.toString().split(new RegExp(r'\[|\]'))[2];
    } catch (e) {
      err = e.toString().split(new RegExp(r'\[|\]'))[2];
    } finally {
      if (err == "") {
        Navigator.of(context).pushNamed('/account');
      } else {
        debugPrint(err);
        setState(() {
          _error = err;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      onChanged: _updateFormProgress,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Padding(
            padding: EdgeInsets.all(8.0),
            child: TextFormField(
              controller: _emailTextController,
              decoration: InputDecoration(hintText: 'email'),
              onChanged: (val) => _email = val,
            ),
          ),
          Padding(
            padding: EdgeInsets.all(8.0),
            child: TextFormField(
              controller: _passwordTextController,
              decoration: InputDecoration(hintText: 'Password'),
              onChanged: (val) => _password = val,
              obscureText: true,
            ),
          ),
          Padding(
            padding: EdgeInsets.all(8.0),
            child: Text(
              _error,
              style: TextStyle(color: Colors.red.withOpacity(1.0)),
              textAlign: TextAlign.center,
            ),
          ),
          TextButton(
            style: ButtonStyle(
              foregroundColor: MaterialStateProperty.resolveWith(
                  (Set<MaterialState> states) {
                return states.contains(MaterialState.disabled)
                    ? null
                    : Colors.white;
              }),
              backgroundColor: MaterialStateProperty.resolveWith(
                  (Set<MaterialState> states) {
                return states.contains(MaterialState.disabled)
                    ? null
                    : Colors.blue;
              }),
            ),
            onPressed: _formProgress == 1 ? _login : null,
            child: Text('login'),
          ),
        ],
      ),
    );
  }
}

// *****************************************************************************
// ****************************** register form ********************************
// *****************************************************************************
class RegisterForm extends StatefulWidget {
  @override
  _RegisterFormState createState() => _RegisterFormState();
}

class _RegisterFormState extends State<RegisterForm> {
  final _emailTextController = TextEditingController();
  final _password1TextController = TextEditingController();
  final _password2TextController = TextEditingController();

  double _formProgress = 0.0;

  String _email = "";
  String _password1 = "";
  String _password2 = "";
  String _error = "";

  void _updateForm() {
    var progress = 0.0;
    final controllers = [
      _emailTextController,
      _password1TextController,
      _password2TextController,
    ];

    for (final controller in controllers) {
      if (controller.value.text.isNotEmpty) {
        progress += 1 / controllers.length;
      }
    }

    setState(() {
      _formProgress = progress;
    });
  }

  void _register() async {
    var err = "";
    debugPrint("Registering: $_email, $_password1");
    try {
      await FirebaseAuth.instance.createUserWithEmailAndPassword(
        email: _email,
        password: _password1,
      );
    } on FirebaseAuthException catch (e) {
      err = e.toString().split(new RegExp(r'\[|\]'))[2];
    } catch (e) {
      err = e.toString().split(new RegExp(r'\[|\]'))[2];
    } finally {
      if (err == "") {
        Navigator.of(context).pushNamed('/account');
      } else {
        debugPrint(err);
        setState(() {
          _error = err;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      onChanged: _updateForm,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Padding(
            padding: EdgeInsets.all(8.0),
            child: TextFormField(
              controller: _emailTextController,
              decoration: InputDecoration(hintText: 'email'),
              onChanged: (val) => _email = val,
            ),
          ),
          Padding(
            padding: EdgeInsets.all(8.0),
            child: TextFormField(
              controller: _password1TextController,
              decoration: InputDecoration(hintText: 'Password'),
              onChanged: (val) => _password1 = val,
              obscureText: true,
            ),
          ),
          Padding(
            padding: EdgeInsets.all(8.0),
            child: TextFormField(
              controller: _password2TextController,
              decoration: InputDecoration(hintText: 'Repeat Password'),
              onChanged: (val) => _password2 = val,
              obscureText: true,
            ),
          ),
          Padding(
            padding: EdgeInsets.all(8.0),
            child: Text(
              _error,
              style: TextStyle(color: Colors.red.withOpacity(1.0)),
              textAlign: TextAlign.center,
            ),
          ),
          TextButton(
            style: ButtonStyle(
              foregroundColor: MaterialStateProperty.resolveWith(
                  (Set<MaterialState> states) {
                return states.contains(MaterialState.disabled)
                    ? null
                    : Colors.white;
              }),
              backgroundColor: MaterialStateProperty.resolveWith(
                  (Set<MaterialState> states) {
                return states.contains(MaterialState.disabled)
                    ? null
                    : Colors.blue;
              }),
            ),
            onPressed: (_formProgress == 1 && _password1 == _password2)
                ? _register
                : null,
            child: Text('register'),
          ),
        ],
      ),
    );
  }
}
