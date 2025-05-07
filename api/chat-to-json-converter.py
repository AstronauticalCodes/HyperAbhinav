final_json = {}

user1 = []
user2 = []
lastExecutedLineIndex = -1
with open(r'texts/sample-2.txt', encoding='utf-8') as file:
    lines = file.readlines()

    for lineIndex, line in enumerate(lines):
        if lineIndex < lastExecutedLineIndex:
            continue
        data = line.split('-')[-1].strip().split(':')
        user = data[0].strip()
        message = [data[-1].strip()]

        x = "me" if user == "Astronautical AK" else "friend"

        with open('texts/conversation.txt', 'a', encoding='utf-8') as f:
            f.write(f'{x}: {message[0]}\n')

        # for nextLineIndex, next_line in enumerate(lines[lineIndex + 1:]):
        #     next_data = next_line.split('-')[-1].strip().split(':')
        #     next_user = next_data[0].strip()
        #     lastExecutedLineIndex = nextLineIndex + lineIndex + 1
        #     if next_user != user and next_user != "":
        #         break
        #     else:
        #         next_message = next_data[-1].strip()
        #         message.append("\n" + next_message)
        #
        # if user == "Astronautical AK":
        #     user1.append(message)
        # elif user == "Study Grp 🤜🤛 (Jay)":
        #     user2.append(message)

# print(user1)
#
# with open('texts/trial.txt', 'w', encoding='utf-8') as f:
#     f.write('')
#
# with open('texts/trial.txt', 'a', encoding='utf-8') as f:
#     for lineIndex in range(len(user1)):
#         f.write(f"{len(user1[lineIndex])}\n")
