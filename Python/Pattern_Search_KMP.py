import os
import sys


def read_data():
    if len(sys.argv) != 3:
        print('Wrong number of arguments')
        exit()
    _, pattern, given_file = sys.argv
    if not os.path.isfile(given_file):
        print('Last argument should be a patternh to file')
        exit()
    with open(given_file, 'r') as f:
        text = f.read()
    return str(pattern), str(text)


def create_lps(pattern):
    longest_prefix_suffix = [0] * len(pattern)

    # length of the previous longest prefix suffix
    length = 0
    i = 1
    # fill array with longest proper prefix which is
    # also a suffix of given substring
    while i < len(pattern):
        if pattern[i] == pattern[length]:
            length += 1
            longest_prefix_suffix[i] = length
            i += 1
        else:
            if length != 0:
                length = longest_prefix_suffix[length-1]
            else:
                longest_prefix_suffix[i] = 0
                i += 1
    return longest_prefix_suffix


def search(pattern, text):
    # calculate longest prefix suffix array
    lps = create_lps(pattern)
    i = 0  # index for text
    j = 0  # index for pattern
    indexes = []
    # iterate through text and search for patterns and subpatterns
    while i < len(text):
        if pattern[j] == text[i]:
            i += 1
            j += 1
        # end of pattern
        if j == len(pattern):
            indexes.append(str(i - len(pattern)))
            j = lps[j-1]
        # mismatch after j matches
        elif i < len(text) and pattern[j] != text[i]:
            # Skip mathcing lps[0..lps[j-1]] characters
            if j != 0:
                j = lps[j-1]
            else:
                i += 1
    if len(indexes) == 0:
        print('Pattern not found')
    else:
        print(f'Pattern found at index(es): {", ".join(indexes)}')


if __name__ == '__main__':
    pattern, text = read_data()
    search(pattern, text)
