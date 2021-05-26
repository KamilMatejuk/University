<?php
$projects_item_name = 'Tweet Bot';
$projects_item_img = 'imgs/tweet-bot.png';
$projects_item_desc = 'Tweet Bot description Lorem, ipsum dolor sit amet consectetur adipisicing elit. Illum, fuga.';
$projects_item_technologies = 'Python, Keras (Tendorflow), Flask';
$projects_item_link = './projects-tweet-bot';
$projects_item_full_code_link = 'https://github.com/Reclocco/PYTON-SUPER-PROJEKT';
$projects_item_code = '
<span class="comment"># 30 dozwolonych znaków</span>
<span class="comment"># (im mniej znaków tym prościej wytrenować)</span>
chars = sorted(
        [chr(i) for i in range(97, 123)] + <span class="comment"># małe litery</span>
        [\' \', \'#\', \'@\', \'.\'] <span class="comment"># znaki specjalne</span>
    )

<span class="comment"># przeliczenie ze znaków na liczby</span>
char_to_num = dict((c, i) for i, c in enumerate(chars))
num_to_char = dict((i, c) for i, c in enumerate(chars))

def generateModel(text):
    input_len = len(text)
    vocab_len = len(chars)
    print("Total number of characters:", input_len)
    print("Total number of different characters:", vocab_len)
    seq_length = 100
    x_data = []
    y_data = []

    for i in range(0, input_len - seq_length, 1):
        input_seq = text[i:i + seq_length]
        output = text[i + seq_length]
        x_data.append([char_to_num[char] for char in input_seq])
        y_data.append(char_to_num[output])

    n_patterns = len(x_data)
    X = numpy.reshape(x_data, (n_patterns, seq_length, 1))
    X = X / float(vocab_len)
    Y = np_utils.to_categorical(y_data)

    tb._SYMBOLIC_SCOPE.value = True

    model = Sequential()
    model.add(LSTM(256, input_shape=(X.shape[1], X.shape[2]), return_sequences=True))
    model.add(Dropout(0.3))
    model.add(LSTM(512, return_sequences=True))
    model.add(Dropout(0.3))
    model.add(LSTM(512))
    model.add(Dropout(0.1))
    model.add(Dense(Y.shape[1], activation=\'softmax\'))

    return model, X, Y, x_data';
require_once('__projects_description.php');