import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Demo (ACTION_PROCESS_TEXT + Free-form)',
      home: MyHomePage(title: 'Demo'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const channel = MethodChannel('sample.text');
  List<TextSpan> receivedParams = [];

  @override
  void initState() {
    super.initState();

    channel.setMethodCallHandler((call) async {
      if (call.method == "sendParams") {
        setState(() {
          receivedParams = (call.arguments.entries as Iterable)
              .map(
                (entry) => [
                  TextSpan(
                    text: '${entry.key}: ',
                    style: const TextStyle(fontWeight: FontWeight.bold),
                  ),
                  TextSpan(
                    text: '${entry.value}\n',
                    style: const TextStyle(fontWeight: FontWeight.normal),
                  ),
                ],
              )
              .expand<TextSpan>((i) => i)
              .toList();

          debugPrint(call.method);
        });
      }
    });
  }

  @override
  void dispose() {
    super.dispose();
    channel.setMethodCallHandler(null);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          title: Text(widget.title),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              RichText(
                text: TextSpan(
                  style: const TextStyle(color: Colors.black, fontSize: 18),
                  children: receivedParams,
                ),
              ),
            ],
          ),
        ));
  }
}
