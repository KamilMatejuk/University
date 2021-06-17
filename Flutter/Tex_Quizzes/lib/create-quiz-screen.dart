import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class CreateQuizScreen extends StatefulWidget {
  @override
  _CreateQuizScreenState createState() => _CreateQuizScreenState();
}

class _CreateQuizScreenState extends State<CreateQuizScreen> {
  void checkUserLoggedIn() async {
    try {
      if (FirebaseAuth.instance.currentUser == null) {
        Navigator.of(context).pushNamed('/');
      }
      FirebaseAuth.instance.idTokenChanges().listen((User? user) {
        if (user == null) {
          Navigator.of(context).pushNamed('/');
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

  var quiz = {'name': '', 'questions': []};
  final _quizNameController = TextEditingController();
  final _questionController = TextEditingController();
  final _answer1Controller = TextEditingController();
  final _answer2Controller = TextEditingController();
  final _answer3Controller = TextEditingController();
  final _answer4Controller = TextEditingController();
  var _checkedAnswers = [true, false, false, false];
  var _error = '';

  bool? nextQuestion() {
    // check if all fields filled
    final controllers = [
      _questionController,
      _answer1Controller,
      _answer2Controller,
      _answer3Controller,
      _answer4Controller,
    ];
    for (final controller in controllers) {
      if (controller.value.text.isEmpty) {
        setState(() {
          _error = 'Fill all fields';
        });
        return null;
      }
    }
    // check if one option selected as correct
    var selected = 0;
    var selectedNr = -1;
    for (var i = 0; i < 4; i++) {
      if (_checkedAnswers[i]) {
        selected++;
        selectedNr = i + 1;
      }
    }
    if (selected != 1) {
      setState(() {
        _error = 'Exactly one option can be selected as correct';
      });
      return null;
    }
    // add question
    var q = {
      'question': _questionController.value.text.toString(),
      'a1': _answer1Controller.value.text.toString(),
      'a2': _answer2Controller.value.text.toString(),
      'a3': _answer3Controller.value.text.toString(),
      'a4': _answer4Controller.value.text.toString(),
      'correct': selectedNr,
    };
    (quiz['questions'] as List).add(q);
    debugPrint("Updated quiz $quiz");
    // clear values
    for (final controller in controllers) {
      controller.text = "";
    }
    for (var checkbox in _checkedAnswers) {
      checkbox = false;
    }
    _checkedAnswers[0] = true;
    setState(() {
      _error = '';
    });
    return true;
  }

  void saveQuiz() {
    // save quiz name
    if (_quizNameController.value.text.isEmpty) {
      setState(() {
        _error = 'Add quiz name';
      });
      return null;
    }
    quiz['name'] = _quizNameController.value.text;
    // push to database
    if (FirebaseAuth.instance.currentUser != null) {
      FirebaseFirestore.instance.collection("quizes").add({
        'author': FirebaseAuth.instance.currentUser?.uid,
        'name': quiz['name'],
        'questions': quiz['questions'],
      }).then((value) {
        debugPrint("successs $value");
        Navigator.of(context).pushNamed('/account');
      }).catchError((err) => debugPrint("error ${err.toString()}"));
    } else {
      debugPrint("Not logged in");
    }
  }

  Widget answer(int nr, controller) {
    return SizedBox(
      width: 400,
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Expanded(
            flex: 1,
            child: Checkbox(
              checkColor: Colors.white,
              value: _checkedAnswers[nr - 1],
              onChanged: (bool? value) {
                setState(() {
                  _checkedAnswers[nr - 1] = value!;
                });
              },
            ),
          ),
          Expanded(
            flex: 10,
            child: Padding(
              padding: EdgeInsets.all(8.0),
              child: TextFormField(
                controller: controller,
                decoration: InputDecoration(hintText: 'Answer $nr'),
                textAlign: TextAlign.center,
              ),
            ),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[200],
      body: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        alignment: Alignment.center,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // quiz name
            Padding(
              padding: EdgeInsets.fromLTRB(50.0, 8.0, 50.0, 8.0),
              child: TextFormField(
                controller: _quizNameController,
                decoration: InputDecoration(hintText: 'Quiz Name'),
                textAlign: TextAlign.center,
              ),
            ),
            // question number
            Padding(
              padding: EdgeInsets.all(20.0),
              child: Text('Question ${(quiz['questions'] as List).length + 1}',
                  style: Theme.of(context).textTheme.headline5),
            ),
            // question text
            Padding(
              padding: EdgeInsets.fromLTRB(50.0, 8.0, 50.0, 8.0),
              child: TextFormField(
                controller: _questionController,
                decoration: InputDecoration(hintText: 'Your question'),
                textAlign: TextAlign.center,
              ),
            ),
            // answers
            answer(1, _answer1Controller),
            answer(2, _answer2Controller),
            answer(3, _answer3Controller),
            answer(4, _answer4Controller),
            Padding(
              padding: EdgeInsets.all(8.0),
              child: Text(
                _error,
                style: TextStyle(color: Colors.red.withOpacity(1.0)),
                textAlign: TextAlign.center,
              ),
            ),
            // buttons
            SizedBox(
              width: 400,
              child: Row(
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Padding(
                    padding: EdgeInsets.all(30.0),
                    child: FloatingActionButton(
                      onPressed: saveQuiz,
                      tooltip: 'Finish and save',
                      child: Icon(Icons.save),
                      heroTag: "fab_save",
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.all(30.0),
                    child: FloatingActionButton(
                      onPressed: nextQuestion,
                      tooltip: 'Add next question',
                      child: Icon(Icons.arrow_forward),
                      heroTag: "fab_next",
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
