from collections import Counter

composition = input()

first_notes = [bar[0] for bar in composition.split("|") if bar]
note_counter = Counter(first_notes)

a_minor_tones = "ADE"
c_major_tones = "CFG"

a_minor_score = sum(note_counter[tone] for tone in a_minor_tones)
c_major_score = sum(note_counter[tone] for tone in c_major_tones)


if a_minor_score == c_major_score:
    scale = "C-dur" if composition[-1] == "C" else "A-mol"
else:
    scale = "C-dur" if c_major_score > a_minor_score else "A-mol"
print(scale)
