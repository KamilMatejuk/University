<?php
$projects_item_name = 'Compiler';
$projects_item_img = 'imgs/compiler.png';
$projects_item_desc = 'Compiler description Lorem, ipsum dolor sit amet consectetur adipisicing elit. Illum, fuga.';
$projects_item_technologies = 'C++, Flex, Bison';
$projects_item_link = './projects-compiler';
$projects_item_full_code_link = 'https://github.com/KamilMatejuk/Compiler';
$projects_item_code = '
#include &lt;iostream&gt;
#include &lt;fstream&gt;
#include &lt;string.h&gt;
#include &lt;vector&gt;

using namespace std;

extern string run_parser(FILE * input, bool debug);

int main(int argc, char const * argv[]){
    FILE * input;

    <span class="comment">// check args</span>
    if(argc &lt; 3 || argc &gt; 5){
    cerr &lt;&lt; "Please specify input and output files as respectively 2nd and 3rd argument" &lt;&lt; endl;
    cerr &lt;&lt; "You can add --debug as a last argument, for debugging options";
    return -1;
    }

    <span class="comment">// open file</span>
    input = fopen(argv[1], "r");
    if(!input){
    cerr &lt;&lt; "Couldn\'t open file " &lt;&lt; argv[1] &lt;&lt; endl;
    return -1;
    }
    bool debug = (argc == 4 && string(argv[3]) == "--debug");
    string machine_code = run_parser(input, debug);
    fclose(input);

    <span class="comment">// save output to file</span>
    ofstream output;
    output.open(argv[2]);
    output &lt;&lt; machine_code;
    output.close();

    return 0;
}';
require_once('__projects_description.php');
