import 'package:flutter/material.dart';

import './login-screen.dart';
import './account-screen.dart';
import './create-quiz-screen.dart';
import './solve-screen.dart';

void main() => runApp(MainApp());

class MainApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        routes: {
          '/': (context) => LoginScreen(),
          '/account': (context) => AccountScreen(),
          '/create': (context) => CreateQuizScreen(),
          '/solve': (context) => SolveQuizScreen(""),
        },
        onGenerateRoute: (settings) {
          final settingsUri = Uri.parse(settings.name.toString().substring(2));
          final quizId = settingsUri.queryParameters['id'];
          debugPrint(settingsUri.toString());
          if (settings.name.toString().contains('solve')) {
            return MaterialPageRoute(builder: (context) {
              return SolveQuizScreen(quizId.toString());
            });
          }
        },
        debugShowCheckedModeBanner: false);
  }
}
