# Kamil Matejuk
import sys
import uuid
import random


maxSize = 150 * 8  # bitów
maxDataSize = maxSize - 4 * 32
flag = '01111110'

'''
struktura ramki:
    8 bitów - flaga (01111110)
    16 bitów - nr identyfikacyjny (unikatowy dla paczki, taki sam dla każdego fragmentu)
    3 bity - flagi
    13 bitów - przesunięcie
    32 bity - adres źródłowy
    32 bity - adres docelowy
    x bitów - dane
    16 bitów - suma kontrolna
    8 bitów - flaga (01111110)
'''

def bitStuffing(pattern, message):
    stuffed = ''
    p = pattern[:-1]
    opposite = '0' if pattern[-1] == '1' else '1'
    while message.find(p) != -1:
        i = message.find(p) + len(p)
        stuffed = stuffed + message[:i] + opposite
        message = message[i:]
    stuffed += message
    return stuffed

def bitDestuffing(pattern, message):
    destuffed = ''
    p = pattern[:-1]
    while message.find(p) != -1:
        i = message.find(p) + len(p)
        destuffed = destuffed + message[:i]
        message = message[i+1:]
    destuffed += message
    return destuffed

def crcReminder(message):
    # CRC-16-IBM
    # x^16 + x^15 + x^2 + 1
    polynominal_bit = '11000000000000101'
    len_message = len(message)
    initial_padding = '0' * (len(polynominal_bit) - 1)
    message_padded = list(message + initial_padding)
    while '1' in message_padded[:len_message]:
        current_shift = message_padded.index('1')
        for i in range(len(polynominal_bit)):
            xor = (polynominal_bit[i] != message_padded[current_shift + i])
            message_padded[current_shift + i] = str(int(xor))
    return ''.join(message_padded)[len_message:]

def addFrame(message, id, number, last):
    nr_identyfikacyjny = id
    flagi = '000' if last else '001'
    przesuniecie = str(bin(number))[2:].zfill(13)
    adres_zrodlowy = str(bin(uuid.getnode()))[2:34].zfill(32)
    adres_docelowy = str(bin(uuid.getnode()))[2:34].zfill(32)
    dane = message
    suma_kontrolna = ''.join('0' for i in range(16))
    internal = nr_identyfikacyjny + flagi + przesuniecie + adres_zrodlowy + adres_docelowy + dane
    suma_kontrolna = crcReminder(internal)

    stuffed = bitStuffing(flag, internal + suma_kontrolna)
    frame = flag + stuffed + flag
    # print('id', nr_identyfikacyjny)
    # print('frame', number)
    # print('flaga', flagi)
    # print('przesuniecie', przesuniecie)
    # print('az', adres_zrodlowy)
    # print('ad', adres_docelowy)
    # print('crc', suma_kontrolna)
    # print('dane', dane)
    # print()
    return frame

def frame(fileZ, fileW):
    # wczytanie bitów z pliku
    message = ''
    with open(fileZ) as Z:
        for line in Z.read().split():
            for char in line:
                if char in ['0', '1']:
                    message += char

    # podział na fragmenty
    fragments = []
    i = 0
    while i < len(message):
        fragments.append(message[i:i+maxDataSize])
        i += maxDataSize

    # nr identyfikacyjny
    id = ''.join(random.choice(['0','1']) for i in range(16))
    # kolejność
    i = 0
    # ramkowanie
    with open(fileW, 'w+') as W:
        for f in fragments:
            if fragments.index(f) == len(fragments)-1:      # ostatni
                W.write(addFrame(f, id, i, True))
            else:
                W.write(addFrame(f, id, i, False))
            i += 1

def getContent(fragments):
    data = []
    for f in fragments:
        destuffed = bitDestuffing(flag, f)

        nr_identyfikacyjny = destuffed[:16]
        flagi = destuffed[16:19]
        ostatni = flagi[2] == '0'
        przesuniecie = int(destuffed[19:32], 2)
        adres_zrodlowy = destuffed[32:64]
        adres_docelowy = destuffed[64:96]
        dane = destuffed[96:-16]
        suma_kontrolna = destuffed[-16:]
        correct = int(crcReminder(destuffed)) == 0

        item = {
            'id': nr_identyfikacyjny,
            'ostatni': ostatni,
            'kolejnosc': przesuniecie,
            'dane': dane,
            'poprawne crc': correct
        }
        data.append(item)
    return data

def differentiate(content):
    # rozrównienie wiadomości po id
    differentMessages = []
    for c in content:
        alreadyIn = False
        for d in differentMessages:
            if d['id'] == c['id']:
                # dodać do istniejącego
                c.pop('id', None)
                d['pakiety'].append(c)
                alreadyIn = True
                break
        if not alreadyIn:
            # stworzyć pole
            id = c['id']
            c.pop('id', None)
            differentMessages.append({
                'id': id,
                'pakiety': [c]
            })
    return differentMessages

def completeMessage(item):
    last = [i for i in item['pakiety'] if i['ostatni']]
    # ostatnia paczka nie dotarła
    if len(last) == 0:
        print('Didn\'t recieve final package\n')
        biggest = max(i['kolejnosc'] for i in item['pakiety'])
        message = ''
        for n in range(biggest+1):
            for i in item['pakiety']:
                if i['kolejnosc'] == n:
                    message += i['dane']
                    break
        message += '[ last package not recieved ]'
        return message

    message = ''
    number = last[0]['kolejnosc']
    for n in range(number+1):
        found = False
        for i in item['pakiety']:
            if i['kolejnosc'] == n:
                if not i['poprawne crc']:
                    # niepoprawna suma kontrolna
                    print('Some packages have incorrect checksum (crc)\n')
                    message += f'[ incorrect checksum for package {n} ]'
                else:
                    message += i['dane']
                found = True
                break
        if not found:
            # nie każda paczka dotarłą
            message += f'[ package {n} not recieved ]'
            print('Not all packages arrived\n')
    return message

def deframe(fileW, fileX):
    code = ''
    with open(fileW) as W:
        for line in W.read().split():
            for char in line:
                if char in ['0', '1']:
                    code += char
    # odczytanie wiadomości z ramek
    fragments = []
    while code.find(flag) != -1:
        start = code.find(flag) + len(flag)
        end = code[start:].find(flag)
        if end != -1:
            f = code[start : start+end]
            if len(f) > 0:
                fragments.append(f)
        code = code[start:]
    content = getContent(fragments)
    different = differentiate(content)
    with open(fileX, 'w+') as X:
        for d in different:
            m = completeMessage(d)
            X.write(m)


if __name__ == '__main__':
    if len(sys.argv) == 4 and sys.argv[1] == '--deframe':
            deframe(sys.argv[2], sys.argv[3])
    elif len(sys.argv) == 4 and sys.argv[1] == '--frame':
            frame(sys.argv[2], sys.argv[3])
