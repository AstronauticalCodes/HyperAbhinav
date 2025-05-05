ignore_lines = [
    "Messages and calls are end-to-end encrypted. Only people in this chat can read, listen to, or share them. Learn more.",
    "Waiting for this message",
    "<Media omitted>",
]

final_data = []

with open(r'texts/02-05-2025.txt', encoding='utf-8') as file:
    data = file.readlines()
    for line in data:
        line = line.strip()
        if not any(i in line for i in ignore_lines):
            final_data.append(line)

with open('texts/sample-2.txt', 'w', encoding='utf-8') as file:
    for line in final_data:
        file.write(line + '\n')
