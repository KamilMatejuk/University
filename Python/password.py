import os
import string
import traceback
from pynput.keyboard import Key, Listener

global user_input

def on_release(key):
    global user_input
    # store key
    k = str(key).replace('\'', '')
    if len(k) == 1:
        user_input += k
    # delete last char
    if key == Key.backspace:
        user_input = user_input[:-1]

    updateStandards(user_input)
    print('Write your password:', ''.join('*' for _ in range(len(user_input))))

    # Stop listener
    if key in [Key.enter, Key.esc]:
        finished(user_input)
        return False


def getPassword():
    try:
        global user_input
        user_input = ''
        updateStandards('')
        print('Write your password:')
        os.system("stty -echo") # turn off showing input
        # Collect events until released
        with Listener(on_release=on_release) as listener:
            listener.join()
            input()
    except:
        print(traceback.print_exc())
    finally:
        os.system("stty echo")  # turn on  showing input


def accept(text, accepted=False):
    if accepted:
        return f'\033[32;1m{text}\033[0m' # green
    else:
        return f'\033[31;1m{text}\033[0m' # red


def finished(password):
    print('Your password is:', password)


def checkStandards(password):
    uppercase_letters = string.ascii_uppercase
    lowercase_letters = string.ascii_lowercase
    digits = string.digits
    special_characters = "!@#$%^&*()-_"

    length_is_over_8 = False            
    contains_uppercase_letter = False
    contains_lowercase_letter = False
    contains_digit = False
    contains_special_character = False
    checklist = []
    if len(password) >= 8 :     
        length_is_over_8 = True
    checklist.append(length_is_over_8)

    for character in password:
        if character in uppercase_letters:
            contains_uppercase_letter = True
            break
    checklist.append(contains_uppercase_letter)

    for character in password:
        if character in lowercase_letters:
            contains_lowercase_letter = True
            break
    checklist.append(contains_lowercase_letter)

    for character in password:
        if character in digits:
            contains_digit = True
            break
    checklist.append(contains_digit)

    for character in password:
        if character in special_characters:
            contains_special_character = True
            break
    checklist.append(contains_special_character)
    return checklist


def updateStandards(password):
    # clear console
    os.system('clear')
    # check
    standards = checkStandards(password)
    print(accept("Length >= 8", standards[0]))
    print(accept("Contains uppercase letter", standards[1]))
    print(accept("Contains lowercase letter", standards[2]))
    print(accept("Contains digit", standards[3]))
    print(accept("Contains special character", standards[4]))


if __name__ == '__main__':
    getPassword()