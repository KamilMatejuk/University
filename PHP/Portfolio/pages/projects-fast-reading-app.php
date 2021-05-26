<?php
$projects_item_name = 'Fast Reading App';
$projects_item_img = 'imgs/fast-reading-app.png';
$projects_item_desc = 'Fast Reading App description Lorem, ipsum dolor sit amet consectetur adipisicing elit. Illum, fuga.';
$projects_item_technologies = 'Java, Android';
$projects_item_link = './projects-fast-reading-app';
$projects_item_full_code_link = 'https://github.com/KamilMatejuk/Fast-Reading-Mobile-App';
$projects_item_code = '
public abstract class AbstractTrening extends AppCompatActivity {

    TextView textView;
    EditText editText;
    int time, rounds, size, width, count;
    boolean waitingForInput = true;
    ArrayList&lt;String&gt; userAnswers = null;
    ArrayList&lt;String&gt; givenNumbers = null;
    
    protected abstract void textChanged(CharSequence s); <span class="comment">// wyłapywanie kiedy klient skończył wpisywać</span>
    protected abstract void newText(); <span class="comment">// wyswietlanie tekstu</span>
    protected abstract void startTest();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trening);
    
        textView = findViewById(R.id.textView_speed);
        editText = findViewById(R.id.editText_speed);
    
        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        time = sharedPreferences.getInt("time", 1000);
        rounds = sharedPreferences.getInt("rounds", 10);
        size = sharedPreferences.getInt("size", 2);
        width = sharedPreferences.getInt("width", 20);
        count = sharedPreferences.getInt("count", 1);
    
    
        countdown();
    
        editText.addTextChangedListener(new TextWatcher() {
            <span class="comment">...</span>
        });
    }
    
    private void countdown() {
        editText.setVisibility(View.INVISIBLE);
        textView.setTextSize(50);
        new Thread() {
            public void run() {
                try {
                    for(int i=3; i>=0; i--){
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String c = (finalI>0 ? String.valueOf(finalI) : "Start!");
                                textView.setText(c);
                            }
                        });
                        Thread.sleep(1000);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editText.setVisibility(View.VISIBLE);
                            textView.setTextSize(30);
                        }
                    });
                    startTest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}';
require_once('__projects_description.php');