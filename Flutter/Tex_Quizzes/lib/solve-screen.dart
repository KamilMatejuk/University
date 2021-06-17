import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter_tex/flutter_tex.dart';

class Id {
  final String id;
  Id(this.id);
}

class Quiz {
  final String name;
  final List questions;
  Quiz(this.name, this.questions);
}

class Question {
  final String question;
  final String a1;
  final String a2;
  final String a3;
  final String a4;
  final int correct;
  Question(this.question, this.a1, this.a2, this.a3, this.a4, this.correct);
}

class SolveQuizScreen extends StatefulWidget {
  final String quizId;
  const SolveQuizScreen(this.quizId);

  @override
  _SolveQuizScreenState createState() => _SolveQuizScreenState();
}

class _SolveQuizScreenState extends State<SolveQuizScreen> {
  var userAnswers = [];
  bool _checkAnswers = false;
  Future<Quiz> getQuizQuestions() async {
    var quiz;
    await FirebaseFirestore.instance
        .collection('quizes')
        .doc(widget.quizId)
        .get()
        .then((DocumentSnapshot documentSnapshot) {
      if (documentSnapshot.exists && documentSnapshot.data() != null) {
        var questions = (documentSnapshot.get('questions') as List)
            .map((q) => Question(
                  q['question'],
                  q['a1'],
                  q['a2'],
                  q['a3'],
                  q['a4'],
                  q['correct'],
                ))
            .toList();
        quiz = Quiz(
          documentSnapshot.get('name') as String,
          questions,
        );
      }
    });
    if (quiz != null) {
      for (var q in quiz.questions) {
        userAnswers.add([false, false, false, false]);
      }
    }
    return quiz;
  }

  Widget questionWidget(Question question, int index) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        SizedBox(
          height: 100,
          child: TeXView(
            renderingEngine: TeXViewRenderingEngine.katex(),
            child: TeXViewDocument(
              r'$$ ' + question.question + r' $$',
              style: TeXViewStyle.fromCSS(
                  'text-align: center; overflow: visible;'),
            ),
          ),
        ),
        Row(
          children: [
            Expanded(
              flex: 1,
              child: answer(question.a1, index, 0, question.correct - 1),
            ),
            Expanded(
              flex: 1,
              child: answer(question.a2, index, 1, question.correct - 1),
            ),
            Expanded(
              flex: 1,
              child: answer(question.a3, index, 2, question.correct - 1),
            ),
            Expanded(
              flex: 1,
              child: answer(question.a4, index, 3, question.correct - 1),
            ),
          ],
        ),
      ],
    );
  }

  Color getColor(int questionIndex, int answerIndex, int correct) {
    if (!_checkAnswers) {
      return Colors.grey;
    }
    if (userAnswers[questionIndex][answerIndex] && answerIndex == correct) {
      return Colors.green;
    }
    if (userAnswers[questionIndex][answerIndex] && answerIndex != correct) {
      return Colors.red;
    }
    return Colors.grey;
  }

  Widget answer(String text, int questionIndex, int answerIndex, int correct) {
    return SizedBox(
      width: 400,
      child: Container(
        margin: const EdgeInsets.all(3.0),
        padding: const EdgeInsets.all(3.0),
        decoration: BoxDecoration(
            border: Border.all(
                width: _checkAnswers ? 5.0 : 2.0,
                color: getColor(
                  questionIndex,
                  answerIndex,
                  correct,
                ))),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Expanded(
              flex: 1,
              child: Checkbox(
                checkColor: Colors.white,
                value: userAnswers[questionIndex][answerIndex],
                onChanged: (bool? value) {
                  setState(() {
                    if (!_checkAnswers &&
                        !userAnswers[questionIndex][answerIndex]) {
                      for (var j = 0; j < 4; j++) {
                        userAnswers[questionIndex][j] = false;
                      }
                      userAnswers[questionIndex][answerIndex] = true;
                    }
                  });
                },
              ),
            ),
            Expanded(
              flex: 10,
              child: SizedBox(
                height: 75,
                child: TeXView(
                  renderingEngine: TeXViewRenderingEngine.katex(),
                  child: TeXViewDocument(
                    r'$$ ' + text + r' $$',
                    style: TeXViewStyle.fromCSS(
                        'text-align: center; overflow: visible;'),
                  ),
                ),
              ),
            ),
          ],
        ),
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
          crossAxisAlignment: CrossAxisAlignment.end,
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Padding(
              padding: EdgeInsets.all(30.0),
              child: Text(widget.quizId),
            ),
            Center(
              child: SingleChildScrollView(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    // *************************** quiz ************************
                    FutureBuilder(
                      future: getQuizQuestions(),
                      builder: (context, snapshot) {
                        List<Widget> children;
                        if (snapshot.hasData) {
                          Quiz data = snapshot.data as Quiz;
                          children = <Widget>[
                            // welcome text
                            Padding(
                              padding: EdgeInsets.all(20.0),
                              child: Text('Quiz "${data.name}"',
                                  style: Theme.of(context).textTheme.headline4),
                            ),
                            SizedBox(
                              height: MediaQuery.of(context).size.height * 0.7,
                              child: ListView.builder(
                                physics: ClampingScrollPhysics(),
                                shrinkWrap: true,
                                itemCount: data.questions.length,
                                itemBuilder: (context, index) {
                                  return questionWidget(
                                      data.questions[index], index);
                                },
                              ),
                            ),
                            Padding(
                              padding: EdgeInsets.all(30.0),
                              child: FloatingActionButton(
                                onPressed: () {
                                  setState(() {
                                    _checkAnswers = true;
                                  });
                                },
                                tooltip: 'Check',
                                child: Icon(Icons.check),
                                heroTag: "fab_check",
                              ),
                            ),
                          ];
                        } else if (snapshot.hasError) {
                          debugPrint(snapshot.error.toString());
                          children = <Widget>[Text("This quiz doesn't exist")];
                        } else {
                          children = <Widget>[CircularProgressIndicator()];
                        }
                        return Center(
                          child: SingleChildScrollView(
                            physics: ScrollPhysics(),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: children,
                            ),
                          ),
                        );
                      },
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
