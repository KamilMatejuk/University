import 'dart:html' as html;
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class AccountScreen extends StatefulWidget {
  @override
  _AccountScreenState createState() => _AccountScreenState();
}

class Fruits {}

class _AccountScreenState extends State<AccountScreen> {
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

  Future<List> getMyQuizes() async {
    var quizes = [];
    if (FirebaseAuth.instance.currentUser != null) {
      await FirebaseFirestore.instance
          .collection('quizes')
          .where('author', isEqualTo: FirebaseAuth.instance.currentUser?.uid)
          .get()
          .then((QuerySnapshot querySnapshot) {
        querySnapshot.docs.forEach((doc) {
          quizes.add({'name': doc['name'], 'link': '/#/solve?id=${doc.id}'});
        });
      });
    }
    return quizes;
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
            // **************************** logout *****************************
            Padding(
              padding: EdgeInsets.all(30.0),
              child: FloatingActionButton(
                onPressed: () async {
                  await FirebaseAuth.instance.signOut();
                  Navigator.of(context).pushNamed('/');
                },
                tooltip: 'Logout',
                child: Icon(Icons.logout),
                heroTag: "fab_logout",
              ),
            ),
            // **************************** quizes *****************************
            Center(
              child: SingleChildScrollView(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    // welcome text
                    Padding(
                      padding: EdgeInsets.all(20.0),
                      child: Text('Hi',
                          style: Theme.of(context).textTheme.headline4),
                    ),
                    // quizes text
                    Padding(
                      padding: EdgeInsets.all(20.0),
                      child: Text('Here are your courses',
                          style: Theme.of(context).textTheme.headline4),
                    ),
                    // list of quizes
                    FutureBuilder<List>(
                      future: getMyQuizes(),
                      builder: (context, snapshot) {
                        List<Widget> children;
                        if (snapshot.hasData) {
                          List data = snapshot.data ?? [];
                          children = <Widget>[
                            SizedBox(
                              height: 300,
                              child: ListView.builder(
                                itemCount: data.length,
                                itemBuilder: (context, index) {
                                  return Center(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        Material(
                                          child: InkWell(
                                            onTap: () => {
                                              Navigator.of(context).pushNamed(
                                                  data[index]['link'])
                                            },
                                            child: Card(
                                              child: Column(
                                                children: [
                                                  Padding(
                                                    padding:
                                                        EdgeInsets.all(20.0),
                                                    child: Text(
                                                        data[index]['name'],
                                                        style: Theme.of(context)
                                                            .textTheme
                                                            .headline6),
                                                  ),
                                                  Padding(
                                                    padding:
                                                        EdgeInsets.all(20.0),
                                                    child: Text(
                                                      data[index]['link'],
                                                      style: TextStyle(
                                                          color: Colors.blue
                                                              .withOpacity(
                                                                  1.0)),
                                                      textAlign:
                                                          TextAlign.center,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                        ),
                                        Material(
                                          child: InkWell(
                                            onTap: () {
                                              var uri = html
                                                  .window.location.href
                                                  .toString();
                                              var host = uri.split('#')[0];
                                              var link = host +
                                                  data[index]['link']
                                                      .toString()
                                                      .substring(1);
                                              Clipboard.setData(
                                                ClipboardData(text: link),
                                              );
                                            },
                                            child: Padding(
                                              padding: EdgeInsets.all(20.0),
                                              child: Icon(Icons.copy),
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                  );
                                },
                              ),
                            ),
                          ];
                        } else if (snapshot.hasError) {
                          children = <Widget>[Text("err")];
                        } else {
                          children = <Widget>[CircularProgressIndicator()];
                        }
                        return Center(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: children,
                          ),
                        );
                      },
                    ),
                    // add quiz button
                    FloatingActionButton(
                      onPressed: () {
                        Navigator.of(context).pushNamed('/create');
                      },
                      tooltip: 'Add quiz',
                      child: Icon(Icons.add),
                      heroTag: "fab_add_quiz",
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
