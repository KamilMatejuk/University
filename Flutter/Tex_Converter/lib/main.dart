import 'package:flutter/material.dart';
import 'package:flutter_tex/flutter_tex.dart';

void main() => runApp(MainApp());

class MainApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {'/': (context) => MainScreen()},
    );
  }
}

class MainScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[200],
      body: Center(
        child: MainForm(),
      ),
    );
  }
}

class MainForm extends StatefulWidget {
  @override
  _MainFormState createState() => _MainFormState();
}

class _MainFormState extends State<MainForm> {
  final _equationController = TextEditingController();
  String _resultEqution = "";

  void _showEquation() {
    var text = "";

    if (_equationController.text == "") {
      showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Center(
              child: Text("Fill the text box first"),
            ),
          );
        },
      );
    } else {
      text = _equationController.text.replaceAll(" ", "");
    }

    setState(() {
      _resultEqution = text;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        alignment: Alignment.center,
        child: LayoutBuilder(
            builder: (BuildContext ctx, BoxConstraints constraints) {
          // *******************************************************************
          // ************************ layout for mobile ************************
          // *******************************************************************
          if (constraints.maxWidth < 600) {
            return SingleChildScrollView(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  // edit text
                  Padding(
                    padding: EdgeInsets.all(8.0),
                    child: TextFormField(
                      controller: _equationController,
                      decoration:
                          InputDecoration(hintText: 'Write your equation here'),
                      textAlign: TextAlign.center,
                      style: DefaultTextStyle.of(context)
                          .style
                          .apply(fontSizeFactor: 1.5),
                    ),
                  ),

                  // text view
                  Padding(
                    padding: EdgeInsets.all(8.0),
                    child: TeXView(
                      renderingEngine: TeXViewRenderingEngine.katex(),
                      child: TeXViewDocument(
                        r'$$ ' + _resultEqution + r' $$',
                        style: TeXViewStyle.fromCSS(
                            'text-align: center; overflow: visible;'),
                      ),
                    ),
                  ),
                ],
              ),
            );
          } else {
            // *****************************************************************
            // ********************** layout for desktop ***********************
            // *****************************************************************
            return Row(
              children: [
                // edit text
                Expanded(
                  flex: 1,
                  child: Padding(
                    padding: EdgeInsets.all(8.0),
                    child: TextFormField(
                      controller: _equationController,
                      decoration:
                          InputDecoration(hintText: 'Write your equation here'),
                      textAlign: TextAlign.center,
                      style: DefaultTextStyle.of(context)
                          .style
                          .apply(fontSizeFactor: 1.5),
                    ),
                  ),
                ),

                // text view
                Expanded(
                  flex: 1,
                  child: Center(
                    child: SizedBox(
                      height: 300,
                      child: Padding(
                        padding: EdgeInsets.all(8.0),
                        child: TeXView(
                          renderingEngine: TeXViewRenderingEngine.katex(),
                          child: TeXViewDocument(
                            r'$$ ' + _resultEqution + r' $$',
                            style: TeXViewStyle.fromCSS(
                              'text-align: center; max-height: 100px;',
                            ),
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            );
          }
        }),
      ),
      bottomNavigationBar: BottomAppBar(
        shape: const CircularNotchedRectangle(),
        child: Container(height: 50.0),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _showEquation,
        tooltip: 'Translate',
        child: const Icon(Icons.swap_horiz_outlined, size: 40.0),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
    );
  }
}
