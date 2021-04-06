import os
import sys
import glob
import subprocess


'''
Compile and evaluate VHDL entity from file f
'''
def add_compile(f):
    entity = get_entity(f)
    # compilation
    print(yellow(text_with_var('Compilation ...', f'[ghdl -a {f}]')), end="")
    bash_command(["ghdl", "-a", f])
    # elaboration
    print(yellow(text_with_var('Elaboration ...', f'[ghdl -e {entity}]')), end="")
    bash_command(["ghdl", "-e", entity])


'''
Run VHDL code from file f
'''
def run(f):
    entity = get_entity(f)
    # running
    print(yellow(text_with_var('Running ...', f'[ghdl -r {entity}]')))
    os.system(f'ghdl -r {entity}')


'''
Turn text to yellow for console print
'''
def yellow(text):
    return '\033[0;33m' + text + '\033[0m'


'''
Show text with variable, that contains always 30 spaces
'''
def text_with_var(text, var):
    return text + '\t\t{0:30s}'.format(var)


'''
Read entity name from file f
'''
def get_entity(f):
    file_path = os.path.join(os.getcwd(), f)
    try:
        with open(file_path) as f:
            text = f.read().lower().split(' ')
            for i in range(1, len(text) - 1):
                prev = text[i-1]
                curr = text[i]
                nxt = text[i+1]
                if 'entity' in prev and 'is' in nxt:
                    return curr
    except IOError:
        print("File not accessible")
        exit(1)


'''
Run command in bash, checking for errors, wornings, and printing 'OK' if successfull
'''
def bash_command(command):
    process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    output, error = process.communicate()
    if error:
        print()
        error = error.decode('ascii').strip()
        for line in error.split('\n'):
            if 'warning' in line:
                line = line.replace('warning', '\033[35;1mwarning\033[0m')
            else:
                l = line.split(':')
                line = ':'.join(l[:3]) + ':\033[31;1merror\033[0m:' + ':'.join(l[3:])
            print(line)
        exit(1)
    else:
        print('\033[32;1mOK\033[0m')
    if output:
        output = output.decode('ascii').strip()
        if len(output) > 0:
            print(output)


if __name__ == '__main__':
    # remove cached entities
    if '--del' in sys.argv:
        for f in glob.glob('work-obj*.cf'):
            os.remove(f)
            print(yellow(text_with_var('Removing file', f'"{f}"') + '\033[32;1mOK\033[0m'))
    
    # get list of files
    files = [a for a in sys.argv[1:] if a != '--del']
    if len(files) == 0:
        print('Choose file / list of files to compile and run')
        exit(1)

    # compile all into same workspace
    for f in files:
        add_compile(f)
    
    # run entity from last file
    run(files[-1])
