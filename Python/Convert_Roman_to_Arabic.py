def convert(roman):
    # letters = [wartosc litery w alfabecie arabskim, ilosc wystapien]
    letters = [[romanSwitch(roman[0]), 1]]
    for i in range(1, len(roman)):
        if letters[-1][0] == romanSwitch(roman[i]):
            letters[-1][1] += 1
        else:
            letters.append([romanSwitch(roman[i]), 1])
    values = [l[0] * l[1] for l in letters]
    number = 0
    for i in range(0, len(values)-1):
        if values[i] < values[i+1]:
            number -= values[i]
        else:
            number += values[i]
    number += values[-1]
    return number


def romanSwitch(char):
    switch = {"I": 1, "V": 5, "X": 10, "L": 50, "C": 100, "D": 500, "M": 1000}
    return switch.get(char.upper())


if __name__ == "__main__":
    print(convert("III"))
    print(convert("IV"))
    print(convert("XXXV"))
    print(convert("LD"))
    print(convert("C"))
    print(convert("D"))
    print(convert("MD"))
    print(convert("MMXX"))

    