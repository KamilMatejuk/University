import os
import sys


def read_data():
    if len(sys.argv) != 3:
        print('Wrong number of arguments')
        exit()
    _, pattern, given_file = sys.argv
    if not os.path.isfile(given_file):
        print('Last argument should be a path to file')
        exit()
    with open(given_file, 'r') as f:
        text = f.read()
    return str(pattern), str(text)


def next_state(pattern, state, char, charlist):
    # char same as next char in pattern
    if state < len(pattern) and char == charlist.index(pattern[state]):
        return state + 1

    # return longest possible prefix
    for s in range(state, 0, -1):
        if charlist.index(pattern[s - 1]) == char:
            i = 0
            while i < s - 1 and pattern[i] == pattern[state - s + 1 + i]:
                i += 1
            if i == s - 1:
                return s
    return 0


def create_states_array(pattern, charlist):
    # create array of zeros with size len(charlist) x len(pattern)
    states = [[0] * len(charlist) for _ in range(len(pattern) + 1)]
    # fill array with states
    for state in range(len(pattern) + 1):
        for char in range(len(charlist)):
            states[state][char] = next_state(pattern, state, char, charlist)
    return states


def search(patern, text):
    # get unique chars occurring in text and pattern
    charlist = list(set([char for char in str(text) + str(pattern)]))
    # create 2D array of states
    states = create_states_array(pattern, charlist)
    # iterate through states and find pattern
    state = 0
    indexes = []
    for i in range(len(text)):
        state = states[state][charlist.index(text[i])]
        if state == len(patern):
            indexes.append(str(i - state + 1))
    # return pattern occurrences to user
    if len(indexes) == 0:
        print('Pattern not found')
    else:
        print(f'Pattern found at index(es): {", ".join(indexes)}')


if __name__ == '__main__':
    pattern, text = read_data()
    search(pattern, text)
